package leyou.search.client;

import leyou.com.item.pojo.SpuBo;
import leyou.com.pojo.PageResult;
import leyou.search.LeyouSearchApplication;
import leyou.search.Responstory.GoodsRepository;
import leyou.search.pojo.Goods;
import leyou.search.service.GoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2019/12/21 20:07
 * @Modeified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeyouSearchApplication.class)
public class CreateIndex {
    @Autowired
    private GoodsClient goodsClient;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private GoodsService goodsService;

    @Test
    public void createIndex() {
        elasticsearchTemplate.createIndex(Goods.class);
        elasticsearchTemplate.putMapping(Goods.class);
    }

    @Test
    public void InsertData() {
        Integer page = 1;
        Integer rows = 100;

        do {
            // 分批查询spuBo
            PageResult<SpuBo> pageResult = goodsClient.querySpuByPage(null, true, page, rows);
            // 遍历spubo集合转化为List<Goods>
            List<Goods> goodsList = pageResult.getItems().stream().map(spuBo -> {
                try {
                    return goodsService.buildGoods(spuBo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());
            goodsRepository.saveAll(goodsList);
            // 获取当前页的数据条数，如果是最后一页，没有100条
            rows = pageResult.getItems().size();
            // 每次循环页码加1
            page++;
        } while (rows == 100);
    }
}
