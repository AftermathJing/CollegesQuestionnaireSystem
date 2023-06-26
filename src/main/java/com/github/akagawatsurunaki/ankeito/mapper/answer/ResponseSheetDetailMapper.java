package com.github.akagawatsurunaki.ankeito.mapper.answer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.akagawatsurunaki.ankeito.entity.answer.ResponseSheetDetail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.lang.NonNull;

@Mapper
public interface ResponseSheetDetailMapper extends BaseMapper<ResponseSheetDetail> {

    default long countByQnnreIdAndQuestionId(@NonNull String qnnreId, @NonNull Integer questionId) {
        return selectCount(new QueryWrapper<ResponseSheetDetail>().lambda().eq(ResponseSheetDetail::getQnnreId,
                qnnreId).eq(ResponseSheetDetail::getQuestionId, questionId));
    }
}
