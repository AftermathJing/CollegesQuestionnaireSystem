package com.github.akagawatsurunaki.ankeito.param;

import com.github.akagawatsurunaki.ankeito.api.param.add.AddUserParam;
import com.github.akagawatsurunaki.ankeito.api.param.modify.ModifyUserParam;
import com.github.akagawatsurunaki.ankeito.common.enumeration.UserRole;
import com.github.akagawatsurunaki.ankeito.common.enumeration.UserStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback
public class ModifyUserParamTest {

    @Test
    public void testEqualsAndHashCode() {
        String id = "1234567890abcdef";
        String username = "testUser";
        String password = "testPassword";
        UserRole userRole = UserRole.ADMIN;
        UserStatus userStatus = UserStatus.ENABLE;

        AddUserParam addUserParam = new AddUserParam();
        addUserParam.setUsername(username);
        addUserParam.setPassword(password);

        ModifyUserParam param1 = new ModifyUserParam();
        param1.setId(id);
        param1.setUserRole(userRole);
        param1.setUserStatus(userStatus);

        ModifyUserParam param2 = new ModifyUserParam();
        param2.setId(id);
        param2.setUserRole(userRole);
        param2.setUserStatus(userStatus);

        ModifyUserParam param3 = new ModifyUserParam();
        param3.setId("abcdef1234567890");
        param3.setUserRole(userRole);
        param3.setUserStatus(userStatus);

        Assertions.assertEquals(param1, param2);
        Assertions.assertNotEquals(param1, param3);
        Assertions.assertEquals(param1.hashCode(), param2.hashCode());
        Assertions.assertNotEquals(param1.hashCode(), param3.hashCode());

        // 测试能否正确判断不同类的对象
        Assertions.assertNotEquals(param1, addUserParam);
    }

    @Test
    public void testToString() {
        String id = "1234567890abcdef";
        UserRole userRole = UserRole.ADMIN;
        UserStatus userStatus = UserStatus.ENABLE;

        ModifyUserParam param = new ModifyUserParam();
        param.setId(id);
        param.setUserRole(userRole);
        param.setUserStatus(userStatus);
        System.out.println("param = " + param);

    }


}
