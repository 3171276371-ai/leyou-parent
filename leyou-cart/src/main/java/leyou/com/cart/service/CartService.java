package leyou.com.cart.service;

import com.leyou.pojo.UserInfo;
import leyou.com.cart.client.CartClient;
import leyou.com.cart.interceptor.CartInterceptor;
import leyou.com.cart.pojo.Cart;
import leyou.com.item.pojo.TbSku;
import leyou.com.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2020/2/19 9:44
 * @Modeified By:
 */
@Service
public class CartService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private CartClient cartClient;
    public static final Logger LOGGER = LoggerFactory.getLogger(CartService.class);
    public void addCart(Cart cart) {
        UserInfo userInfo = CartInterceptor.getUserInfo();
        BoundHashOperations<String, Object, Object> stringObjectObjectBoundHashOperations = redisTemplate.boundHashOps(userInfo.getId().toString());
        Long skuId = cart.getSkuId();
        Integer num = cart.getNum();
        if (stringObjectObjectBoundHashOperations.hasKey(skuId.toString())){
            String cartJson = stringObjectObjectBoundHashOperations.get(skuId.toString()).toString();
            cart = JsonUtils.parse(cartJson, Cart.class);
            cart.setNum(num+cart.getNum());
            stringObjectObjectBoundHashOperations.put(skuId.toString(),JsonUtils.serialize(cart));
        }
        cart.setUserId(userInfo.getId());
        // 其它商品信息，需要查询商品服务
        TbSku tbSku = cartClient.querySkuById(skuId);
        cart.setImage(StringUtils.isBlank(tbSku.getImages()) ? "" : StringUtils.split(tbSku.getImages(), ",")[0]);
        cart.setPrice(tbSku.getPrice());
        cart.setTitle(tbSku.getTitle());
        cart.setOwnSpec(tbSku.getOwnSpec());
        stringObjectObjectBoundHashOperations.put(cart.getSkuId().toString(),JsonUtils.serialize(cart));
    }

    public List<Cart> query() {
        UserInfo userInfo = CartInterceptor.getUserInfo();
        String id = userInfo.getId().toString();
        if (!redisTemplate.hasKey(id)){
            return null;
        }
        BoundHashOperations<String, Object, Object> stringObjectObjectBoundHashOperations = redisTemplate.boundHashOps(userInfo.getId().toString());
        List<Object> carts = stringObjectObjectBoundHashOperations.values();
        if (CollectionUtils.isEmpty(carts)){
            return null;
        }
        return carts.stream().map(o -> JsonUtils.parse(o.toString(), Cart.class)).collect(Collectors.toList());
    }

    public void updateCartNum(Cart cart) {
        UserInfo userInfo = CartInterceptor.getUserInfo();
        Integer num = cart.getNum();
        Long skuId = cart.getSkuId();
        BoundHashOperations<String, Object, Object> stringObjectObjectBoundHashOperations = redisTemplate.boundHashOps(userInfo.getId().toString());
        String cartJson = stringObjectObjectBoundHashOperations.get(skuId.toString()).toString();
        cart = JsonUtils.parse(cartJson, Cart.class);
        cart.setNum(num);
        stringObjectObjectBoundHashOperations.put(skuId.toString(),JsonUtils.serialize(cart));
    }

    public void deleteCart(Cart cart) {
        Long skuId = cart.getSkuId();
        UserInfo userInfo = CartInterceptor.getUserInfo();
        BoundHashOperations<String, Object, Object> stringObjectObjectBoundHashOperations = redisTemplate.boundHashOps(userInfo.getId().toString());
//        String cartJson = stringObjectObjectBoundHashOperations.get(skuId.toString()).toString();
//        cart = JsonUtils.parse(cartJson,Cart.class);
        stringObjectObjectBoundHashOperations.delete(skuId.toString());
    }
}
