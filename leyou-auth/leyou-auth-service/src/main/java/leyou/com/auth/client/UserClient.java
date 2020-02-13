package leyou.com.auth.client;

import leyou.com.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2020/2/13 15:33
 * @Modeified By:
 */
@FeignClient(value = "leyou-service")
public interface UserClient extends UserApi {
}