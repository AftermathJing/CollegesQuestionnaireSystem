package com.github.aftermathjing.questionnaire.api.param.add;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class AddOptionParam {
    Integer questionId;
    String qnnreId;
    String[] content;
}
