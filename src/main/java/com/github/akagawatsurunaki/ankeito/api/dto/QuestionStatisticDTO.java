package com.github.akagawatsurunaki.ankeito.api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionStatisticDTO {
    Integer questionId;
    String questionName;
    Integer questionCount;
    List<InnerOption> optionList;

}

