package leyou.com.user.api;

import leyou.com.user.pojo.TbUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2020/2/13 15:27
 * @Modeified By:
 */
public interface UserApi {
    @GetMapping("query")
    TbUser login(@RequestParam("username")String username, @RequestParam("password")String password);
}
