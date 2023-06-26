package com.github.aftermathjing.questionnaire.api.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Builder
@Data
@Jacksonized
public class InnerOption {
    Integer optionId;
    String optionContent;
    Float percent;
    Integer count;
}
