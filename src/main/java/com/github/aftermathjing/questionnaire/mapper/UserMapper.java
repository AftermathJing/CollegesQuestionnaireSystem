package com.github.aftermathjing.questionnaire.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.aftermathjing.questionnaire.entity.User;
import lombok.val;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.lang.NonNull;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    default User selectByUsername(@NonNull String username) {
        val wrapper = new QueryWrapper<User>().eq("username", username);
        val users = this.selectList(wrapper);
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }
}
