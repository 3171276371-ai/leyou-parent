package leyou.com;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2020/2/19 12:07
 * @Modeified By:
 */
@SpringBootTest(classes = LeyouCartApplication.class)
@RunWith(SpringRunner.class)
public class test {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Test
    public void test(){
        BoundHashOperations<String, Object, Object> stringObjectObjectBoundHashOperations = redisTemplate.boundHashOps("18180585426");
        Set<Object> keys = stringObjectObjectBoundHashOperations.keys();
        for (Object key : keys) {
            System.out.println(key);
        }
    }
}
