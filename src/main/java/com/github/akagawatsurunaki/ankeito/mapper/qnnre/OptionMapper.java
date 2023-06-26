package com.github.akagawatsurunaki.ankeito.mapper.qnnre;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.akagawatsurunaki.ankeito.entity.qnnre.Option;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.lang.NonNull;

import java.util.List;

@Mapper
public interface OptionMapper extends BaseMapper<Option> {

    default List<Option> selectByQuestionId(@NonNull Integer questionId) {
        return selectList(Wrappers.<Option>query().lambda().eq(Option::getQuestionId, questionId));
    }

    default List<Option> selectByQnnreIdAndQuestionId(@NonNull String qnnreId, @NonNull Integer questionId) {
        return selectList(Wrappers.<Option>query().lambda().eq(Option::getQnnreId, qnnreId).eq(Option::getQuestionId,
                questionId));
    }

    default Option selectByQnnreIdAndQuestionIdAndOptionId(@NonNull String qnnreId, @NonNull Integer questionId
            , @NonNull Integer optionId) {
        return selectOne(Wrappers.<Option>query().lambda().eq(Option::getQnnreId, qnnreId).eq(Option::getQuestionId,
                questionId).eq(Option::getId, optionId));
    }

}
