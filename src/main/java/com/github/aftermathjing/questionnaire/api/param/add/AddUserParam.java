package com.github.aftermathjing.questionnaire.api.param.add;

import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.Date;

@Data
@Jacksonized
public class AddUserParam {
    String username;
    String password;
    Date startTime;
    Date stopTime;
}
