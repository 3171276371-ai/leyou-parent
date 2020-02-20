package leyou.com.user.api;

import leyou.com.user.pojo.TbUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserApi {

    /**
     * 登录用户
     * @param username
     * @param password
     * @return
     */
    @GetMapping("query")
    TbUser login(@RequestParam("username")String username, @RequestParam("password")String password);

}
