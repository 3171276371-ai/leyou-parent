package cxt.cn.goods.client;

import leyou.com.item.api.SpecApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2019/12/21 13:33
 * @Modeified By:
 */
@FeignClient(value = "item-service")
public interface SpecClient extends SpecApi {
}
