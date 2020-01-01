package leyou.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author:陈啸掭
 * @Description:
 * @CreateTime: 2019/12/6 15:28
 */
@SpringBootApplication
@EnableDiscoveryClient
public class UploadApplication {
    public static void main(String[] args) {
        SpringApplication.run(UploadApplication.class);
    }
}
