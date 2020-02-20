package leyou.com.auth.controller;

import com.leyou.pojo.UserInfo;
import com.leyou.utils.JwtUtils;
import leyou.com.auth.config.JwtProperties;
import leyou.com.auth.service.AuthService;
import leyou.com.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2020/2/13 15:32
 * @Modeified By:
 */
@RestController
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
//        if (StringUtils.isBlank(token)) {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
        //写入cookie
        CookieUtils.setCookie(request, response, prop.getCookieName(),
                token, prop.getCookieMaxAge(), null,true);
        return ResponseEntity.ok(token);
    }

    /**
     * 验证用户信息
     * @param token
     * @return
     */
    @GetMapping("verify")
    public ResponseEntity<UserInfo> verify(@CookieValue("LY_TOKEN")String token,
                                           HttpServletRequest request,
                                           HttpServletResponse response){
        try {
            UserInfo infoFromToken = JwtUtils.getInfoFromToken(token, prop.getPublicKey());
            //重新生成token
            token = JwtUtils.generateToken(infoFromToken, prop.getPrivateKey(), prop.getExpire());
            //重新生成cookie
            CookieUtils.setCookie(request,response,prop.getCookieName(),token,prop.getCookieMaxAge(),"UTF-8");
            return ResponseEntity.ok(infoFromToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
