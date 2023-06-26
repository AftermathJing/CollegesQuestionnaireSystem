package com.github.akagawatsurunaki.ankeito.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson2.JSONObject;
import com.github.akagawatsurunaki.ankeito.api.param.add.AddProjectParam;
import com.github.akagawatsurunaki.ankeito.api.param.delete.DeleteProjectParam;
import com.github.akagawatsurunaki.ankeito.api.param.modify.ModifyProjectParam;
import com.github.akagawatsurunaki.ankeito.api.param.query.QueryProjectListParam;
import com.github.akagawatsurunaki.ankeito.common.enumeration.ServiceResultCode;
import com.github.akagawatsurunaki.ankeito.entity.Project;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@Transactional
@Rollback
public class ProjectControllerTest {

    static String projectName;
    static String projectContent;
    static String createdBy;
    static String lastUpdatedBy;

    static List<Project> projects;

    @Autowired
    private ProjectController projectController;

    public void init() {
        projectName = RandomUtil.randomString(RandomUtil.BASE_CHAR, 16);
        projectContent = RandomUtil.randomString(RandomUtil.BASE_CHAR, 200);
        createdBy = RandomUtil.randomString(RandomUtil.BASE_CHAR, 8);
        lastUpdatedBy = RandomUtil.randomString(RandomUtil.BASE_CHAR, 8);
    }

    @Test
    @Order(1)
    public void testAddProjectInfo() {
        init();
        var param = new AddProjectParam();
        param.setProjectName(projectName);
        param.setProjectContent(projectContent);
        param.setLastUpdatedBy(lastUpdatedBy);
        param.setCreatedBy(createdBy);
        var httpResponseEntity = projectController.addProjectInfo(param);
        // 断言 insert 成功
        Assertions.assertEquals(String.valueOf(ServiceResultCode.OK.getValue()), httpResponseEntity.getCode());
        Assertions.assertEquals("成功增加1条项目信息", httpResponseEntity.getMessage());
        // 断言 insert 失败
        param = new AddProjectParam();
        httpResponseEntity = projectController.addProjectInfo(param);
        Assertions.assertEquals(String.valueOf(ServiceResultCode.FAILED.getValue()), httpResponseEntity.getCode());
        Assertions.assertEquals("项目信息增加失败", httpResponseEntity.getMessage());
    }


    @Test
    @Order(2)
    @SuppressWarnings("unchecked")
    public void testQueryProjectList() {
        val queryProjectListParam = new QueryProjectListParam();
        val httpResponseEntity = projectController.queryProjectList(queryProjectListParam);
        projects = ((List<Project>) httpResponseEntity.getData());
        Assertions.assertEquals(String.valueOf(ServiceResultCode.OK.getValue()), httpResponseEntity.getCode());
        Assertions.assertEquals("共查询到" + projects.size() + "条项目信息", httpResponseEntity.getMessage());
    }

    @Test
    @Order(3)
    @SuppressWarnings("unchecked")
    public void testModify() {

        val param = new ModifyProjectParam();
        projects = ((List<Project>) projectController.queryProjectList(new QueryProjectListParam()).getData());
        val project = projects.get(0);

        param.setId(project.getId());
        param.setProjectName(projectName);
        param.setProjectContent(RandomUtil.randomString(RandomUtil.BASE_CHAR, 200));

        val expectedProject = Project.builder().id(project.getId()).projectName(projectName).projectContent(param.getProjectContent()).build();

        if (CollUtil.isNotEmpty(projects)) {
            val httpResponseEntity = projectController.modifyProjectInfo(param);
            Assertions.assertEquals(String.valueOf(ServiceResultCode.OK.getValue()), httpResponseEntity.getCode());
            Assertions.assertEquals("成功修改1条项目信息", httpResponseEntity.getMessage());
            Assertions.assertNotEquals(JSONObject.toJSONString(expectedProject), JSONObject.toJSONString(httpResponseEntity.getData()));
        }

        param.setId(UUID.randomUUID().toString());

        val httpResponseEntity = projectController.modifyProjectInfo(param);

        Assertions.assertEquals(String.valueOf(ServiceResultCode.NO_SUCH_ENTITY.getValue()), httpResponseEntity.getCode());
        Assertions.assertEquals("此项目不存在", httpResponseEntity.getMessage());
        Assertions.assertNotEquals(JSONObject.toJSONString(expectedProject), JSONObject.toJSONString(httpResponseEntity.getData()));
    }


    @Test
    @Order(4)
    public void testDelete() {
        val param = new DeleteProjectParam();
        // 删除失败
        param.setId(UUID.randomUUID().toString());
        var httpResponseEntity = projectController.deleteProjectById(param);

        Assertions.assertEquals(String.valueOf(ServiceResultCode.NO_SUCH_ENTITY.getValue()), httpResponseEntity.getCode());
        Assertions.assertEquals("此项目不存在", httpResponseEntity.getMessage());

        if (CollUtil.isNotEmpty(projects)) {
            val project = projects.get(0);
            param.setId(project.getId());
            httpResponseEntity = projectController.deleteProjectById(param);

            Assertions.assertEquals(String.valueOf(ServiceResultCode.OK.getValue()), httpResponseEntity.getCode());
            Assertions.assertEquals("成功删除1条项目信息", httpResponseEntity.getMessage());
        }
    }
}
