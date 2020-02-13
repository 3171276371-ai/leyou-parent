package leyou.com.auth.service.impl;

import com.leyou.pojo.UserInfo;
import com.leyou.utils.JwtUtils;
import leyou.com.auth.client.UserClient;
import leyou.com.auth.config.JwtProperties;
import leyou.com.auth.service.AuthService;
import leyou.com.user.pojo.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2020/2/13 15:36
 * @Modeified By:
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserClient client;

    @Autowired
    private JwtProperties properties;
    /**
     * 验证用户名和密码
     * @param username
     * @param password
     * @return
     */
    @Override
    public String login(String username, String password) {
        TbUser tbUser = client.login(username, password);
        System.out.println(tbUser);
        if (tbUser==null){
            return null;
        }
        try {
           return JwtUtils.generateToken(new UserInfo(tbUser.getId(), tbUser.getUsername()),
                    properties.getPrivateKey(), properties.getExpire());
        } catch (Exception e) {
          return null;
        }
    }
}
