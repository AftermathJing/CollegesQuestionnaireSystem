package com.github.akagawatsurunaki.ankeito.service;

import cn.hutool.core.collection.CollUtil;
import com.github.akagawatsurunaki.ankeito.api.param.add.AddProjectParam;
import com.github.akagawatsurunaki.ankeito.api.param.delete.DeleteProjectParam;
import com.github.akagawatsurunaki.ankeito.api.param.modify.ModifyProjectParam;
import com.github.akagawatsurunaki.ankeito.api.param.query.QueryProjectListParam;
import com.github.akagawatsurunaki.ankeito.api.result.ServiceResult;
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

@SpringBootTest
public class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;

    @Test
    @Order(1)
    public void testAddProject() {
        // 构造添加项目的参数
        AddProjectParam addProjectParam = new AddProjectParam();
        addProjectParam.setProjectName("test");
        addProjectParam.setProjectContent("test content");
        addProjectParam.setCreatedBy("test");
        addProjectParam.setLastUpdatedBy("test");
        ServiceResult<Project> result = projectService.addProject(addProjectParam);
        Assertions.assertEquals(ServiceResultCode.OK, result.getCode(), "返回结果状态码应该为 OK");
        Assertions.assertNotNull(result.getData(), "返回结果数据不应该为空");
        Assertions.assertEquals(addProjectParam.getProjectName(), result.getData().getProjectName(),
                "返回结果中的项目名称应该与添加的参数一致");

        // 添加失败的异常
        projectService.addProject(new AddProjectParam());
    }

    @Test
    @Order(2)
    public void testGetProjectPageAsList() {
        // 构造查询所有项目的参数
        QueryProjectListParam queryProjectListParam = new QueryProjectListParam();
        ServiceResult<List<Project>> result = projectService.getProjectPageAsList(queryProjectListParam);
        Assertions.assertEquals(ServiceResultCode.OK, result.getCode(), "返回结果状态码应该为 OK");
        Assertions.assertNotNull(result.getData(), "返回结果数据不应该为空");
        Assertions.assertTrue(result.getData().size() >= 0, "所有项目数量应该大于等于0");

        projectService.getProjectPageAsList(new QueryProjectListParam());

        queryProjectListParam.setPageNum(1);
        projectService.getProjectPageAsList(queryProjectListParam);

        queryProjectListParam.setPageSize(1);
        projectService.getProjectPageAsList(queryProjectListParam);

        queryProjectListParam = new QueryProjectListParam();
        queryProjectListParam.setPageNum(0);
        queryProjectListParam.setPageSize(0);
        queryProjectListParam.setProjectName("123");
        queryProjectListParam.setCreatedBy("55665");

        projectService.getProjectPageAsList(queryProjectListParam);
    }

    @Test
    @Order(3)
    public void testGetProjectsByName() {
        // 构造不带分页的查询参数
        QueryProjectListParam queryProjectListParam = new QueryProjectListParam();
        queryProjectListParam.setProjectName("test");
        ServiceResult<List<Project>> result = projectService.getProjectsByName(queryProjectListParam);
        Assertions.assertEquals(ServiceResultCode.OK, result.getCode(), "返回结果状态码应该为 OK");
        Assertions.assertNotNull(result.getData(), "返回结果数据不应该为空");
        Assertions.assertTrue(result.getData().size() >= 0, "查询到的项目数量应该大于等于0");

        projectService.getProjectsByName(new QueryProjectListParam());
        queryProjectListParam.setPageNum(1);
        queryProjectListParam.setPageSize(111);
        projectService.getProjectsByName(queryProjectListParam);
    }

    @Test
    @Order(4)
    public void testModifyProject() {
        // 先添加一个项目用于测试更新操作
        AddProjectParam addProjectParam = new AddProjectParam();
        addProjectParam.setProjectName("test");
        addProjectParam.setProjectContent("test content");
        addProjectParam.setCreatedBy("test");
        addProjectParam.setLastUpdatedBy("test");
        ServiceResult<Project> addResult = projectService.addProject(addProjectParam);
        Assertions.assertEquals(ServiceResultCode.OK, addResult.getCode(), "添加项目操作应该成功");

        // 构造更新项目的参数
        ModifyProjectParam modifyProjectParam = new ModifyProjectParam();
        modifyProjectParam.setId(addResult.getData().getId());
        modifyProjectParam.setProjectName("updated test");
        modifyProjectParam.setProjectContent("updated content");
        ServiceResult<Project> modifyResult = projectService.modifyProject(modifyProjectParam);
        Assertions.assertEquals(ServiceResultCode.OK, modifyResult.getCode(), "返回结果状态码应该为 OK");
        Assertions.assertNotNull(modifyResult.getData(), "返回结果数据不应该为空");
        Assertions.assertEquals(modifyProjectParam.getProjectName(), modifyResult.getData().getProjectName(),
                "返回结果中的项目名称应该与更新的参数一致");
        Assertions.assertEquals(modifyProjectParam.getProjectContent(), modifyResult.getData().getProjectContent(),
                "返回结果中的项目内容应该与更新的参数一致");

        modifyProjectParam = new ModifyProjectParam();
        modifyProjectParam.setId("456564464");
        modifyProjectParam.setProjectName("TEst");
        modifyProjectParam.setProjectContent("askjdfhasjikhdfjkaesh");
        projectService.modifyProject(modifyProjectParam);
    }

    @Test
    @Order(5)
    public void testDeleteProject() {
        // 先添加一个项目用于测试删除操作
        AddProjectParam addProjectParam = new AddProjectParam();
        addProjectParam.setProjectName("test");
        addProjectParam.setProjectContent("test content");
        addProjectParam.setCreatedBy("test");
        addProjectParam.setLastUpdatedBy("test");
        ServiceResult<Project> addResult = projectService.addProject(addProjectParam);
        Assertions.assertEquals(ServiceResultCode.OK, addResult.getCode(), "添加项目操作应该成功");

        // 构造删除项目的参数
        DeleteProjectParam deleteProjectParam = new DeleteProjectParam();
        deleteProjectParam.setId(addResult.getData().getId());
        ServiceResult<Project> deleteResult = projectService.deleteProject(deleteProjectParam);
        Assertions.assertEquals(ServiceResultCode.OK, deleteResult.getCode(), "返回结果状态码应该为 OK");
        Assertions.assertNotNull(deleteResult.getData(), "返回结果数据不应该为空");
        Assertions.assertEquals(addResult.getData().getId(), deleteResult.getData().getId(), "返回结果中的项目ID应该与被删除的项目一致");

        deleteProjectParam.setId("000");
        projectService.deleteProject(deleteProjectParam);

        val projectPageAsList = projectService.getProjectPageAsList(new QueryProjectListParam());
        val project = projectPageAsList.getData().get(0);
        val deleteProjectParam1 = new DeleteProjectParam();
        deleteProjectParam1.setId(project.getId());
        projectService.deleteProject(deleteProjectParam1);
        new Thread(() -> {
            projectService.deleteProject(deleteProjectParam1);
        }).start();

        val qParam = new QueryProjectListParam();
        qParam.setProjectName("test");
        val test = projectService.getProjectsByName(qParam);
        val param = new DeleteProjectParam();
        val projects = test.getData();
        if (CollUtil.isNotEmpty(projects)) {
            param.setId(projects.get(0).getId());
            val projectServiceResult = projectService.deleteProject(param);
            Assertions.assertEquals(ServiceResultCode.OK, projectServiceResult.getCode());
        }


    }


}
