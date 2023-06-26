package com.github.aftermathjing.questionnaire.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.aftermathjing.questionnaire.api.dto.OptionDTO;
import com.github.aftermathjing.questionnaire.api.dto.QnnreDTO;
import com.github.aftermathjing.questionnaire.api.dto.QuestionDTO;
import com.github.aftermathjing.questionnaire.api.param.add.AddOptionParam;
import com.github.aftermathjing.questionnaire.api.param.add.AddQnnreParam;
import com.github.aftermathjing.questionnaire.api.param.add.AddQuestionParam;
import com.github.aftermathjing.questionnaire.api.param.delete.DeleteQnnreParam;
import com.github.aftermathjing.questionnaire.api.param.modify.ModifyQnnreParam;
import com.github.aftermathjing.questionnaire.api.param.query.QueryQnnreListParam;
import com.github.aftermathjing.questionnaire.api.result.ServiceResult;
import com.github.aftermathjing.questionnaire.common.enumeration.*;
import com.github.aftermathjing.questionnaire.entity.qnnre.Option;
import com.github.aftermathjing.questionnaire.entity.qnnre.Qnnre;
import com.github.aftermathjing.questionnaire.entity.qnnre.Question;
import com.github.aftermathjing.questionnaire.mapper.qnnre.OptionMapper;
import com.github.aftermathjing.questionnaire.mapper.qnnre.QnnreMapper;
import com.github.aftermathjing.questionnaire.mapper.qnnre.QuestionMapper;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 问卷服务类，提供查询问卷类型和添加问卷功能
 */
@Service
public class QnnreService {

    QnnreMapper qnnreMapper;
    QuestionMapper questionMapper;
    OptionMapper optionMapper;

    /**
     * 使用QnnreMapper进行依赖注入
     *
     * @param qnnreMapper QnnreMapper对象
     */
    @Autowired
    QnnreService(QnnreMapper qnnreMapper,
                 QuestionMapper questionMapper,
                 OptionMapper optionMapper) {
        this.qnnreMapper = qnnreMapper;
        this.questionMapper = questionMapper;
        this.optionMapper = optionMapper;
    }

    /**
     * 获取所有问卷类型
     *
     * @return 返回ServiceResult对象，其中包含查询结果和执行结果信息
     */
    public ServiceResult<List<String>> getQnnreType() {
        val data = Arrays.stream(QnnreType.values())
                .map(it -> it.value).toList();
        return ServiceResult.ofOK("查询到" + data.size() + "项目类型", data);
    }

    /**
     * 添加问卷
     *
     * @param addQnnreParam 添加问卷所需参数
     * @return 返回ServiceResult对象，其中包含添加的问卷对象和执行结果信息
     */
    public ServiceResult<Qnnre> addQnnre(@NonNull AddQnnreParam addQnnreParam) {
        try {
            val qnnre = Qnnre.builder()
                    .id(UUID.randomUUID().toString())
                    .name(Optional.ofNullable(addQnnreParam.getName()).orElse("未命名"))
                    .projectId(Optional.ofNullable(addQnnreParam.getProjectId()).orElseThrow(() -> new IllegalArgumentException("项目ID必须被指定")))
                    .description(Optional.ofNullable(addQnnreParam.getDescription()).orElse("未填写调查说明"))
                    .startTime(Optional.ofNullable(addQnnreParam.getStartTime()).orElse(new Date()))
                    // 新建的问卷默认为草稿状态
                    .qnnreStatus(QnnreStatus.DRAFT)
                    // 如果用户没填写, 自动设定结束时间为次月
                    .stopTime(Optional.ofNullable(addQnnreParam.getStopTime()).orElse(DateUtil.nextMonth())).build();
            qnnreMapper.insert(qnnre);
            return ServiceResult.ofOK("问卷创建成功", qnnre);

        } catch (IllegalArgumentException e) {
            return ServiceResult.of(ServiceResultCode.ILLEGAL_PARAM, e.getMessage());
        }
    }

    /**
     * 根据问卷ID获取问卷对象
     *
     * @param queryQnnreListParam 查询问卷所需参数
     * @return 返回ServiceResult对象，其中包含执行结果信息和查询的问卷对象（如果存在）
     */
    public ServiceResult<Qnnre> getQnnreById(@NonNull QueryQnnreListParam queryQnnreListParam) {
        return Optional.ofNullable(queryQnnreListParam.getId())
                .map(id -> qnnreMapper.selectById(id))
                .map(value -> ServiceResult.ofOK("查询到指定的问卷", value))
                .orElseGet(() -> ServiceResult.of(ServiceResultCode.NO_SUCH_ENTITY, "问卷不存在"));
    }

