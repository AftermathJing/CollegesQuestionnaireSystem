package com.github.aftermathjing.questionnaire.api.result;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class HttpResponseEntity {
    String code;
    String message;
    Object data;
}
