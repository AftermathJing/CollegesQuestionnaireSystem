package com.github.aftermathjing.questionnaire.api.param.add;

import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.Date;

@Data
@Jacksonized
public class AddQnnreParam {
    String projectId;
    String name;
    String description;
    Date startTime;
    Date stopTime;
}
