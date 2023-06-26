package com.github.aftermathjing.questionnaire.service;

import com.github.aftermathjing.questionnaire.api.param.add.AddOptionParam;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@Transactional
@Rollback
public class OptionServiceTest {

    @Autowired
    OptionService optionService;

    @Autowired
    QnnreService qnnreService;

    @Test
    public void testAddOptions() {
        val addOptionParam = AddOptionParam.builder().build();
        String[] strs = {"asd", "aaa"};
        addOptionParam.setQnnreId(UUID.randomUUID().toString());
        addOptionParam.setQuestionId(null);
        addOptionParam.setContent(strs);

        var serviceResult = optionService.addOptions(addOptionParam);

        Assertions.assertEquals("选项必须依赖于指定的问题", serviceResult.getMessage());
        addOptionParam.setQuestionId(1);

        serviceResult = optionService.addOptions(addOptionParam);
        Assertions.assertEquals("成功保存选项", serviceResult.getMessage());

        addOptionParam.setContent(null);

        serviceResult = optionService.addOptions(addOptionParam);
        Assertions.assertEquals("必须至少有1个选项", serviceResult.getMessage());
    }

}
