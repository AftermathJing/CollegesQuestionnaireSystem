package com.github.aftermathjing.questionnaire.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ServiceResultCode {
    OK(666),
    FAILED(0),
    NO_SUCH_ENTITY(0),
    DTO_TO_ENTITY_EXCEPTION(0),
    ILLEGAL_PARAM(0),

    METHOD_DEPRECATED(-1)
    ;

    @Getter
    final int value;
}
