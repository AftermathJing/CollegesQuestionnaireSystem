package com.github.aftermathjing.questionnaire.controller;

import com.github.aftermathjing.questionnaire.api.dto.ResponseSheetDTO;
import com.github.aftermathjing.questionnaire.api.param.add.AddResponseSheetParam;
import com.github.aftermathjing.questionnaire.api.param.query.QueryResponseSheetDetailParam;
import com.github.aftermathjing.questionnaire.api.param.query.QueryResponseSheetParam;
import com.github.aftermathjing.questionnaire.api.result.HttpResponseEntity;
import com.github.aftermathjing.questionnaire.common.convertor.ServiceResultConvertor;
import com.github.aftermathjing.questionnaire.service.ResponseService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/response")
public class ResponseController {

    ResponseService responseService;

    @Autowired
    ResponseController(ResponseService responseService) {
        this.responseService = responseService;
    }

    @RequestMapping(path = "/getResponseSheet", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity getResponseSheet(@RequestBody QueryResponseSheetParam queryResponseSheetParam) {
        val serviceResult = responseService.getResponseSheet(queryResponseSheetParam);
        return new ServiceResultConvertor<>(serviceResult).toHttpResponseEntity();
    }

    @RequestMapping(path = "/createEmptyResponseSheet", method = RequestMethod.POST, headers = "Accept=application" +
            "/json")
    public HttpResponseEntity createEmptyResponseSheet(@RequestBody AddResponseSheetParam addResponseSheetParam) {
        val serviceResult = responseService.createEmptyResponseSheet(addResponseSheetParam);
        return new ServiceResultConvertor<>(serviceResult).toHttpResponseEntity();
    }

    @RequestMapping(path = "/getResponseSheetDetail", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity getResponseSheetDetail(@RequestBody QueryResponseSheetDetailParam queryResponseSheetDetailParam) {
        val serviceResult = responseService.getResponseSheetDetail(queryResponseSheetDetailParam);
        return new ServiceResultConvertor<>(serviceResult).toHttpResponseEntity();
    }

    @RequestMapping(path = "/submitResponseSheet", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity submitResponseSheet(@RequestBody ResponseSheetDTO sheetDTO) {
        val serviceResult = responseService.submitResponseSheetDTO(sheetDTO);
        return new ServiceResultConvertor<>(serviceResult).toHttpResponseEntity();
    }
}
