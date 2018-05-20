package com.sinohealth.dscp;

import com.sinohealth.dscp.model.*;
import com.sinohealth.dscp.service.*;
import com.sinohealth.dscp.util.EncryptUtil;
import com.sinohealth.dscp.util.TimeUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {UserApplication.class})
@ActiveProfiles(profiles = "test")
public class TestApplication {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserServiceV1 userServiceV1;
    @Autowired
    private RoleServiceV1 roleServiceV1;
    @Autowired
    private UserRoleServiceV1 userRoleServiceV1;
    @Autowired
    private ResourceServiceV1 resourceServiceV1;
    @Autowired
    private RoleResourceServiceV1 roleResourceServiceV1;

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
        User user = userServiceV1.getUserRepository().findOne(randomId);
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

    /**
     * 测试添加认证数据
     */
    @Test
    public void testAddAuthData() {

        final String loginName="root1";

        //添加角色
        addRoleData(loginName);

        //添加资源权限
        addResourceData(loginName);
    }

    public void addRoleData(String loginName) {
        Long roleCount = roleServiceV1.getRoleCount();
        if (roleCount == 0) {
            final Integer num = 10;
            Role role = null;
            for (int i = 0; i < num; i++) {
                role = new Role();
                role.setRoleName("角色" + i + 1);
                role.setCreateTime(TimeUtil.ymdHms2date());
                role.setUpdateTime(TimeUtil.ymdHms2date());
                role.setCreateUser("lj");
                role.setUpdateUser("lj");
                roleServiceV1.getRoleRepository().save(role);
            }
            logger.info("认证数据多余"+roleCount+"条，已完成！");
            roleCount = 10L;
        } else {
            logger.info("认证数据多余1条，无需添加！");
        }
        //取随机个数作为角色id
        List<Integer> roleIds = new ArrayList<>();
        for (int i = 0; i < (roleCount > 2 ? roleCount - 1 : roleCount); i++) {
            int randomId = new Random().nextInt(roleCount.intValue()) + 1;
            roleIds.add(randomId);
            logger.info("随机角色编号为：【" + randomId + "】重复的会被剔除");
        }
        HashSet<Integer> hashSet = new HashSet<Integer>(roleIds);
        roleIds.clear();
        roleIds.addAll(hashSet);

        //把随机角色给予用户root1
        Integer root1Id = userServiceV1.getByLoginName(loginName).getId();
        UserRole userRole = null;
        for (Integer roleId : roleIds) {
            userRole = new UserRole();
            userRole.setUserId(root1Id);
            userRole.setRoleId(roleId);
            userRole.setCreateUser("lj");
            userRole.setUpdateUser("lj");
            userRole.setCreateTime(TimeUtil.ymdHms2date());
            userRole.setUpdateTime(TimeUtil.ymdHms2date());
            userRoleServiceV1.addUserRole(userRole);
        }
    }

    public void addResourceData(String loginName) {
        Long resourceCount = resourceServiceV1.getResourceCount();
        if (resourceCount == 0) {
            //添加资源数据
            Resource resource = null;
            final Integer num = 10;
            for (int i = 0; i < num; i++) {
                resource = new Resource();
                resource.setResourceName("药品管理" + i);
                resource.setResourceUrl("http://localhost:8086/drag_manager");
                resource.setParentId(i == 0 ? 0 : i % 2 == 0 ? 1 : 2);
                resource.setCreateTime(TimeUtil.ymdHms2date());
                resource.setUpdateTime(TimeUtil.ymdHms2date());
                resource.setCreateUser("lj");
                resource.setUpdateUser("lj");
                resourceServiceV1.addResource(resource);
            }
            logger.info("认证数据多余10条，已完成！");
            resourceCount = 10L;
        }else{
            logger.info("认证数据多余1条，无需添加！");
        }

        //把随机资源给予给予角色root1其中一个角色
        Integer root1Id = userServiceV1.getByLoginName(loginName).getId();
        List<Integer> roleIds = userRoleServiceV1.getUserRoleRepository().getRoleIdsByUserId(root1Id);
        if(roleIds == null || roleIds.size() == 0){
            Assert.fail("root1没有任何角色，现在添加角色");
        }else{
            Integer roleId = roleIds.get(0);
            logger.info("给root1用户中角色编号为：【"+roleIds+"】添加资源！");

            //取随机个数作为资源id
            List<Integer> resourceIds = new ArrayList<>();
            for (int i = 0; i < (resourceCount > 2 ? resourceCount - 1 : resourceCount); i++) {
                int randomId = new Random().nextInt(resourceCount.intValue()) + 1;
                resourceIds.add(randomId);
                logger.info("随机资源编号为：【" + randomId + "】重复的会被剔除");
            }
            HashSet<Integer> hashSet = new HashSet<Integer>(resourceIds);
            resourceIds.clear();
            resourceIds.addAll(hashSet);

            //给用户root1取出的角色添加随机资源
            RoleResource roleResource = null;
            for(Integer resourceId:resourceIds){
                roleResource = new RoleResource();
                roleResource.setRoleId(roleId);
                roleResource.setResourceId(resourceId);
                roleResource.setCreateTime(TimeUtil.ymdHms2date());
                roleResource.setUpdateTime(TimeUtil.ymdHms2date());
                roleResource.setUpdateUser("lj");
                roleResource.setCreateUser("lj");
                roleResourceServiceV1.getRoleResourceRepository().save(roleResource);
            }
        }
    }
}