    /**
     * 根据查询参数获取问卷列表，其中去掉了被标记为删除的问卷。
     *
     * @param queryQnnreListParam 查询参数
     * @return 返回一个 ServiceResult<List<Qnnre>> 对象，其中包含查询到的问卷列表和相关信息。
     * 如果查询参数中的项目名为空或者不存在对应的问卷，会返回一个包含错误代码和错误消息的 ServiceResult 对象。
     */
    public ServiceResult<List<Qnnre>> getQnnresExcludeDeletedQnnre(@NonNull QueryQnnreListParam queryQnnreListParam) {
        try {
            val qnnres =
                    qnnreMapper.selectByProjectId(Optional.ofNullable(queryQnnreListParam.getProjectId()).orElseThrow(() -> new IllegalArgumentException("项目名必须被指定")));
            // 去除掉其中被标记为删除的问卷
            val result =
                    qnnres.stream().filter(qnnre -> ObjectUtil.notEqual(QnnreStatus.DELETED, qnnre.getQnnreStatus())).toList();
            return ServiceResult.ofOK("成功查询到" + result.size() + "份问卷", result);
        } catch (IllegalArgumentException e) {
            return ServiceResult.of(ServiceResultCode.ILLEGAL_PARAM, e.getMessage());
        }
    }

    /**
     * 发布问卷的服务方法。将指定ID的问卷状态更新为已发布。
     *
     * @param modifyQnnreParam 修改问卷参数对象，包含待更新问卷的ID。
     * @return 返回一个ServiceResult对象，表示服务执行结果。成功时返回包含“成功发布问卷”消息的ServiceResult对象，失败时返回包含错误码和错误消息的ServiceResult对象。
     */
    public ServiceResult<Object> publishQnnre(@NonNull ModifyQnnreParam modifyQnnreParam) {
        try {
            qnnreMapper.updateQnnreStatusById(
                    Optional.ofNullable(modifyQnnreParam.getQnnreId()).orElseThrow(() -> new IllegalArgumentException("问卷ID必须被指定")),
                    QnnreStatus.PUBLISHED);
            return ServiceResult.ofOK("成功发布问卷");
        } catch (IllegalArgumentException e) {
            return ServiceResult.of(ServiceResultCode.ILLEGAL_PARAM, e.getMessage());
        }
    }


    /**
     * 根据指定的问卷ID删除问卷及其下的所有问题和选项。
     *
     * @param deleteQnnreParam 删除问卷参数，包括问卷ID。
     * @return 删除结果 ServiceResult<QnnreDTO>，返回值中带有提示信息。
     */
    public ServiceResult<QnnreDTO> hardDeleteQnnre(@NonNull DeleteQnnreParam deleteQnnreParam) {
        try {
            val qnnreDTOServiceResult = get(deleteQnnreParam.getQnnreId());
            Optional.ofNullable(qnnreDTOServiceResult.getData()).ifPresentOrElse(
                    qnnre -> {
                        qnnreMapper.deleteById(qnnre.getQnnre());
                        qnnre.getQuestionDTOList().forEach(questionDTO -> {
                            questionMapper.deleteById(questionDTO.getQuestion());
                            questionDTO.getOptionList().forEach(option -> optionMapper.deleteById(option.getOption()));
                        });
                    }, () -> {
                        throw new NullPointerException("删除失败, 问卷不存在");
                    }
            );
            return qnnreDTOServiceResult.with("问卷删除成功");
        } catch (NullPointerException e1) {
            return ServiceResult.of(ServiceResultCode.NO_SUCH_ENTITY, e1.getMessage());
        }
    }

    /**
     * 问卷软删除服务，将指定问卷的状态设置为 DELETED，并且保留其数据记录。
     */
    public ServiceResult<Object> softDeleteQnnre(@NonNull DeleteQnnreParam deleteQnnreParam) {
        return Optional.ofNullable(deleteQnnreParam.getQnnreId()).flatMap(
                qnnreId ->
                        Optional.ofNullable(qnnreMapper.selectById(qnnreId)).map(
                                qnnre -> {
                                    if (qnnre.getQnnreStatus().equals(QnnreStatus.PUBLISHED)) {
                                        return ServiceResult.of(ServiceResultCode.FAILED, "问卷删除失败：不能删除一个正在进行的问卷");
                                    }
                                    qnnreMapper.updateQnnreStatusById(qnnreId, QnnreStatus.DELETED);
                                    return ServiceResult.ofOK("问卷删除成功");
                                }
                        )
        ).orElse(ServiceResult.of(ServiceResultCode.FAILED, "内部服务器异常"));
    }

