package com.github.aftermathjing.questionnaire.api.dto;

import com.github.aftermathjing.questionnaire.entity.answer.ResponseSheet;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class ResponseSheetDTO {
    ResponseSheet responseSheet;
    QnnreDTO qnnreDTO;
}
