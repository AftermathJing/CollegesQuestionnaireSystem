package com.github.akagawatsurunaki.ankeito.controller;

import com.github.akagawatsurunaki.ankeito.api.param.add.AddOptionParam;
import com.github.akagawatsurunaki.ankeito.api.result.HttpResponseEntity;
import com.github.akagawatsurunaki.ankeito.common.convertor.ServiceResultConvertor;
import com.github.akagawatsurunaki.ankeito.service.OptionService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/option")
public class OptionController {

    OptionService optionService;
    @Autowired
    OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @RequestMapping(path = "/addOptions", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity addOptions(@RequestBody AddOptionParam addOptionParam) {
        val serviceResult = optionService.addOptions(addOptionParam);
        return new ServiceResultConvertor<>(serviceResult).toHttpResponseEntity();
    }

}
