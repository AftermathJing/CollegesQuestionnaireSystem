package com.github.aftermathjing.questionnaire.controller;

import com.github.aftermathjing.questionnaire.service.ProjectService;
import com.github.aftermathjing.questionnaire.api.param.add.AddProjectParam;
import com.github.aftermathjing.questionnaire.api.param.delete.DeleteProjectParam;
import com.github.aftermathjing.questionnaire.api.param.modify.ModifyProjectParam;
import com.github.aftermathjing.questionnaire.api.param.query.QueryProjectListParam;
import com.github.aftermathjing.questionnaire.api.result.HttpResponseEntity;
import com.github.aftermathjing.questionnaire.common.convertor.ServiceResultConvertor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {

    ProjectService projectService;

    @Autowired
    ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @RequestMapping(path = "/queryProjectList", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryProjectList(@Validated @RequestBody QueryProjectListParam queryProjectListParam) {
        val serviceResult = projectService.getProjectPageAsList(queryProjectListParam);
        return new ServiceResultConvertor<>(serviceResult).toHttpResponseEntity();
    }

    @RequestMapping(path = "/addProjectInfo", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity addProjectInfo(@Validated @RequestBody AddProjectParam addProjectParam) {
        val serviceResult = projectService.addProject(addProjectParam);
        return new ServiceResultConvertor<>(serviceResult).toHttpResponseEntity();
    }

    @RequestMapping(path = "/modifyProjectInfo", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity modifyProjectInfo(@Validated @RequestBody ModifyProjectParam modifyProjectParam) {
        val serviceResult = projectService.modifyProject(modifyProjectParam);
        return new ServiceResultConvertor<>(serviceResult).toHttpResponseEntity();
    }

    @RequestMapping(path = "/deleteProjectById", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity deleteProjectById(@Validated @RequestBody DeleteProjectParam deleteProjectParam) {
        val serviceResult = projectService.deleteProject(deleteProjectParam);
        return new ServiceResultConvertor<>(serviceResult).toHttpResponseEntity();
    }

}
