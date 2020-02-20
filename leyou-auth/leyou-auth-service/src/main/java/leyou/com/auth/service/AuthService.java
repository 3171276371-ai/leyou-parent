package leyou.com.auth.service;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2020/2/13 15:36
 * @Modeified By:
 */
public interface AuthService {

    /**
     * 验证用户名和密码
     * @param username
     * @param password
     * @return
     */
    String login(String username, String password);

}
