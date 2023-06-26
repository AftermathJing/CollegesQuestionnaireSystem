package com.github.akagawatsurunaki.ankeito.common.convertor;

import com.github.akagawatsurunaki.ankeito.api.result.HttpResponseEntity;
import com.github.akagawatsurunaki.ankeito.api.result.ServiceResult;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;

public class ServiceResultConvertor<Data> {

    private final ServiceResult<Data> serviceResult;

    public ServiceResultConvertor(ServiceResult<Data> serviceResult) {
        this.serviceResult = serviceResult;
    }

    public HttpResponseEntity toHttpResponseEntity() {
        return HttpResponseEntity
                .builder()
                .code(String.valueOf(serviceResult.getCode().getValue()))
                .message(serviceResult.getMessage())
                .data(serviceResult.getData())
                .build();
    }

}
