package com.github.akagawatsurunaki.ankeito.common.enumeration;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum QnnreStatus {
    DRAFT("草稿"),  // 草稿状态
    PUBLISHED("已发布"),  // 已发布状态
    CLOSED("已关闭"),
    DELETED("已删除");  // 已关闭状态

    public final String value;  // 状态名称

}
