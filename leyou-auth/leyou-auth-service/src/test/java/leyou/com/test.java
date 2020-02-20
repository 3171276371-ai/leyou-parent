package leyou.com;

import leyou.com.auth.client.UserClient;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2020/2/13 20:22
 * @Modeified By:
 */
@SpringBootTest(classes = LeyouAuthService.class)
@RunWith(SpringRunner.class)
public class test {
    @Autowired
    private UserClient userClient;

}
