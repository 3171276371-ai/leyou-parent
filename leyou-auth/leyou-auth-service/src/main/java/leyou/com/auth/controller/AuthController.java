package leyou.com.auth.controller;

import leyou.com.auth.config.JwtProperties;
import leyou.com.auth.service.AuthService;
import leyou.com.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2020/2/13 15:32
 * @Modeified By:
 */
@Controller
@EnableConfigurationProperties(JwtProperties.class)
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtProperties prop;



    /**
     * 登录授权
     * @param username
     * @param password
     * @return
     */
    @PostMapping("login")
    public ResponseEntity<String> login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletRequest request,
            HttpServletResponse response){
        //登录
        String token = authService.login(username,password);
        //写入cookie
        CookieUtils.setCookie(request, response, prop.getCookieName(),
                token, prop.getExpire(), null,true);
        return ResponseEntity.ok(token);
    }

}
