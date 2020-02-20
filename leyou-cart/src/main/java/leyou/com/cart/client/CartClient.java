package leyou.com.cart.client;

import leyou.com.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2020/2/19 10:00
 * @Modeified By:
 */
@FeignClient("item-service")
public interface CartClient extends GoodsApi {
}
