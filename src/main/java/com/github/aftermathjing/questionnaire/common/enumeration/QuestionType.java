package com.github.aftermathjing.questionnaire.common.enumeration;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum QuestionType {
    SINGLE_CHOICE_QUESTION("单选题"),
    MULTIPLE_CHOICE_QUESTION("多选题"),
    I_DONT_CARE("关我屁事");

    public final String value ;
}
