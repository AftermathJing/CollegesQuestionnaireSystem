package com.github.aftermathjing.questionnaire.api.dto;

import com.github.aftermathjing.questionnaire.entity.qnnre.Question;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
public class QuestionDTO {
    Question question;
    List<OptionDTO> optionList;
}
