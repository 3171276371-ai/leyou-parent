package cxt.cn.goods.client;

import leyou.com.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2019/12/21 13:06
 * @Modeified By:
 */
@FeignClient(value = "item-service")
public interface CategoryClient extends CategoryApi {

}
