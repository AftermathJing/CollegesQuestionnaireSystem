package com.github.akagawatsurunaki.ankeito.entity;

import cn.hutool.core.date.DateField;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson2.JSONObject;
import com.github.akagawatsurunaki.ankeito.common.enumeration.UserRole;
import com.github.akagawatsurunaki.ankeito.common.enumeration.UserStatus;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

import static com.github.akagawatsurunaki.ankeito.common.constant.StringValue.BASE_NAME;
import static com.github.akagawatsurunaki.ankeito.common.constant.StringValue.BASE_STRING;

@SpringBootTest
@Transactional
@Rollback
public class UserTest {

    /**
     * 随机生成一个用户对象
     *
     * @return 随机生成的用户对象
     */
    private User genUserByBuilder() {
        return User.builder().id(UUID.randomUUID().toString()).userRole(RandomUtil.randomEle(UserRole.values())).userStatus(RandomUtil.randomEle(UserStatus.values())).startTime(RandomUtil.randomDate(new Date(), DateField.SECOND, Integer.MIN_VALUE, Integer.MAX_VALUE)).stopTime(RandomUtil.randomDate(new Date(), DateField.SECOND, Integer.MIN_VALUE, Integer.MAX_VALUE)).password(RandomUtil.randomString(BASE_STRING, 16)).username(RandomUtil.randomString(BASE_NAME, 4)).lastUpdatedBy(RandomUtil.randomString(BASE_NAME, 4)).lastUpdateTime(RandomUtil.randomDate(new Date(), DateField.SECOND, Integer.MIN_VALUE, Integer.MAX_VALUE)).createdBy(RandomUtil.randomString(BASE_NAME, 4)).creationTime(RandomUtil.randomDate(new Date(), DateField.SECOND, Integer.MIN_VALUE, Integer.MAX_VALUE)).build();
    }

    private User genUserBySetters() {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUserRole(RandomUtil.randomEle(UserRole.values()));
        user.setUserStatus(RandomUtil.randomEle(UserStatus.values()));
        user.setStartTime(RandomUtil.randomDate(new Date(), DateField.SECOND, Integer.MIN_VALUE, Integer.MAX_VALUE));
        user.setStopTime(RandomUtil.randomDate(new Date(), DateField.SECOND, Integer.MIN_VALUE, Integer.MAX_VALUE));
        user.setPassword(RandomUtil.randomString(BASE_STRING, 16));
        user.setUsername(RandomUtil.randomString(BASE_NAME, 4));
        user.setLastUpdatedBy(RandomUtil.randomString(BASE_NAME, 4));
        user.setLastUpdateTime(RandomUtil.randomDate(new Date(), DateField.SECOND, Integer.MIN_VALUE, Integer.MAX_VALUE));
        user.setCreatedBy(RandomUtil.randomString(BASE_NAME, 4));
        user.setCreationTime(RandomUtil.randomDate(new Date(), DateField.SECOND, Integer.MIN_VALUE, Integer.MAX_VALUE));
        return user;
    }

    @Test
    public void testEquals() {
        val userByBuilder = genUserByBuilder();
        val userBySetters = genUserBySetters();

        Assertions.assertNotEquals(userByBuilder, userBySetters);

        Assertions.assertNotEquals(JSONObject.toJSONString(userByBuilder), JSONObject.toJSONString(userBySetters));
        Assertions.assertEquals(JSONObject.toJSONString(userByBuilder), JSONObject.toJSONString(userByBuilder));
        Assertions.assertEquals(JSONObject.toJSONString(userBySetters), JSONObject.toJSONString(userBySetters));
    }

    @Test
    public void testEquals2() {
        // 测试相同的对象是否相等
        val user1 = genUserByBuilder();
        val user2 = user1;
        Assertions.assertEquals(user1, user2);

        // 测试不同对象之间的相等性
        // 构造两个不同的用户对象
        val user3 = genUserByBuilder();
        val user4 = genUserByBuilder();
        // 用户名不相同，其他都相同
        user4.setUsername(user3.getUsername() + "RANDOM");
        Assertions.assertNotEquals(user3, user4);

        // 测试与null之间的相等性
        Assertions.assertNotEquals(user3, null);

        // 测试与其他类型的对象之间的相等性
        Assertions.assertNotEquals(user3, new Object());

        // 测试用FastJSON序列化后是否相等
        val user5 = genUserByBuilder();
        val user6 = new User();
        user6.setId(user5.getId());
        user6.setUserRole(user5.getUserRole());
        user6.setUserStatus(user5.getUserStatus());
        user6.setStartTime(user5.getStartTime());
        user6.setStopTime(user5.getStopTime());
        user6.setPassword(user5.getPassword());
        user6.setUsername(user5.getUsername());
        user6.setLastUpdatedBy(user5.getLastUpdatedBy());
        user6.setLastUpdateTime(user5.getLastUpdateTime());
        user6.setCreatedBy(user5.getCreatedBy());
        user6.setCreationTime(user5.getCreationTime());
        // user5和user6除了引用不同，其他完全相同
        Assertions.assertEquals(JSONObject.toJSONString(user5), JSONObject.toJSONString(user6));
    }

    @Test
    public void testToString() {
        val userByBuilder = genUserByBuilder();
        val userBySetters = genUserBySetters();
        System.out.println("userBySetters = " + userBySetters);
        System.out.println("userByBuilder = " + userByBuilder);
    }

}
