package com.github.aftermathjing.questionnaire.api.dto;

import com.github.aftermathjing.questionnaire.entity.qnnre.Qnnre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
public class QnnreDTO {
    Qnnre qnnre;

    List<QuestionDTO> questionDTOList;

}
