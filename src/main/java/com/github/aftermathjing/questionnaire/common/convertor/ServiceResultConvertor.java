package com.github.aftermathjing.questionnaire.common.convertor;

import com.github.aftermathjing.questionnaire.api.result.HttpResponseEntity;
import com.github.aftermathjing.questionnaire.api.result.ServiceResult;

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
