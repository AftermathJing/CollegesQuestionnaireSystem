package com.github.aftermathjing.questionnaire.service;

import cn.hutool.core.util.RandomUtil;
import com.github.aftermathjing.questionnaire.api.param.add.AddUserParam;
import com.github.aftermathjing.questionnaire.api.param.delete.DeleteUserParam;
import com.github.aftermathjing.questionnaire.api.param.login.UserLoginParam;
import com.github.aftermathjing.questionnaire.api.param.modify.ModifyUserParam;
import com.github.aftermathjing.questionnaire.api.param.query.QueryUserListParam;
import com.github.aftermathjing.questionnaire.common.enumeration.ServiceResultCode;
import com.github.aftermathjing.questionnaire.common.enumeration.UserRole;
import com.github.aftermathjing.questionnaire.common.enumeration.UserStatus;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@SpringBootTest
@Transactional
@Rollback
public class UserServiceTest {

    static String username = "测试人员";
    static String password = "1145141919810";
    @Autowired
    UserService userService;

    @Test
    @Order(1)
    public void testAdd() {
        try {
            val param = new AddUserParam();
            param.setUsername(username);
            param.setPassword(password);
            param.setStartTime(new Date());
            param.setStopTime(new Date(1749985432 * 1000L));
            userService.add(param);
            userService.add(new AddUserParam());
        } catch (Exception ignore) {

        }
    }

    @Test
    @Order(2)
    public void testUserLogin() {
        try {
            var param = new UserLoginParam();
            param.setUsername(username);
            param.setPassword(password);
            var serviceResult = userService.userLogin(param);
            Assertions.assertEquals(ServiceResultCode.FAILED, serviceResult.getCode());

            param.setUsername("不存在用户");
            param.setPassword("123456");
            serviceResult = userService.userLogin(param);
            Assertions.assertEquals(ServiceResultCode.FAILED, serviceResult.getCode());
            Assertions.assertEquals("用户名或密码错误", serviceResult.getMessage());

            param = new UserLoginParam();
            param.setUsername(username);
            param.setPassword("123456888");
            serviceResult = userService.userLogin(param);
            Assertions.assertEquals(ServiceResultCode.FAILED, serviceResult.getCode());
            Assertions.assertEquals("用户名或密码错误", serviceResult.getMessage());
        } catch (Exception ignore) {

        }

    }

    @Test
    @Order(3)
    public void testModify() {
        try {
            var param = new ModifyUserParam();
            val userByUsername = userService.getUserByUsername(username).getData();

            param.setUserRole(RandomUtil.randomEle(UserRole.values()));
            param.setUserStatus(RandomUtil.randomEle(UserStatus.values()));
            param.setPassword("1919810555");
            param.setStartTime(new Date());
            param.setStopTime(new Date(1899985432 * 1000L));

            if (userByUsername != null) {
                param.setId(userByUsername.getId());
                param.setUsername(userByUsername.getUsername());

                val serviceResult = userService.modify(param);
                Assertions.assertEquals(ServiceResultCode.OK, serviceResult.getCode());
                Assertions.assertEquals("成功修改1条用户信息", serviceResult.getMessage());
            }

            param.setId(UUID.randomUUID().toString());
            val serviceResult = userService.modify(param);
            Assertions.assertEquals(ServiceResultCode.NO_SUCH_ENTITY, serviceResult.getCode());
            Assertions.assertEquals("此用户不存在", serviceResult.getMessage());
        } catch (Exception ignore) {
        }
    }

    @Test
    @Order(4)
    public void testGetUserPageAsList() {
        try {
            val param = new QueryUserListParam();
            param.setPageNum(1);
            param.setPageSize(1);
            param.setUsername(username);
            val serviceResult = userService.getUserPageAsList(param);
            Assertions.assertEquals(ServiceResultCode.NO_SUCH_ENTITY, serviceResult.getCode());
        } catch (Exception ignore) {
        }
    }


    @Test
    @Order(5)
    public void testGetUserByUsername() {
        try {
            val queryUserListParam = new QueryUserListParam();
            queryUserListParam.setUsername(username);
            val serviceResult = userService.getUserPageAsList(queryUserListParam);
            Assertions.assertEquals(ServiceResultCode.NO_SUCH_ENTITY, serviceResult.getCode());
        } catch (Exception ignore) {
        }
    }

    @Test
    @Order(6)
    public void testDeleteUser() {
        try {
            val param = new DeleteUserParam();
            val user = userService.getUserByUsername(username).getData();
            param.setId(user.getId());
            val serviceResult = userService.delete(param);
            Assertions.assertEquals(ServiceResultCode.OK, serviceResult.getCode());
        } catch (Exception ignore) {
        }
    }

}
