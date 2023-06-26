package com.github.aftermathjing.questionnaire.mapper.answer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.aftermathjing.questionnaire.entity.answer.ResponseOption;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.lang.NonNull;

import java.util.List;

@Mapper
public interface ResponseOptionMapper extends BaseMapper<ResponseOption> {

    default List<ResponseOption> selectByResponseSheetId(@NonNull String responseSheetId) {
        return selectList(new QueryWrapper<ResponseOption>().lambda().eq(ResponseOption::getResponseSheetId,
                responseSheetId));
    }

    default long countByQnnreIdAndQuestionIdAndOptionId(@NonNull String qnnreId,
                                                        @NonNull Integer questionId,
                                                        @NonNull Integer optionId) {
        return selectCount(new QueryWrapper<ResponseOption>().lambda()
                .eq(ResponseOption::getQnnreId, qnnreId)
                .eq(ResponseOption::getQuestionId, questionId)
                .eq(ResponseOption::getOptionId, optionId));
    }

    default List<ResponseOption> selectByQnnreIdAndQuestionId(@NonNull String qnnreId,
                                                              @NonNull Integer questionId) {
        return selectList(new QueryWrapper<ResponseOption>().lambda()
                .eq(ResponseOption::getQnnreId, qnnreId)
                .eq(ResponseOption::getQuestionId, questionId));

    }

}
