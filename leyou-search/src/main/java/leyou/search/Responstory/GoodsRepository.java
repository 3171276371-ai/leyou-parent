package leyou.search.Responstory;

import leyou.search.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2019/12/21 20:08
 * @Modeified By:
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods,Long>{
}
