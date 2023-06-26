package com.github.aftermathjing.questionnaire.api.param.add;

import lombok.Data;

import java.util.Date;

@Data
public class AddQnnreParam {
    String projectId;
    String name;
    String description;
    Date startTime;
    Date stopTime;
}
