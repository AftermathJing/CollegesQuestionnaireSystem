package com.github.aftermathjing.questionnaire.api.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
public class QuestionStatisticDTO {
    Integer questionId;
    String questionName;
    Integer questionCount;
    List<InnerOption> optionList;

}

