package com.github.aftermathjing.questionnaire.mapper.qnnre;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.aftermathjing.questionnaire.entity.qnnre.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.List;

@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

    default List<Question> selectByQnnrId(@NonNull String qnnreId) {
        return selectList(Wrappers.<Question>query().lambda().eq(Question::getQnnreId, qnnreId));
    }

    default Question selectByQnnreIdAndQuestionId(@NonNull String qnnreId, @NonNull Integer questionId) {
        return selectOne(new QueryWrapper<Question>().lambda().eq(Question::getId, questionId).eq(Question::getQnnreId,
                qnnreId));
    }

    default List<Question> selectByQnnreIds(@NonNull Collection<String> qnnreIds) {
        return selectList(new QueryWrapper<Question>().lambda().in(Question::getQnnreId, qnnreIds));
    }
}
