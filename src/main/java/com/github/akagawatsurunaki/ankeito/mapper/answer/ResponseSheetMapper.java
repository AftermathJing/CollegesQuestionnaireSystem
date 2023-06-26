package com.github.akagawatsurunaki.ankeito.mapper.answer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.akagawatsurunaki.ankeito.entity.answer.ResponseSheet;
import com.github.akagawatsurunaki.ankeito.entity.qnnre.Qnnre;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.lang.NonNull;

import java.util.List;

@Mapper
public interface ResponseSheetMapper extends BaseMapper<ResponseSheet> {
    default List<ResponseSheet> selectByQnnreId(@NonNull String qnnreId) {
        return selectList(Wrappers.<ResponseSheet>query().lambda().eq(ResponseSheet::getQnnreId, qnnreId));
    }

}
