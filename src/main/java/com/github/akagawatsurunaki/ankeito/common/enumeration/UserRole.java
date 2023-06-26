package com.github.akagawatsurunaki.ankeito.common.enumeration;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserRole {
    ADMIN(1, "管理员"),
    STUDENT(0, "学生"),
    TEACHER(2, "老师"),
    NO_ROLE(3, "无身份");

    public final int value;
    public final String chinese;

}
