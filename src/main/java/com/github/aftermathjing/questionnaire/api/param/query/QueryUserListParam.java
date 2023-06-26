package com.github.aftermathjing.questionnaire.api.param.query;

import lombok.Data;

@Data
public class QueryUserListParam {
    int pageNum;
    int pageSize;
    String username;
}
