package com.github.akagawatsurunaki.ankeito.entity.answer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseSheetDetail {

    /**
     * 答卷ID(主键)
     */
    String responseSheetId;

    /**
     * 问卷ID
     */

    String qnnreId;
    /**
     * 问题ID 为了防止有其他类型的问题
     */

    Integer questionId;

}
