package com.github.aftermathjing.questionnaire.api.param.add;

import lombok.Data;

@Data
public class AddProjectParam {
    String createdBy;
    String lastUpdatedBy;
    String projectName;
    String projectContent;
}
