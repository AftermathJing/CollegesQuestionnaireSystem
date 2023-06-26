package com.github.akagawatsurunaki.ankeito.api.param.modify;

import com.github.akagawatsurunaki.ankeito.api.param.add.AddOptionParam;
import com.github.akagawatsurunaki.ankeito.api.param.add.AddQuestionParam;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class ModifyQnnreParam {

    AddQuestionParam[] addQuestionParams;

    AddOptionParam[] addOptionParams;

    String qnnreId;
    String qnnreTitle;
    String qnnreDescription;

}
