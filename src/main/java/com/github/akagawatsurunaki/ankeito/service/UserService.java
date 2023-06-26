package com.github.akagawatsurunaki.ankeito.service;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.github.akagawatsurunaki.ankeito.api.param.add.AddUserParam;
import com.github.akagawatsurunaki.ankeito.api.param.delete.DeleteUserParam;
import com.github.akagawatsurunaki.ankeito.api.param.login.UserLoginParam;
import com.github.akagawatsurunaki.ankeito.api.param.modify.ModifyUserParam;
import com.github.akagawatsurunaki.ankeito.api.param.query.QueryUserListParam;
import com.github.akagawatsurunaki.ankeito.api.result.ServiceResult;
import com.github.akagawatsurunaki.ankeito.common.enumeration.QnnreType;
import com.github.akagawatsurunaki.ankeito.common.enumeration.ServiceResultCode;
import com.github.akagawatsurunaki.ankeito.common.enumeration.UserRole;
import com.github.akagawatsurunaki.ankeito.common.enumeration.UserStatus;
import com.github.akagawatsurunaki.ankeito.entity.User;
import com.github.akagawatsurunaki.ankeito.mapper.UserMapper;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }



    /**
     * 获取所有用户列表
     *
     * @return 服务响应结果
     */
    public ServiceResult<List<User>> getUserPageAsList(@NonNull QueryUserListParam queryUserListParam) {
        List<User> result = new ArrayList<>();

        if (StrUtil.isNotBlank(queryUserListParam.getUsername())) {
            val serviceResult = getUserByUsername(queryUserListParam.getUsername());
            val userByUsername = getUserByUsername(queryUserListParam.getUsername()).getData();
            result.add(userByUsername);
            return serviceResult.as(result);
        }

        val allUsers = userMapper.selectList(null);

        return ServiceResult.of(
                ServiceResultCode.OK,
                "共查询到" + allUsers.size() + "条用户信息",
                allUsers);
    }

    public ServiceResult<User> getUserByUsername(@NonNull String username) {
        return Optional.ofNullable(userMapper.selectByUsername(username)).map(
                it -> ServiceResult.ofOK("根据名称" + username + "查询到 1 条用户信息", it)
        ).orElse(ServiceResult.of(ServiceResultCode.NO_SUCH_ENTITY, "没有名为" + username + "的用户"));
    }

    /**
     * 用户登录
     *
     * @param userLoginParam 用户登录参数, 包括用户名和密码
     * @return 服务响应结果
     */
    public ServiceResult<User> userLogin(@NonNull UserLoginParam userLoginParam) {
        val user = userMapper.selectByUsername(userLoginParam.getUsername());

        if (user == null || (!user.getPassword().equals(userLoginParam.getPassword()))) {
            return ServiceResult.of(
                    ServiceResultCode.FAILED,
                    "用户名或密码错误"
            );
        }

        return ServiceResult.of(
                ServiceResultCode.OK,
                "登录成功",
                user
        );
    }

    /**
     * 增加一条用户信息
     *
     * @param addUserParam 用户数据传输对象
     * @return 服务响应结果
     */
    public ServiceResult<User> add(@NonNull AddUserParam addUserParam) {
        val user = User.builder()
                .id(UUID.fastUUID().toString())
                .username(addUserParam.getUsername())
                .password(addUserParam.getPassword())
                .userRole(UserRole.STUDENT)
                .startTime(addUserParam.getStartTime())
                .stopTime(addUserParam.getStopTime())
                .userStatus(UserStatus.DISABLE)
                .build();

        try {
            userMapper.insert(user);

            return ServiceResult.of(
                    ServiceResultCode.OK,
                    "成功增加1条用户信息",
                    user);

        } catch (Exception ignore) {
        }

        return ServiceResult.of(
                ServiceResultCode.FAILED,
                "增加用户信息失败"
        );

    }

    /**
     * 修改一条用户信息
     *
     * @param modifyUserParam 用户数据传输对象
     * @return 服务响应结果
     */
    public ServiceResult<User> modify(@NonNull ModifyUserParam modifyUserParam) {

        val user = User.builder()
                .id(modifyUserParam.getId())
                .username(modifyUserParam.getUsername())
                .password(modifyUserParam.getPassword())
                .userRole(modifyUserParam.getUserRole())
                .startTime(modifyUserParam.getStartTime())
                .stopTime(modifyUserParam.getStopTime())
                .userStatus(modifyUserParam.getUserStatus())
                .build();

        val oldUser = userMapper.selectById(user.getId());
        if (oldUser == null) {
            return ServiceResult.of(
                    ServiceResultCode.NO_SUCH_ENTITY,
                    "此用户不存在",
                    user
            );
        }

        userMapper.updateById(user);
        return ServiceResult.of(
                ServiceResultCode.OK,
                "成功修改1条用户信息",
                user);
    }

    /**
     * 删除一条用户信息
     *
     * @param deleteUserParam 用户数据传输对象
     * @return 服务响应结果
     */
    public ServiceResult<User> delete(@NonNull DeleteUserParam deleteUserParam) {
        val id = deleteUserParam.getId();
        val user = userMapper.selectById(id);
        if (user == null) {
            return ServiceResult.of(
                    ServiceResultCode.NO_SUCH_ENTITY,
                    "此用户不存在");
        }

        userMapper.deleteById(id);
        return ServiceResult.of(
                ServiceResultCode.OK,
                "成功删除1条用户信息",
                user);

    }

}
