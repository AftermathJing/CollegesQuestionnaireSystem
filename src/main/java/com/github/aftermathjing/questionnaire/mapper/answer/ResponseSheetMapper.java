package com.github.aftermathjing.questionnaire.mapper.answer;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.aftermathjing.questionnaire.entity.answer.ResponseSheet;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.lang.NonNull;

import java.util.List;

@Mapper
public interface ResponseSheetMapper extends BaseMapper<ResponseSheet> {
    default List<ResponseSheet> selectByQnnreId(@NonNull String qnnreId) {
        return selectList(Wrappers.<ResponseSheet>query().lambda().eq(ResponseSheet::getQnnreId, qnnreId));
    }

}
