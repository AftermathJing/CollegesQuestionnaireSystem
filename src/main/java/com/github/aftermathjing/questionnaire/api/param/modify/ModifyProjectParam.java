package com.github.aftermathjing.questionnaire.api.param.modify;

import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
public class ModifyProjectParam {
    String id;
    String projectName;
    String projectContent;
}
