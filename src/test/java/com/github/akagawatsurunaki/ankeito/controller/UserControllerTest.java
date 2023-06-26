package com.github.akagawatsurunaki.ankeito.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson2.JSONObject;
import com.github.akagawatsurunaki.ankeito.api.param.add.AddUserParam;
import com.github.akagawatsurunaki.ankeito.api.param.delete.DeleteUserParam;
import com.github.akagawatsurunaki.ankeito.api.param.login.UserLoginParam;
import com.github.akagawatsurunaki.ankeito.api.param.modify.ModifyUserParam;
import com.github.akagawatsurunaki.ankeito.api.param.query.QueryUserListParam;
import com.github.akagawatsurunaki.ankeito.api.result.ServiceResult;
import com.github.akagawatsurunaki.ankeito.common.convertor.ServiceResultConvertor;
import com.github.akagawatsurunaki.ankeito.common.enumeration.ServiceResultCode;
import com.github.akagawatsurunaki.ankeito.common.enumeration.UserRole;
import com.github.akagawatsurunaki.ankeito.common.enumeration.UserStatus;
import com.github.akagawatsurunaki.ankeito.entity.User;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@SpringBootTest
@Transactional
@Rollback
public class UserControllerTest {
    @Autowired
    UserController userController;
    static String username;
    static String password;
    static Date startTime;
    static Date stopTime;
    static List<User> users;


    @Test
    public void init() {
        username = RandomUtil.randomString(RandomUtil.BASE_CHAR, 5);
        startTime = RandomUtil.randomDate(new Date(), DateField.SECOND, Integer.MIN_VALUE, Integer.MAX_VALUE);
        stopTime = RandomUtil.randomDate(new Date(), DateField.SECOND, Integer.MIN_VALUE, Integer.MAX_VALUE);
        password = RandomUtil.randomString(RandomUtil.BASE_CHAR_NUMBER, 16);
    }

    @Test
    @Order(1)
    public void testAddUserInfo() {
        init();
        var param = new AddUserParam();
        param.setUsername(username);
        param.setPassword(password);
        param.setStartTime(startTime);
        param.setStopTime(stopTime);
        var httpResponseEntity = userController.addUserInfo(param);
        // 断言 insert 成功
        Assertions.assertEquals(String.valueOf(ServiceResultCode.OK.getValue()), httpResponseEntity.getCode());
        Assertions.assertEquals("成功增加1条用户信息", httpResponseEntity.getMessage());
        // 断言 insert 失败
        param = new AddUserParam();
        httpResponseEntity = userController.addUserInfo(param);
        Assertions.assertEquals(String.valueOf(ServiceResultCode.FAILED.getValue()), httpResponseEntity.getCode());
        Assertions.assertEquals("增加用户信息失败", httpResponseEntity.getMessage());

    }

    @Test
    @Order(2)
    public void testUserLogin() {
        val param = new UserLoginParam();
        param.setUsername(username);
        param.setPassword(password);
        var httpResponseEntity = userController.userLogin(param);
        // 登录成功
        Assertions.assertEquals(String.valueOf(ServiceResultCode.OK.getValue()), httpResponseEntity.getCode());
        // 登录失败(用户名/密码错误)

        // 用户名错误
        param.setUsername(UUID.randomUUID().toString());
        httpResponseEntity = userController.userLogin(param);
        Assertions.assertEquals(String.valueOf(ServiceResultCode.FAILED.getValue()), httpResponseEntity.getCode());

        // 密码错误
        param.setUsername(username);
        param.setPassword(UUID.randomUUID().toString());
        Assertions.assertEquals(String.valueOf(ServiceResultCode.FAILED.getValue()), httpResponseEntity.getCode());
    }

    @Test
    @Order(3)
    @SuppressWarnings("unchecked")
    public void testQueryUserList() {
        val queryUserListParam = new QueryUserListParam();
        val httpResponseEntity = userController.queryUserList(queryUserListParam);
        users = ((List<User>) httpResponseEntity.getData());
        Assertions.assertEquals(String.valueOf(ServiceResultCode.OK.getValue()), httpResponseEntity.getCode());
        Assertions.assertEquals("共查询到" + users.size() + "条用户信息", httpResponseEntity.getMessage());
    }

    @Test
    @Order(4)
    @SuppressWarnings("unchecked")
    public void testModifyUser() {
        val param = new ModifyUserParam();
        users = ((List<User>) userController.queryUserList(new QueryUserListParam()).getData());
        val user = users.get(0);

        param.setId(user.getId());
        param.setUsername(username);
        param.setPassword(password);
        param.setStartTime(RandomUtil.randomDate(new Date(), DateField.SECOND, Integer.MIN_VALUE, 0));
        param.setStopTime(RandomUtil.randomDate(new Date(), DateField.SECOND, 0, Integer.MAX_VALUE));
        param.setUserRole(RandomUtil.randomEle(UserRole.values()));
        param.setUserStatus(RandomUtil.randomEle(UserStatus.values()));

        val expectedUser = User.builder()
                .id(user.getId())
                .username(param.getUsername())
                .password(param.getPassword())
                .userRole(param.getUserRole())
                .startTime(param.getStartTime())
                .stopTime(param.getStopTime())
                .userStatus(param.getUserStatus())
                .build();

        if (CollUtil.isNotEmpty(users)) {
            val httpResponseEntity = userController.modifyUser(param);
            Assertions.assertEquals(String.valueOf(ServiceResultCode.OK.getValue()), httpResponseEntity.getCode());
            Assertions.assertEquals("成功修改1条用户信息", httpResponseEntity.getMessage());
            Assertions.assertEquals(JSONObject.toJSONString(expectedUser), JSONObject.toJSONString(httpResponseEntity.getData()));
        }

        param.setId(UUID.randomUUID().toString());

        val httpResponseEntity = userController.modifyUser(param);

        Assertions.assertEquals(String.valueOf(ServiceResultCode.NO_SUCH_ENTITY.getValue()), httpResponseEntity.getCode());
        Assertions.assertEquals("此用户不存在", httpResponseEntity.getMessage());
        Assertions.assertNotEquals(JSONObject.toJSONString(expectedUser), JSONObject.toJSONString(httpResponseEntity.getData()));
    }

    @Test
    @Order(5)
    public void testDeleteUser() {
        val param = new DeleteUserParam();
        // 删除失败
        param.setId(UUID.randomUUID().toString());
        var httpResponseEntity = userController.deleteUser(param);

        Assertions.assertEquals(String.valueOf(ServiceResultCode.NO_SUCH_ENTITY.getValue()), httpResponseEntity.getCode());
        Assertions.assertEquals("此用户不存在", httpResponseEntity.getMessage());

        if (CollUtil.isNotEmpty(users)) {
            val user = users.get(0);
            param.setId(user.getId());
            httpResponseEntity = userController.deleteUser(param);

            Assertions.assertEquals(String.valueOf(ServiceResultCode.OK.getValue()), httpResponseEntity.getCode());
            Assertions.assertEquals("成功删除1条用户信息", httpResponseEntity.getMessage());
        }
    }

}
