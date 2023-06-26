package com.github.aftermathjing.questionnaire.api.param.add;

import lombok.Data;

import java.util.Date;

@Data
public class AddUserParam {
    String username;
    String password;
    Date startTime;
    Date stopTime;
}
