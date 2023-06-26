package com.github.aftermathjing.questionnaire.api.param.add;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class AddQuestionParam {
    String qnnreId;
    Integer index;
    String problemName;
    Boolean mustAnswer;
    String type;
}
