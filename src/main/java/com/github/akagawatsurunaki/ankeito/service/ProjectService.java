package com.github.akagawatsurunaki.ankeito.service;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.akagawatsurunaki.ankeito.api.param.add.AddProjectParam;
import com.github.akagawatsurunaki.ankeito.api.param.delete.DeleteProjectParam;
import com.github.akagawatsurunaki.ankeito.api.param.modify.ModifyProjectParam;
import com.github.akagawatsurunaki.ankeito.api.param.query.QueryProjectListParam;
import com.github.akagawatsurunaki.ankeito.api.result.ServiceResult;
import com.github.akagawatsurunaki.ankeito.common.enumeration.ServiceResultCode;
import com.github.akagawatsurunaki.ankeito.entity.Project;
import com.github.akagawatsurunaki.ankeito.mapper.ProjectMapper;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    ProjectMapper projectMapper;

    @Autowired
    ProjectService(ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }

    /**
     * 查询项目列表
     *
     * @param queryProjectListParam 查询项目列表参数，包括分页参数
     * @return 服务响应结果，包括按照分页查询到的项目列表
     */
    public ServiceResult<List<Project>> getProjectPageAsList(@NonNull QueryProjectListParam queryProjectListParam) {
        List<Project> records = new ArrayList<>();

        if (StrUtil.isNotBlank(queryProjectListParam.getId())) {
            val project = projectMapper.selectById(queryProjectListParam.getId());
            Optional.ofNullable(project).ifPresent(records::add);
            return ServiceResult.of(
                    ServiceResultCode.OK,
                    "共查询到" + records.size() + "条项目信息",
                    records
            );
        }

        // 是否执行分页查询
        if (queryProjectListParam.getPageNum() != 0 && queryProjectListParam.getPageSize() != 0) {
            val projectPage = projectMapper.selectPage(
                    new Page<>(queryProjectListParam.getPageNum(), queryProjectListParam.getPageSize()),
                    null
            );
            records = projectPage.getRecords();
        } else {
            // 不执行分页查询
            if (StrUtil.isNotBlank(queryProjectListParam.getProjectName())) {
                return getProjectsByName(queryProjectListParam);
            }
            records = projectMapper.selectList(null);
        }

        return ServiceResult.of(
                ServiceResultCode.OK,
                "共查询到" + records.size() + "条项目信息",
                records
        );
    }

    /**
     * 根据项目名称查询
     *
     * @param queryProjectListParam 查询项目列表参数，包括分页参数，要查询的项目名称
     * @return 服务响应结果，包括按照分页、指定项目名称查询到的项目列表
     */
    public ServiceResult<List<Project>> getProjectsByName(@NonNull QueryProjectListParam queryProjectListParam) {
        val projectName = queryProjectListParam.getProjectName();
        if (projectName == null) {
            return ServiceResult.of(
                    ServiceResultCode.ILLEGAL_PARAM,
                    "项目名称不能为空"
            );
        }
        val records = projectMapper.selectPageByProjectName(queryProjectListParam);
        return ServiceResult.of(
                ServiceResultCode.OK,
                "共查询到" + records.size() + "条项目信息",
                records
        );
    }

    /**
     * 添加项目
     *
     * @param addProjectParam 添加项目参数
     * @return 服务响应结果，包括将要被添加的Project对象
     */
    public ServiceResult<Project> addProject(@NonNull AddProjectParam addProjectParam) {
        val project = Project.builder()
                .id(UUID.fastUUID().toString())
                // TODO: 用户校验
                .personInCharge("TODO")
                .projectName(addProjectParam.getProjectName())
                .projectContent(addProjectParam.getProjectContent())
                .createdBy(addProjectParam.getCreatedBy())
                .lastUpdatedBy(addProjectParam.getLastUpdatedBy())
                .createTime(new Date())
                .lastUpdateDate(new Date()).build();

        try {
            projectMapper.insert(project);

            return ServiceResult.of(
                    ServiceResultCode.OK,
                    "成功增加1条项目信息",
                    project);
        } catch (Exception ignore) {
        }

        return ServiceResult.of(ServiceResultCode.FAILED, "项目信息增加失败");
    }

    /**
     * 删除一条项目信息
     *
     * @param deleteProjectParam 删除项目参数
     * @return 服务响应结果，包括将被删除的Project对象
     */
    public ServiceResult<Project> deleteProject(@NonNull DeleteProjectParam deleteProjectParam) {
        val id = deleteProjectParam.getId();
        val project = projectMapper.selectById(id);
        if (project == null) {
            return ServiceResult.of(
                    ServiceResultCode.NO_SUCH_ENTITY,
                    "此项目不存在");
        }

        projectMapper.deleteById(id);

        return ServiceResult.of(
                ServiceResultCode.OK,
                "成功删除1条项目信息",
                project);

    }

    public ServiceResult<Project> modifyProject(@NonNull ModifyProjectParam modifyProjectParam) {
        val id = modifyProjectParam.getId();
        val project = projectMapper.selectById(id);
        if (project == null) {
            return ServiceResult.of(
                    ServiceResultCode.NO_SUCH_ENTITY,
                    "此项目不存在");
        }

        // 修改属性
        project.setProjectContent(modifyProjectParam.getProjectContent());
        project.setProjectName(modifyProjectParam.getProjectName());

        projectMapper.updateById(project);
        return ServiceResult.of(
                ServiceResultCode.OK,
                "成功修改1条项目信息",
                project);
    }

}
