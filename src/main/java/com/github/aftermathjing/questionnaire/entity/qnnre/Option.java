package com.github.aftermathjing.questionnaire.entity.qnnre;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.springframework.stereotype.Repository;

@Data
@Builder
@Jacksonized
@TableName("`option`")
public class Option {
    Integer id;
    Integer questionId;
    String content;
    String qnnreId;
}
