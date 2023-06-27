package com.github.aftermathjing.questionnaire.api.param.login;

import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
public class UserLoginParam {
    String username;
    String password;
}
