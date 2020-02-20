package leyou.com;

import leyou.com.dao.userDao;
import leyou.com.service.impl.userServiceImpl;
import leyou.com.user.pojo.TbUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2020/2/13 20:00
 * @Modeified By:
 */
@SpringBootTest(classes = LeyouUserApplication.class)
@RunWith(SpringRunner.class)
public class test {
    @Autowired
    private userServiceImpl userService;
    @Autowired
    private userDao userDao;

    @Test
    public void test(){
//        TbUser tbUser = new TbUser();
//        tbUser.setUsername("liuxiang");
//        TbUser tbUser1 = userDao.selectOne(tbUser);
//        System.out.println(tbUser1);
//        String password = CodecUtils.md5Hex("cxt147852369", tbUser1.getSalt());
//        System.out.println(password);
        TbUser login = userService.login("liuxiang", "cxt147852369");
        System.out.println(login);

    }
}
