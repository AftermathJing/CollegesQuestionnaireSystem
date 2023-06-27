package com.github.aftermathjing.questionnaire.api.param.add;

import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
public class AddProjectParam {
    String createdBy;
    String lastUpdatedBy;
    String projectName;
    String projectContent;
}
