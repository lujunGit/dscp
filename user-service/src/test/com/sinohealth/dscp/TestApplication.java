package com.sinohealth.dscp;

import com.sinohealth.dscp.model.User;
import com.sinohealth.dscp.service.UserServiceV1;
import com.sinohealth.dscp.util.EncryptUtil;
import com.sinohealth.dscp.util.TimeUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {UserApplication.class})
@ActiveProfiles(profiles = "test")
public class TestApplication {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserServiceV1 userServiceV1;

    @Test
    public void addUserData() {
        long num = userServiceV1.getUserRepository().count();
        if (num > 0) {
            logger.info("当前测试表中数据：" + num + "条，无需增加！");
        } else {
            User user = null;
            for (int i = 0; i < 100; i++) {
                user = new User();
                user.setName("用户" + i);
                user.setAddress("广东-广州");
                user.setCreateTime(TimeUtil.ymdHms2date());
                user.setCreateUser("lj" + i);
                user.setEmail("1245282613@qq.com");
                user.setLastLoginAddr("广东-广州");
                user.setLoginName("root" + i);
                user.setName("admin" + i);
                user.setLoginPasswd(EncryptUtil.md5Encode("root" + i));
                userServiceV1.addUser(user);
            }
            logger.info("数据添加完毕！");
        }
    }

    @Test
    public void getUserByName() {
        long num = userServiceV1.getUserRepository().count();
        if (num == 0) {
            addUserData();
        }
        List<User> userList = userServiceV1.getUserRepository().findAll();
        int randomId = cycleRandom(userList.size());
        final String name = "admin" + randomId;
        User user = userServiceV1.getUserByName(name);
        if (user == null) {
            logger.error("获取用户失败！");
        }
    }

    @Test
    public void delRandomUser() {
        long num = userServiceV1.getUserRepository().count();
        if (num == 0) {
            addUserData();
        }
        List<User> userList = userServiceV1.getUserRepository().findAll();
        int randomId = cycleRandom(userList.size());
        User user = null;
        try {
            user = userServiceV1.getUserRepository().findOne(randomId);
            if (user != null) {
                userServiceV1.getUserRepository().delete(user);
                logger.info("删除[" + user.getName() + "]成功！");
            }
        } catch (Exception e) {
            logger.error("删除[" + user.getName() + "]失败！", e);
        }
    }

    @Test
    public void modifyUser() {
        long num = userServiceV1.getUserRepository().count();
        if (num == 0) {
            addUserData();
        }
        List<User> userList = userServiceV1.getUserRepository().findAll();
        int randomId = cycleRandom(userList.size());
        User oldUser = userServiceV1.getUserRepository().findOne(randomId);
        Integer flag = userServiceV1.updateUser(TimeUtil.ymdHms2date(), TimeUtil.ymdHms2date(), oldUser.getId
                ());
        if (flag == 1) {
            User newUser = userServiceV1.getUserRepository().findOne(randomId);
            logger.info("修改" + oldUser.getName() + "成功！修改前：" + oldUser.toString() + "|| 修改后：" + newUser
                    .toString());
        }
    }

    @Test
    public void getUserByUserId() {
        long num = userServiceV1.getUserRepository().count();
        if (num == 0) {
            addUserData();
        }
        List<User> userList = userServiceV1.getUserRepository().findAll();
        int randomId = cycleRandom(userList.size());
        User user = userServiceV1.getUserRepository().getOne(randomId);
        logger.info("获取到随机用户：" + user.toString());
    }

    public int cycleRandom(int initNum) {
        int randomId = new Random().nextInt(initNum) + 1;
        logger.info("随机数为：" + randomId);
        boolean existId = userServiceV1.getUserRepository().exists(randomId);
        if (existId) {
            return randomId;
        } else {
            return cycleRandom(initNum);
        }
    }
}
