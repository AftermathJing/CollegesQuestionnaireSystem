package com.github.aftermathjing.questionnaire.entity.qnnre;

import com.github.aftermathjing.questionnaire.common.enumeration.QuestionType;
import com.github.aftermathjing.questionnaire.common.enumeration.Required;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@FieldNameConstants
public class Question {
    Integer id;
    String qnnreId;
    String content;
    Required required;
    QuestionType type;
}
