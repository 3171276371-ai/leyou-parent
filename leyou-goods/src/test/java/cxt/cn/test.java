package cxt.cn;

import cxt.cn.goods.client.SpecClient;
import leyou.com.item.pojo.TbSpecParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2019/12/28 21:07
 * @Modeified By:
 */
@SpringBootTest(classes = GoodsApplication.class)
@RunWith(SpringRunner.class)
public class test {
    @Autowired
    private SpecClient specClient;

    @Test
    public void test(){
        List<TbSpecParam> params = this.specClient.querySpecParam(null, 76L, null, null);
        for (TbSpecParam param : params) {
            System.out.println(param);
        }
    }
}
