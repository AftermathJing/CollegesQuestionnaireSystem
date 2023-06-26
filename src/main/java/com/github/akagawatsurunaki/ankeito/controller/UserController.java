package com.github.akagawatsurunaki.ankeito.controller;

import com.github.akagawatsurunaki.ankeito.api.param.add.AddUserParam;
import com.github.akagawatsurunaki.ankeito.api.param.delete.DeleteUserParam;
import com.github.akagawatsurunaki.ankeito.api.param.login.UserLoginParam;
import com.github.akagawatsurunaki.ankeito.api.param.modify.ModifyUserParam;
import com.github.akagawatsurunaki.ankeito.api.param.query.QueryUserListParam;
import com.github.akagawatsurunaki.ankeito.api.result.HttpResponseEntity;
import com.github.akagawatsurunaki.ankeito.common.convertor.ServiceResultConvertor;
import com.github.akagawatsurunaki.ankeito.service.UserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试登录界面能否正常运行请
 * <a href="http://127.0.0.1:8080/pages/login/index.html">单击这里</a>
 */
@RestController
@RequestMapping("/admin")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/userLogin", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity userLogin(@RequestBody UserLoginParam userLoginParam) {
        val serviceResult = userService.userLogin(userLoginParam);
        return new ServiceResultConvertor<>(serviceResult).toHttpResponseEntity();
    }

    @RequestMapping(path = "/addUserInfo", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity addUserInfo(@RequestBody AddUserParam addUserParam) {
        val serviceResult = userService.add(addUserParam);
        return new ServiceResultConvertor<>(serviceResult).toHttpResponseEntity();
    }

    @RequestMapping(path = "/modifyUserInfo", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity modifyUser(@RequestBody ModifyUserParam modifyUserParam) {
        val serviceResult = userService.modify(modifyUserParam);
        return new ServiceResultConvertor<>(serviceResult).toHttpResponseEntity();
    }

    @RequestMapping(path = "/deleteUserInfo", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity deleteUser(@RequestBody DeleteUserParam deleteUserParam) {
        val serviceResult = userService.delete(deleteUserParam);
        return new ServiceResultConvertor<>(serviceResult).toHttpResponseEntity();
    }

    @RequestMapping(path = "/queryUserList", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryUserList(@RequestBody QueryUserListParam queryUserListParam) {
        val serviceResult = userService.getUserPageAsList(queryUserListParam);
        return new ServiceResultConvertor<>(serviceResult).toHttpResponseEntity();
    }

}
