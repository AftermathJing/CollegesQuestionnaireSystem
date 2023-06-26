package com.github.akagawatsurunaki.ankeito.controller;

import com.github.akagawatsurunaki.ankeito.api.dto.ResponseSheetDTO;
import com.github.akagawatsurunaki.ankeito.api.param.add.AddResponseSheetParam;
import com.github.akagawatsurunaki.ankeito.api.param.query.QueryResponseSheetDetailParam;
import com.github.akagawatsurunaki.ankeito.api.param.query.QueryResponseSheetParam;
import com.github.akagawatsurunaki.ankeito.api.result.HttpResponseEntity;
import com.github.akagawatsurunaki.ankeito.common.convertor.ServiceResultConvertor;
import com.github.akagawatsurunaki.ankeito.service.ResponseService;
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
