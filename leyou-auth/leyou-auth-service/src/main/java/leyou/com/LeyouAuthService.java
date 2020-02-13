package leyou.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2020/2/13 15:19
 * @Modeified By:
 */
@EnableEurekaClient
@SpringBootApplication
@EnableFeignClients
public class LeyouAuthService {
    public static void main(String[] args) {
        SpringApplication.run(LeyouAuthService.class);
    }
}
