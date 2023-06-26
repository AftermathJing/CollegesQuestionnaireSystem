package com.github.akagawatsurunaki.ankeito.service;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.akagawatsurunaki.ankeito.api.param.add.AddOptionParam;
import com.github.akagawatsurunaki.ankeito.api.result.ServiceResult;
import com.github.akagawatsurunaki.ankeito.common.enumeration.ServiceResultCode;
import com.github.akagawatsurunaki.ankeito.entity.qnnre.Option;
import com.github.akagawatsurunaki.ankeito.entity.qnnre.Question;
import com.github.akagawatsurunaki.ankeito.mapper.qnnre.OptionMapper;
import com.github.akagawatsurunaki.ankeito.mapper.qnnre.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OptionService {
    OptionMapper optionMapper;
    QuestionMapper questionMapper;

    @Autowired
    OptionService(OptionMapper optionMapper, QuestionMapper questionMapper) {
        this.optionMapper = optionMapper;
        this.questionMapper = questionMapper;
    }

    private void deleteOptions(@NonNull Integer questionId) {
        questionMapper.delete(Wrappers.<Question>query().lambda().eq(Question::getId, questionId));
    }

    @Deprecated
    public ServiceResult<List<Option>> addOptions(@NonNull AddOptionParam addOptionParam) {
        try {
            List<Option> options = new ArrayList<>();
            // 如果 Question 已经存在了, 那么先删除之
            Optional.ofNullable(addOptionParam.getQuestionId()).ifPresent(this::deleteOptions);
            // 随后再更新选项
            Optional.ofNullable(addOptionParam.getContent()).ifPresentOrElse(
                    contents -> Arrays.stream(contents).forEach(
                            content -> options.add(
                                    Option.builder()
                                            .id(ArrayUtil.indexOf(contents, content))
                                            .content(content)
                                            .questionId(Optional.ofNullable(addOptionParam.getQuestionId()).orElseThrow(() -> new IllegalArgumentException("选项必须依赖于指定的问题")))
                                            .qnnreId(addOptionParam.getQnnreId())
                                            .build()
                            )
                    ),
                    () -> {
                        throw new IllegalArgumentException("必须至少有1个选项");
                    }
            );
            options.forEach(optionMapper::insert);
            return ServiceResult.ofOK("成功保存选项", options);
        } catch (IllegalArgumentException e) {
            return ServiceResult.of(ServiceResultCode.ILLEGAL_PARAM, e.getMessage());
        }
    }

}
