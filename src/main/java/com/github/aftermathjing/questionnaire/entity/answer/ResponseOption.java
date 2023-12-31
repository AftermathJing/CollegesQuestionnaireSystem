package com.github.aftermathjing.questionnaire.entity.answer;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class ResponseOption {

    String responseSheetId;
    /**
     * id 和 questionId是选项的主键
     */
    Integer optionId;
    Integer questionId;
    String qnnreId;
}
