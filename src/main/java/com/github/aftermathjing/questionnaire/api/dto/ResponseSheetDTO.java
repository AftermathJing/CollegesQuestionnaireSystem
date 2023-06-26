package com.github.aftermathjing.questionnaire.api.dto;

import com.github.aftermathjing.questionnaire.entity.answer.ResponseSheet;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseSheetDTO {
    ResponseSheet responseSheet;
    QnnreDTO qnnreDTO;
}
