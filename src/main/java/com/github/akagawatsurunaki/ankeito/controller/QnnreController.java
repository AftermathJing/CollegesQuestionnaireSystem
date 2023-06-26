package com.github.akagawatsurunaki.ankeito.controller;

import com.github.akagawatsurunaki.ankeito.api.param.add.AddQnnreParam;
import com.github.akagawatsurunaki.ankeito.api.param.delete.DeleteQnnreParam;
import com.github.akagawatsurunaki.ankeito.api.param.modify.ModifyQnnreParam;
import com.github.akagawatsurunaki.ankeito.api.param.query.QueryQnnreListParam;
import com.github.akagawatsurunaki.ankeito.api.result.HttpResponseEntity;
import com.github.akagawatsurunaki.ankeito.common.convertor.ServiceResultConvertor;
import com.github.akagawatsurunaki.ankeito.service.QnnreService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QnnreController {
    private final QnnreService qnnreService;

    @Autowired
    public QnnreController(QnnreService qnnreService) {
        this.qnnreService = qnnreService;
    }

    @RequestMapping(path = "/queryUserRole", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryUserRole() {
        val serviceResult = qnnreService.getQnnreType();
        return new ServiceResultConvertor<>(serviceResult).toHttpResponseEntity();
    }

    @RequestMapping(path = "/addQnnre", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity addQnnre(@RequestBody AddQnnreParam addQnnreParam) {
        val serviceResult = qnnreService.addQnnre(addQnnreParam);
        return new ServiceResultConvertor<>(serviceResult).toHttpResponseEntity();
    }

    @RequestMapping(path = "/getQnnre", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity getQnnre(@RequestBody QueryQnnreListParam queryQnnreListParam) {
        val serviceResult = qnnreService.getQnnreById(queryQnnreListParam);
        return new ServiceResultConvertor<>(serviceResult).toHttpResponseEntity();
    }

    @RequestMapping(path = "/getQnnresExcludeDeletedQnnre", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity getQnnresExcludeDeletedQnnre(@RequestBody QueryQnnreListParam queryQnnreListParam) {
        val serviceResult = qnnreService.getQnnresExcludeDeletedQnnre(queryQnnreListParam);
        return new ServiceResultConvertor<>(serviceResult).toHttpResponseEntity();
    }

    @RequestMapping(path = "/deleteQnnre", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity deleteQnnre(@RequestBody DeleteQnnreParam deleteQnnreParam) {
        val serviceResult = qnnreService.softDeleteQnnre(deleteQnnreParam);
        return new ServiceResultConvertor<>(serviceResult).toHttpResponseEntity();
    }

    @RequestMapping(path = "/modifyQuestionnaire", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity modifyQuestionnaire(@RequestBody ModifyQnnreParam modifyQnnreParam) {
        val serviceResult = qnnreService.save(modifyQnnreParam);
        return new ServiceResultConvertor<>(serviceResult).toHttpResponseEntity();
    }

    @RequestMapping(path = "/publishQnnre", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity publishQnnre(@RequestBody ModifyQnnreParam modifyQnnreParam) {
        val serviceResult = qnnreService.publishQnnre(modifyQnnreParam);
        return new ServiceResultConvertor<>(serviceResult).toHttpResponseEntity();
    }
}
