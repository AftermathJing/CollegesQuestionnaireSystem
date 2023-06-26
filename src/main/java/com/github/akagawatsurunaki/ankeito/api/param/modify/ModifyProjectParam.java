package com.github.akagawatsurunaki.ankeito.api.param.modify;

import lombok.Data;

@Data
public class ModifyProjectParam {
    String id;
    String projectName;
    String projectContent;
}
