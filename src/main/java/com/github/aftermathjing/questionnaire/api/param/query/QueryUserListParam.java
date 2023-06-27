package com.github.aftermathjing.questionnaire.api.param.query;

import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
public class QueryUserListParam {
    int pageNum;
    int pageSize;
    String username;
}
