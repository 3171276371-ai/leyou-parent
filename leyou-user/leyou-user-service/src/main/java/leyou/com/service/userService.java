package leyou.com.service;

import leyou.com.user.pojo.TbUser;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2020/2/11 13:30
 * @Modeified By:
 */

public interface userService {


    /**
     * 校验用户准确性
     * @param data
     * @param type
     * @return
     */
    Boolean checkData(String data, Integer type);


    /**
     * 发送手机号码以及验证码到队列
     * @param phone
     * @return
     */
    Boolean sendCode(String phone);

    /**
     * 注册用户
     * @param user
     * @param code
     */
    Boolean register(TbUser user, String code);

    /**
     * 登录用户
     * @param username
     * @param password
     * @return
     */
    TbUser login(String username, String password);
}
