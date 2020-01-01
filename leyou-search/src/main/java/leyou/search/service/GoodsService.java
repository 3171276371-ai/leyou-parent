package leyou.search.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import leyou.com.item.pojo.*;
import leyou.search.Responstory.GoodsRepository;
import leyou.search.client.BrandClient;
import leyou.search.client.CategoryClient;
import leyou.search.client.GoodsClient;
import leyou.search.client.SpecClient;
import leyou.search.pojo.Goods;
import leyou.search.pojo.SearchRequest;
import leyou.search.pojo.SearchResult;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2019/12/21 20:03
 * @Modeified By:
 */

@Service
public class GoodsService {
    @Autowired
    private BrandClient brandClient;
    @Autowired
    private CategoryClient categoryClient;
    @Autowired
    private GoodsClient goodsClient;
    @Autowired
    private SpecClient specClient;
    @Autowired
    private GoodsRepository goodsRepository;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 插入商品到elastcsearch
     * @param spu
     * @return
     * @throws IOException
     */
    public Goods buildGoods(TbSpu spu) throws IOException {
        //创建goods对象
        Goods goods = new Goods();
        //查询品牌
        TbBrand brand = brandClient.queryBrandById(spu.getBrandId());
        //查询分类名称
        List<String> names = categoryClient.queryNamesByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        // 查询spu下的所有sku
        List<TbSku> tbSkus = goodsClient.queryTbSkuBySpuId(spu.getId());
        List<Long> prices = new ArrayList<>();
        List<Map<String, Object>> skuMapList = new ArrayList<>();
        // 遍历skus，获取价格集合
        tbSkus.forEach(sku -> {
            prices.add(sku.getPrice());
            Map<String, Object> skuMap = new HashMap<>();
            skuMap.put("id", sku.getId());
            skuMap.put("title", sku.getTitle());
            skuMap.put("price", sku.getPrice());
            skuMap.put("image", StringUtils.isNotBlank(sku.getImages()) ? StringUtils.split(sku.getImages(), ",")[0] : "");
            skuMapList.add(skuMap);
        });

        // 查询出所有的搜索规格参数
        List<TbSpecParam> params = specClient.querySpecParam(null, spu.getCid3(), null, true);
        // 查询spuDetail。获取规格参数值
        TSpuDetail spuDetail = goodsClient.findDetailsById(spu.getId());
        // 获取通用的规格参数
        Map<Long, Object> genericSpecMap = MAPPER.readValue(spuDetail.getGenericSpec(), new TypeReference<Map<Long, Object>>() {
        });
        // 获取特殊的规格参数
        Map<Long, List<Object>> specialSpecMap = MAPPER.readValue(spuDetail.getSpecialSpec(), new TypeReference<Map<Long, List<Object>>>() {
        });
        // 定义map接收{规格参数名，规格参数值}
        Map<String, Object> paramMap = new HashMap<>();
        params.forEach(param -> {
            // 判断是否通用规格参数
            if (param.getGeneric()) {
                // 获取通用规格参数值
                String value = genericSpecMap.get(param.getId()).toString();
                // 判断是否是数值类型
                if (param.getNumeric()) {
                    // 如果是数值的话，判断该数值落在那个区间
                    value = chooseSegment(value, param);
                }
                // 把参数名和值放入结果集中
                paramMap.put(param.getName(), value);
            } else {
                paramMap.put(param.getName(), specialSpecMap.get(param.getId()));
            }
        });

        // 设置参数
        goods.setId(spu.getId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setBrandId(spu.getBrandId());
        goods.setCreateTime(spu.getCreateTime());
        goods.setSubTitle(spu.getSubTitle());
        goods.setAll(spu.getTitle() + brand.getName() + StringUtils.join(names, " "));
        goods.setPrice(prices);
        goods.setSkus(MAPPER.writeValueAsString(skuMapList));
        goods.setSpecs(paramMap);

        return goods;
    }

    private String chooseSegment(String value, TbSpecParam p) {
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        // 保存数值段
        for (String segment : p.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if (segs.length == 2) {
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if (val >= begin && val < end) {
                if (segs.length == 1) {
                    result = segs[0] + p.getUnit() + "以上";
                } else if (begin == 0) {
                    result = segs[1] + p.getUnit() + "以下";
                } else {
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;
    }

    /**
     * @param searchRequest
     * @return
     */
    public SearchResult queryPage(SearchRequest searchRequest) {
        String key = searchRequest.getKey();
        if (StringUtils.isBlank(key)) {
            return null;
        }
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
//        MatchQueryBuilder basicQuery = QueryBuilders.matchQuery("all", searchRequest.getKey()).operator(Operator.AND);
        BoolQueryBuilder boolQueryBuilder = buildBoolQueryBuilder(searchRequest);
        //根据条件查询
        searchQueryBuilder.withQuery(boolQueryBuilder);
        //过滤
        searchQueryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id", "subTitle", "skus"}, null));
        Integer page = searchRequest.getPage();
        Integer size = searchRequest.getSize();
        searchQueryBuilder.withPageable(PageRequest.of(page, size));
        String sortBy = searchRequest.getSortBy();
        Boolean descending = searchRequest.getDescending();
        if (StringUtils.isNotBlank(sortBy)) {
            searchQueryBuilder.withSort(SortBuilders.fieldSort(sortBy).order(descending ? SortOrder.DESC : SortOrder.ASC));
        }
        String categoryAggName = "categories";
        String brandAggName = "brands";
        searchQueryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid3"));
        searchQueryBuilder.addAggregation(AggregationBuilders.terms(brandAggName).field("brandId"));
        // 执行搜索，获取搜索的结果集
        AggregatedPage<Goods> goodsPage = (AggregatedPage<Goods>) goodsRepository.search(searchQueryBuilder.build());
        // 解析聚合结果集
        List<Map<String, Object>> categories = getCategoryAggResult(goodsPage.getAggregation(categoryAggName));
        List<TbBrand> brands = getBrandAggResult(goodsPage.getAggregation(brandAggName));
        List<Map<String, Object>> spec = null;
        if (categories.size() == 1) {
            spec = getParamAggResult((Long) categories.get(0).get("id"), boolQueryBuilder);
        }
        Long totalElements = goodsPage.getTotalElements();
        int totalPage = (totalElements.intValue() + size - 1) / size;
        return new SearchResult(totalElements, (long) totalPage, goodsPage.getContent(), categories, brands, spec);
    }

    /**
     * bool查询构建起
     * @param request
     * @return
     */
    private BoolQueryBuilder buildBoolQueryBuilder(SearchRequest request) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        // 添加基本查询条件
        boolQueryBuilder.must(QueryBuilders.matchQuery("all", request.getKey()).operator(Operator.AND));
        // 添加过滤条件
        if (CollectionUtils.isEmpty(request.getFilter())){
            return boolQueryBuilder;
        }
        for (Map.Entry<String, String> entry : request.getFilter().entrySet()) {

            String key = entry.getKey();
            // 如果过滤条件是“品牌”, 过滤的字段名：brandId
            if (StringUtils.equals("品牌", key)) {
                key = "brandId";
            } else if (StringUtils.equals("分类", key)) {
                // 如果是“分类”，过滤字段名：cid3
                key = "cid3";
            } else {
                // 如果是规格参数名，过滤字段名：specs.key.keyword
                key = "specs." + key + ".keyword";
            }
            boolQueryBuilder.filter(QueryBuilders.termQuery(key, entry.getValue()));
        }

        return boolQueryBuilder;
    }

    /**
     * 聚合出规格参数过滤条件
     *
     * @param id
     * @param basicQuery
     * @return
     */
    private List<Map<String, Object>> getParamAggResult(Long id, QueryBuilder basicQuery) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 基于基本的查询条件，聚合规格参数
        queryBuilder.withQuery(basicQuery);
        // 查询要聚合的规格参数
        List<TbSpecParam> params = this.specClient.querySpecParam(null, id, null, true);
        // 添加聚合
        params.forEach(param -> {
            queryBuilder.addAggregation(AggregationBuilders.terms(param.getName()).field("specs." + param.getName() + ".keyword"));
        });
        // 只需要聚合结果集，不需要查询结果集
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{}, null));

        // 执行聚合查询
        AggregatedPage<Goods> goodsPage = (AggregatedPage<Goods>) this.goodsRepository.search(queryBuilder.build());

        // 定义一个集合，收集聚合结果集
        List<Map<String, Object>> paramMapList = new ArrayList<>();
        // 解析聚合查询的结果集
        Map<String, Aggregation> aggregationMap = goodsPage.getAggregations().asMap();
        for (Map.Entry<String, Aggregation> entry : aggregationMap.entrySet()) {
            Map<String, Object> map = new HashMap<>();
            // 放入规格参数名
            map.put("k", entry.getKey());
            // 收集规格参数值
            List<Object> options = new ArrayList<>();
            // 解析每个聚合
            StringTerms terms = (StringTerms) entry.getValue();
            // 遍历每个聚合中桶，把桶中key放入收集规格参数的集合中
            terms.getBuckets().forEach(bucket -> options.add(bucket.getKeyAsString()));
            map.put("options", options);
            paramMapList.add(map);
        }

        return paramMapList;
    }

    /**
     * 解析品牌聚合结果集
     *
     * @param aggregation
     * @return
     */
    private List<TbBrand> getBrandAggResult(Aggregation aggregation) {
        // 处理聚合结果集
        LongTerms terms = (LongTerms) aggregation;
        // 获取所有的品牌id桶
        List<LongTerms.Bucket> buckets = terms.getBuckets();
        // 定义一个品牌集合，搜集所有的品牌对象
        List<TbBrand> brands = new ArrayList<>();
        // 解析所有的id桶，查询品牌
        buckets.forEach(bucket -> {
            TbBrand brand = this.brandClient.queryBrandById(bucket.getKeyAsNumber().longValue());
            brands.add(brand);
        });
        return brands;
    }

    /**
     * 解析分类
     *
     * @param aggregation
     * @return
     */
    private List<Map<String, Object>> getCategoryAggResult(Aggregation aggregation) {
        // 处理聚合结果集
        LongTerms terms = (LongTerms) aggregation;
        // 获取所有的分类id桶
        List<LongTerms.Bucket> buckets = terms.getBuckets();
        // 定义一个品牌集合，搜集所有的品牌对象
        List<Map<String, Object>> categories = new ArrayList<>();
        List<Long> cids = new ArrayList<>();
        // 解析所有的id桶，查询品牌
        buckets.forEach(bucket -> {
            cids.add(bucket.getKeyAsNumber().longValue());
        });
        List<String> names = this.categoryClient.queryNamesByIds(cids);
        for (int i = 0; i < cids.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", cids.get(i));
            map.put("name", names.get(i));
            categories.add(map);
        }
        return categories;
    }
}



