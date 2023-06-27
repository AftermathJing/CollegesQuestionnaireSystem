package com.github.aftermathjing.questionnaire.entity.qnnre;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.github.aftermathjing.questionnaire.common.enumeration.QnnreStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.springframework.stereotype.Repository;

import java.util.Date;


/**
 * 问卷实体类
 *
 * @author aftermarhjing
 */
@Data
@Builder
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
public class Qnnre {

    /**
     * 问卷ID
     */
    @TableId(type = IdType.INPUT)
    String id;
    /**
     * 项目ID
     */
    String projectId;
    /**
     * 问卷名称
     */
    String name;
    /**
     * 问卷描述
     */
    String description;
    /**
     * 开始时间
     */
    Date startTime;
    /**
     * 结束时间
     */
    Date stopTime;
    /**
     * 问卷状态
     */
    QnnreStatus qnnreStatus;
}