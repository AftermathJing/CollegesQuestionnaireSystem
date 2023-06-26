package com.github.akagawatsurunaki.ankeito.api.param.query;

import lombok.Data;

@Data
public class QueryProjectListParam {
    int pageNum;
    int pageSize;
    String createdBy;
    String projectName;
    String id;
}
