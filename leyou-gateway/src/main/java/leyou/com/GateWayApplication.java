package leyou.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @Author:陈啸掭
 * @Description:
 * @CreateTime: 2019/11/29 11:41
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class GateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class);
    }
}
