package leyou.com.controller;

import leyou.com.service.impl.userServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
}
