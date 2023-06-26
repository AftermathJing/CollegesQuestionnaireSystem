package com.github.aftermathjing.questionnaire.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.aftermathjing.questionnaire.api.param.query.QueryProjectListParam;
import com.github.aftermathjing.questionnaire.entity.Project;
import lombok.val;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.lang.NonNull;

import java.util.List;

@Mapper
public interface ProjectMapper extends BaseMapper<Project> {

    default List<Project> selectPageByProjectName(@NonNull QueryProjectListParam queryProjectListParam) {
        val projectName = queryProjectListParam.getProjectName();
        val wrapper = new QueryWrapper<Project>().eq("project_name", projectName);
        return this.selectList(wrapper);
    }

}
