package leyou.com.controller;

import leyou.com.service.impl.userServiceImpl;
import leyou.com.user.pojo.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2020/2/11 13:30
 * @Modeified By:
 */
@Controller
public class userController {

    @Autowired
    private userServiceImpl userService;
    /**
     * 校验用户的正确性
     * @param data
     * @param type
     * @return
     */
    @GetMapping("check/{data}/{type}")
    public ResponseEntity<Boolean> checkUser(@PathVariable("data")String data,@PathVariable("type")Integer type){
        Boolean boo = this.userService.checkData(data, type);
        if (boo==null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(boo);
    }

    /**
     * 发送手机号码以及验证码到队列
     * @param phone
     * @return
     */
    @PostMapping("code")
    public ResponseEntity<Void> sendCode(@RequestParam("phone")String phone){
        Boolean boo = this.userService.sendCode(phone);
        if (boo == null || !boo) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * 注册用户
     * @return
     */
    @PostMapping("register")
    public ResponseEntity<Boolean> register(@Validated TbUser user, @RequestParam("code")String code){
        Boolean boo = this.userService.register(user, code);
        if (boo == null || !boo) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 登录用户
     * @param username
     * @param password
     * @return
     */
    @GetMapping("query")
    public ResponseEntity<TbUser> login(@RequestParam("username")String username,@RequestParam("password")String password){
        TbUser user = userService.login(username,password);
        if (user==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
