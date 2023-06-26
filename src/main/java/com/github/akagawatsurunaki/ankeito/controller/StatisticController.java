package com.github.akagawatsurunaki.ankeito.controller;

import com.github.akagawatsurunaki.ankeito.api.param.query.QueryProjectParam;
import com.github.akagawatsurunaki.ankeito.api.param.query.QueryStatisticParam;
import com.github.akagawatsurunaki.ankeito.api.result.HttpResponseEntity;
import com.github.akagawatsurunaki.ankeito.common.convertor.ServiceResultConvertor;
import com.github.akagawatsurunaki.ankeito.service.StatisticService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistic")
public class StatisticController {

    StatisticService statisticService;

    @Autowired
    StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @RequestMapping(path = "/getQuestionStatistic", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity getQuestionStatistic(@RequestBody QueryStatisticParam queryStatisticParam) {
        val serviceResult = statisticService.getQuestionStatistic(queryStatisticParam);
        return new ServiceResultConvertor<>(serviceResult).toHttpResponseEntity();
    }

    @RequestMapping(path = "/getSameQuestionsStatistic", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity getSameQuestionsStatistic(@RequestBody QueryProjectParam queryProjectParam) {
        val serviceResult = statisticService.getSameQuestionStatistic(queryProjectParam);
        return new ServiceResultConvertor<>(serviceResult).toHttpResponseEntity();
    }
}
