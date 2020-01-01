package leyou.search.client;

import leyou.com.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2019/12/21 13:20
 * @Modeified By:
 */
@FeignClient(value = "item-service")
public interface GoodsClient extends GoodsApi {
}
