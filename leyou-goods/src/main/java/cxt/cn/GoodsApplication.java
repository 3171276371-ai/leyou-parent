package cxt.cn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2019/12/28 15:02
 * @Modeified By:
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class GoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class);
    }
}
