package com.github.aftermathjing.questionnaire.api.param.modify;

import com.github.aftermathjing.questionnaire.common.enumeration.UserRole;
import com.github.aftermathjing.questionnaire.common.enumeration.UserStatus;
import com.github.aftermathjing.questionnaire.api.param.add.AddUserParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ModifyUserParam extends AddUserParam {
    String id;
    UserRole userRole;
    UserStatus userStatus;

}
