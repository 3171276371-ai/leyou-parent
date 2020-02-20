package leyou.com.auth.client;

import leyou.com.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author:陈啸掭
 * @Description:从user用户微服务调取用户数据加入cookie
 * @Date:Create in 2020/2/13 15:33
 * @Modeified By:
 */
@FeignClient(value = "user-service")
public interface UserClient extends UserApi {
}