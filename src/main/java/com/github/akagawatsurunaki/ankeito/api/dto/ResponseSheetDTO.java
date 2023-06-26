package com.github.akagawatsurunaki.ankeito.api.dto;

import com.github.akagawatsurunaki.ankeito.entity.answer.ResponseSheet;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseSheetDTO {
    ResponseSheet responseSheet;
    QnnreDTO qnnreDTO;
}
