package leyou.search.client;

import leyou.com.item.pojo.SpuBo;
import leyou.com.item.pojo.TbSpu;
import leyou.com.pojo.PageResult;
import leyou.search.LeyouSearchApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2019/12/21 14:00
 * @Modeified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeyouSearchApplication.class)
public class GoodsClientTest {

    @Autowired
    private GoodsClient goodsClient;
    @Autowired
    private CategoryClient categoryClient;
    @Test
    public void testQueryCategories() {
        PageResult<SpuBo> spuBoPageResult = goodsClient.querySpuByPage(null, null, 1, 5);
        spuBoPageResult.getItems().forEach(System.out::println);
    }
    @Test
    public void testQueryCategories2() {
        TbSpu tbSpu = goodsClient.querySpuById(120L);
        System.out.println(tbSpu);
    }

    @Test
    public void testQueryCategories1() {
        List<String> names = this.categoryClient.queryNamesByIds(Arrays.asList(1L, 2L, 3L));
        names.forEach(System.out::println);
    }
}