package com.github.aftermathjing.questionnaire.common.enumeration;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserStatus {
    ENABLE(1, "启用"),
    DISABLE(0, "不启用");

    final int value;
    final String chinese;

}
