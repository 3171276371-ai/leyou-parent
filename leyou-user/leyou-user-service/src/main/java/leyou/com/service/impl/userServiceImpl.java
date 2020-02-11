package leyou.com.service.impl;

import leyou.com.dao.userMapper;
import leyou.com.service.userService;
import leyou.com.user.pojo.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2020/2/11 13:30
 * @Modeified By:
 */
@Service
public class userServiceImpl implements userService {

    @Autowired
    private userMapper userMapper;
    /**
     * 校验用户登录
     * @param data
     * @param type
     * @return
     */
    public Boolean checkData(String data, Integer type) {
        TbUser record = new TbUser();
        switch (type) {
            case 1:
                record.setUsername(data);
                break;
            case 2:
                record.setPhone(data);
                break;
            default:
                return null;
        }
        return this.userMapper.selectCount(record) == 0;
    }
}
