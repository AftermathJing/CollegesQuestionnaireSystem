package com.github.akagawatsurunaki.ankeito.api.param.query;

import lombok.Data;

@Data
public class QueryUserListParam {
    int pageNum;
    int pageSize;
    String username;
}
