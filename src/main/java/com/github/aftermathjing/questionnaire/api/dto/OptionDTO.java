package com.github.aftermathjing.questionnaire.api.dto;

import com.github.aftermathjing.questionnaire.entity.qnnre.Option;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class OptionDTO {
    Option option;
    @Getter
    boolean selected = false;
}