    /**
     * 根据指定的问卷ID清除该问卷下的所有问题和选项，不包含问卷本身。
     *
     * @param deleteQnnreParam 清空问卷参数，包括问卷ID。
     * @return 清空结果 ServiceResult<QnnreDTO>，返回值中带有提示信息。
     */
    public ServiceResult<QnnreDTO> clearQnnre(@NonNull DeleteQnnreParam deleteQnnreParam) {
        try {
            val qnnreDTOServiceResult = get(deleteQnnreParam.getQnnreId());
            Optional.ofNullable(qnnreDTOServiceResult.getData()).ifPresentOrElse(
                    qnnre -> {
                        qnnre.getQuestionDTOList().forEach(questionDTO -> {
                            questionMapper.deleteById(questionDTO.getQuestion());
                            questionDTO.getOptionList().forEach(option -> optionMapper.deleteById(option.getOption()));
                        });
                    }, () -> {
                        throw new NullPointerException("删除失败, 问卷不存在");
                    }
            );
            return qnnreDTOServiceResult.with("问卷删除成功");
        } catch (NullPointerException e1) {
            return ServiceResult.of(ServiceResultCode.NO_SUCH_ENTITY, e1.getMessage());
        }
    }

    /**
     * 保存并修改一个问卷，先清空原有的问卷内容，再根据新的参数重建问卷及其下的问题和选项。
     *
     * @param modifyQnnreParam 修改问卷参数，包括问卷ID、名称、描述、问题列表和选项列表等。
     * @return 修改结果 ServiceResult<QnnreDTO>，返回值中带有提示信息。
     */
    public ServiceResult<QnnreDTO> save(@NonNull ModifyQnnreParam modifyQnnreParam) {
        try {
            // 试图清空原有的问卷内容
            val deleteQnnreParam = new DeleteQnnreParam();
            deleteQnnreParam.setQnnreId(modifyQnnreParam.getQnnreId());
            clearQnnre(deleteQnnreParam);
            // 尝试修改问卷的名称和描述
            val modifyQnnreServiceResult = modifyQnnre(modifyQnnreParam.getQnnreId(),
                    modifyQnnreParam.getQnnreTitle(),
                    modifyQnnreParam.getQnnreDescription());
            // 如果修改失败, 则服务终止
            if (ObjectUtil.notEqual(ServiceResultCode.OK, modifyQnnreServiceResult.getCode())) {
                return modifyQnnreServiceResult;
            }
            val addQuestionParams =
                    Optional.ofNullable(modifyQnnreParam.getAddQuestionParams()).orElseThrow(() -> new NullPointerException("问题参数列表不能为空"));
            Arrays.stream(addQuestionParams).forEach(addQuestionParam -> {
                addQuestionParam.setQnnreId(modifyQnnreParam.getQnnreId());
                QnnreService.this.addMultipleChoiceQuestion(addQuestionParam);
            });
            Arrays.stream(modifyQnnreParam.getAddOptionParams()).forEach(this::addOptions);
            return get(modifyQnnreParam.getQnnreId()).with("问卷修改保存成功");
        } catch (IllegalArgumentException | NullPointerException e) {
            return ServiceResult.of(ServiceResultCode.ILLEGAL_PARAM, e.getMessage());
        }
    }

    /**
     * 根据指定的问卷ID获取问卷及其下所有问题和选项。
     *
     * @param qnnreId 问卷ID。
     * @return 获取结果 ServiceResult<QnnreDTO>，返回值中带有问卷及其下的问题和选项信息。
     */
    public ServiceResult<QnnreDTO> get(@NonNull String qnnreId) {
        try {
            List<QuestionDTO> questionDTOList = new ArrayList<>();
            val qnnre =
                    Optional.ofNullable(qnnreMapper.selectById(qnnreId)).orElseThrow(() -> new NullPointerException(
                            "此问卷不存在"));
            // 获取该问卷下的所有问题
            val questions =
                    Optional.ofNullable(questionMapper.selectByQnnrId(qnnreId)).orElseThrow(() -> new NullPointerException("此问题不存在"));
            questions.forEach(
                    question -> questionDTOList.add(QuestionDTO.builder()
                            .question(question)
                            .optionList(optionMapper.selectByQnnreIdAndQuestionId(qnnreId, question.getId()).stream()
                                    .map(it -> OptionDTO.builder().option(it).build()).toList())
                            .build())
            );
            val data = QnnreDTO.builder().qnnre(qnnre).questionDTOList(questionDTOList).build();
            return ServiceResult.ofOK("成功获取问卷", data);

        } catch (NullPointerException e) {
            return ServiceResult.of(ServiceResultCode.NO_SUCH_ENTITY, e.getMessage());
        }
    }

