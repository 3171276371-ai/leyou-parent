package leyou.com.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author:陈啸掭
 * @Description:
 * @CreateTime: 2019/11/29 12:06
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("leyou.com.item.dao")
public class ItemApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItemApplication.class);
    }
}
