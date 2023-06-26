package com.github.aftermathjing.questionnaire.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    String id;
    String personInCharge;
    String projectName;
    String projectContent;
    String createdBy;
    String lastUpdatedBy;
    Date createTime;
    Date lastUpdateDate;
}
