package leyou.com.service.impl;

import leyou.com.dao.userDao;
import leyou.com.service.userService;
import leyou.com.user.pojo.TbUser;
import leyou.com.utils.CodecUtils;
import leyou.com.utils.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2020/2/11 13:30
 * @Modeified By:
 */
@Service
public class userServiceImpl implements userService {

    static final String KEY_PREFIX = "user:code:phone:";
    static final Logger LOGGER = LoggerFactory.getLogger(userServiceImpl.class);
    @Autowired
    private userDao userDao;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
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
        return this.userDao.selectCount(record) == 0;
    }

    /**
     * 发送手机号码以及验证码到队列
     * @param phone
     * @return
     */
    public Boolean sendCode(String phone) {
        String code = NumberUtils.generateCode(6);
        try {
            // 发送短信
            Map<String, String> msg = new HashMap<>();
            msg.put("phone", phone);
            msg.put("code", code);
            this.amqpTemplate.convertAndSend("LEYOU.SMS.EXCHANGE", "sms.verify.code", msg);
            // 将code存入redis
            this.stringRedisTemplate.opsForValue().set(KEY_PREFIX + phone, code, 5, TimeUnit.MINUTES);
            return true;
        } catch (Exception e) {
            LOGGER.error("发送短信失败。phone：{}， code：{}", phone, code);
            return false;
        }
    }

    /**
     * 注册用户
     * @param user
     * @param code
     */
    @Override
    public Boolean register(TbUser user, String code) {
        String code1 = stringRedisTemplate.opsForValue().get(KEY_PREFIX + user.getPhone());
        if (!StringUtils.equals(code1,code)){
            return false;
        }
        String generateSalt = CodecUtils.generateSalt();
        String password = CodecUtils.md5Hex(user.getPassword(), generateSalt);
        user.setPassword(password);
        user.setId(null);
        user.setCreated(new Date());
        user.setSalt(generateSalt);
        boolean b = userDao.insertSelective(user) == 1;
        if (b){
            stringRedisTemplate.delete(KEY_PREFIX + user.getPhone());
        }
        return b;

    }

    /**
     * 登录用户
     * @param username
     * @param password
     * @return
     */
    @Override
    public TbUser login(String username, String password) {
        // 查询
        TbUser record = new TbUser();
        record.setUsername(username);
        TbUser user = this.userDao.selectOne(record);
        // 校验用户名
        if (user == null) {
            return null;
        }
        // 校验密码
        if (!user.getPassword().equals(CodecUtils.md5Hex(password, user.getSalt()))) {
            return null;
        }
        // 用户名密码都正确
        return user;
    }
}
