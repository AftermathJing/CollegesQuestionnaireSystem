package com.github.aftermathjing.questionnaire.common.enumeration;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.Arrays;

@AllArgsConstructor
public enum Required {
    REQUIRED("必答题"),
    OPTIONAL("非必答题"),
    I_DONT_CARE("关我屁事");
    public final String value;

    public static Required get(@NonNull String value) {
        return Arrays.stream(Required.values()).filter(it -> ObjectUtil.equal(value, it.value)).findFirst().orElse(OPTIONAL);
    }

}
