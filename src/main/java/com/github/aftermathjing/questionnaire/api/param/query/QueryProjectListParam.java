package com.github.aftermathjing.questionnaire.api.param.query;

import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
public class QueryProjectListParam {
    int pageNum;
    int pageSize;
    String createdBy;
    String projectName;
    String id;
}