    /**
     * 添加一个选项。
     *
     * @param addOptionParam 添加选项参数，包括选项内容、所属问题ID和问卷ID等。
     * @throws IllegalArgumentException 参数不合法时抛出此异常。
     */
    private void addOptions(@NonNull AddOptionParam addOptionParam) throws IllegalArgumentException {
        List<Option> options = new ArrayList<>();
        Optional.ofNullable(addOptionParam.getContent()).ifPresentOrElse(
                contents -> Arrays.stream(contents).forEach(
                        content -> options.add(
                                Option.builder()
                                        .id(ArrayUtil.indexOf(contents, content))
                                        .content(content)
                                        .questionId(Optional.ofNullable(addOptionParam.getQuestionId()).orElseThrow(() -> new IllegalArgumentException("选项必须依赖于指定的问题")))
                                        .qnnreId(Optional.ofNullable(addOptionParam.getQnnreId()).orElseThrow(() -> new IllegalArgumentException("选项必须依赖于指定的问卷")))
                                        .build()
                        )
                ),
                () -> {
                    throw new IllegalArgumentException("必须至少有1个选项");
                }
        );
        options.forEach(optionMapper::insert);
    }

    /**
     * 添加一个单选或多选问题。
     *
     * @param addQuestionParam 添加问题参数，包括题目ID、所属问卷ID、题目内容、是否必答和类型等。
     * @throws IllegalArgumentException 参数不合法时抛出此异常。
     */
    private void addMultipleChoiceQuestion(@NonNull AddQuestionParam addQuestionParam) throws IllegalArgumentException {
        val question = Question.builder()
                .id(Optional.ofNullable(addQuestionParam.getIndex()).orElseThrow(() -> new
                        IllegalArgumentException(
                        "问题的ID必须被指定")))
                .qnnreId(Optional.ofNullable(addQuestionParam.getQnnreId()).orElseThrow(() -> new
                        IllegalArgumentException("问题必须依赖于一个已有的问卷")))
                .content(Optional.ofNullable(addQuestionParam.getProblemName()).orElseThrow(() -> new
                        IllegalArgumentException("题目标题不能为空")))
                .required(addQuestionParam.getMustAnswer() ? Required.REQUIRED : Required.OPTIONAL)
                .type(Optional.ofNullable(addQuestionParam.getType()).map(QuestionType::valueOf).orElse
                        (QuestionType.SINGLE_CHOICE_QUESTION))
                .build();
        questionMapper.insert(question);
    }

    /**
     * 修改一个问卷的名称和描述。
     *
     * @param qnnreId          问卷ID。
     * @param qnnreTitle       问卷名称。
     * @param qnnreDescription 问卷描述。
     * @return 修改结果 ServiceResult<QnnreDTO>，返回值中带有提示信息。
     */
    private ServiceResult<QnnreDTO> modifyQnnre(String qnnreId, String qnnreTitle, String qnnreDescription) {
        try {
            Optional.ofNullable(qnnreId).ifPresent(
                    id -> {
                        Optional.ofNullable(qnnreMapper.selectById(id)).ifPresentOrElse(
                                qnnre -> {
                                    qnnre.setName(Optional.ofNullable(qnnreTitle).orElseThrow(() -> new IllegalArgumentException("问卷名不能为空")));
                                    qnnre.setDescription(Optional.ofNullable(qnnreDescription).orElse(""));
                                    qnnreMapper.updateById(qnnre);
                                },
                                () -> {
                                    throw new NullPointerException("该问卷不存在");
                                }
                        );
                    }
            );
            return ServiceResult.ofOK("成功修改问卷");
        } catch (IllegalArgumentException e) {
            return ServiceResult.of(ServiceResultCode.ILLEGAL_PARAM, e.getMessage());
        } catch (NullPointerException e1) {
            return ServiceResult.of(ServiceResultCode.NO_SUCH_ENTITY, e1.getMessage());
        }
    }

}
