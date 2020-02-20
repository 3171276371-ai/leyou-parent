package com.leyou.order.service.api;

import leyou.com.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "leyou-gateway", path = "/api/item")
public interface GoodsService extends GoodsApi {
}
