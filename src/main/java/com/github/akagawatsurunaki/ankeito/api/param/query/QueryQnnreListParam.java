package com.github.akagawatsurunaki.ankeito.api.param.query;

import lombok.Data;

@Data
public class QueryQnnreListParam {
    /**
     * 当使用id查询时, 通常只会返回一个对象
     */
    String id;
    /**
     * 当存在ProjectID时, 问卷范围被约束在此ProjectID指定的项目下
     */
    String projectId;
}
