package leyou.com.cart.controller;

import leyou.com.cart.pojo.Cart;
import leyou.com.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2020/2/19 9:43
 * @Modeified By:
 */
@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 新增购物车
     * @param cart
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> addCart(@RequestBody Cart cart){
        cartService.addCart(cart);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 查询购物车
     * @return
     */
    @GetMapping
    public ResponseEntity<List<Cart>> queryCart(){
       List<Cart> cart =  cartService.query();
       if (CollectionUtils.isEmpty(cart)){
           return ResponseEntity.notFound().build();
       }
       return ResponseEntity.ok(cart);
    }

    /**
     * 添加购物车数量
     * @param cart
     * @return
     */
    @PostMapping("/update")
    public ResponseEntity<Void> addCartNum(@RequestBody Cart cart){
        cartService.updateCartNum(cart);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 删除购物车
     * @param cart
     * @return
     */
    @PostMapping("/delete")
    public ResponseEntity<Void> deleteCart(@RequestBody Cart cart){
        cartService.deleteCart(cart);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }






}
