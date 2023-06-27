package com.github.aftermathjing.questionnaire.service;

import cn.hutool.core.lang.Pair;
import com.github.aftermathjing.questionnaire.api.dto.InnerOption;
import com.github.aftermathjing.questionnaire.api.dto.QuestionStatisticDTO;
import com.github.aftermathjing.questionnaire.api.param.query.QueryProjectParam;
import com.github.aftermathjing.questionnaire.api.param.query.QueryStatisticParam;
import com.github.aftermathjing.questionnaire.api.result.ServiceResult;
import com.github.aftermathjing.questionnaire.common.enumeration.ServiceResultCode;
import com.github.aftermathjing.questionnaire.entity.qnnre.Qnnre;
import com.github.aftermathjing.questionnaire.entity.qnnre.Question;
import com.github.aftermathjing.questionnaire.mapper.ProjectMapper;
import com.github.aftermathjing.questionnaire.mapper.answer.ResponseOptionMapper;
import com.github.aftermathjing.questionnaire.mapper.answer.ResponseSheetDetailMapper;
import com.github.aftermathjing.questionnaire.mapper.qnnre.OptionMapper;
import com.github.aftermathjing.questionnaire.mapper.qnnre.QnnreMapper;
import com.github.aftermathjing.questionnaire.mapper.qnnre.QuestionMapper;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class StatisticService {

    QuestionMapper questionMapper;
    ResponseSheetDetailMapper responseSheetDetailMapper;
    ResponseOptionMapper responseOptionMapper;
    OptionMapper optionMapper;
    ProjectMapper projectMapper;
    QnnreMapper qnnreMapper;
    QnnreService qnnreService;

    @Autowired
    StatisticService(QuestionMapper questionMapper, ResponseSheetDetailMapper responseSheetDetailMapper,
                     ResponseOptionMapper responseOptionMapper, OptionMapper optionMapper, QnnreMapper qnnreMapper,
                     ProjectMapper projectMapper, QnnreService qnnreService) {
        this.questionMapper = questionMapper;
        this.responseSheetDetailMapper = responseSheetDetailMapper;
        this.responseOptionMapper = responseOptionMapper;
        this.optionMapper = optionMapper;
        this.qnnreMapper = qnnreMapper;
        this.projectMapper = projectMapper;
        this.qnnreService = qnnreService;
    }

    /**
     * 根据查询参数获取问题的统计信息。
     *
     * @param queryStatisticParam 查询参数，包含问卷ID
     * @return 问题统计结果的服务结果(ServiceResult)，包含问题统计列表(List<QuestionStatisticDTO>)
     */
    public ServiceResult<List<QuestionStatisticDTO>> getQuestionStatistic(@NonNull QueryStatisticParam queryStatisticParam) {
        try {
            val qnnreId =
                    Optional.ofNullable(queryStatisticParam.getQnnreId()).orElseThrow(() -> new IllegalArgumentException("问卷ID" + "必须被指定"));
            val questionStatisticDTOS = getQuestionStatisticDTOS(qnnreId);
            return ServiceResult.ofOK("成功统计问卷结果", questionStatisticDTOS);
        } catch (IllegalArgumentException e1) {
            return ServiceResult.of(ServiceResultCode.ILLEGAL_PARAM, e1.getMessage());
        } catch (NullPointerException e2) {
            return ServiceResult.of(ServiceResultCode.FAILED, e2.getMessage());
        }
    }

    public ServiceResult<List<QuestionStatisticDTO>> getSameQuestionStatistic(@NonNull QueryProjectParam queryProjectParam) {
        try {
            val qnnreId =
                    Optional.ofNullable(queryProjectParam.getQnnreId()).orElseThrow(() -> new IllegalArgumentException(
                            "问卷ID必须被指定"));
            val questionId =
                    Optional.ofNullable(queryProjectParam.getQuestionId()).orElseThrow(() -> new IllegalArgumentException("问题ID必须被指定"));

            val question = questionMapper.selectByQnnreIdAndQuestionId(qnnreId, questionId);

            val sameQuestionsStatistic =
                    Optional.ofNullable(getSameQuestionsStatistic(queryProjectParam).getData()).orElseThrow(() -> new NullPointerException("相同问题列表为空"));
            val data =
                    sameQuestionsStatistic.stream()
                            .filter(questionStatisticDTO -> questionStatisticDTO.getQuestionName().equals(question.getContent())).toList();
            return ServiceResult.ofOK("成功找到相同题目并统计", data);

        } catch (NullPointerException | IllegalArgumentException e) {
            return ServiceResult.of(ServiceResultCode.FAILED, e.getMessage());
        }
    }

    /**
     * 统计指定项目下所有问卷中相同问题的方法。
     *
     * @param queryProjectParam 查询项目参数对象，包含项目ID。
     * @return 返回一个ServiceResult对象，其中包含了统计结果。如果成功，则ServiceResult的结果状态为OK，并且携带了统计信息；否则，返回错误信息。
     * @note 注意，请不要随意更改这段代码，除非你知道你在做什么
     */
    private ServiceResult<List<QuestionStatisticDTO>> getSameQuestionsStatistic(@NonNull QueryProjectParam queryProjectParam) {
        try {
            var questionIndex = new AtomicInteger(0);

            return ServiceResult.ofOK("成功统计该项目下所有问卷中的相同问题",
                    questionMapper.selectByQnnreIds(
                                    qnnreMapper.selectByProjectId(
                                                    Optional.ofNullable(projectMapper.selectById(
                                                                    Optional.ofNullable(queryProjectParam.getProjectId())
                                                                            .orElseThrow(() -> new NullPointerException("项目ID不能为空")))
                                                            ).orElseThrow(() -> new NullPointerException("项目不存在"))
                                                            .getId())
                                            .stream()
                                            .map(Qnnre::getId)
                                            .toList()).stream()
                            .collect(Collectors.groupingBy(Question::getContent)) // 根据属性进行分组
                            .values().stream()
                            .filter(group -> group.size() > 1) // 过滤出有重复属性值的组
                            .map(questionsWithSameContent -> {
                                val sameQuestionAnswerCount =
                                        questionsWithSameContent.stream()
                                                .map(question ->
                                                        responseOptionMapper.selectByQnnreIdAndQuestionId(
                                                                question.getQnnreId(),
                                                                question.getId()))
                                                .flatMap(List::stream)
                                                .map(responseOption ->
                                                        optionMapper.selectByQnnreIdAndQuestionIdAndOptionId(
                                                                        responseOption.getQnnreId(),
                                                                        responseOption.getQuestionId(),
                                                                        responseOption.getOptionId())
                                                                .getContent())
                                                .toList();

                                var optionIndex = new AtomicInteger(0);

                                return QuestionStatisticDTO.builder().questionId(questionIndex.incrementAndGet())
                                        // 这些问题都是内容相同的, 所以取其第一个即可
                                        .questionName(questionsWithSameContent.get(0).getContent())
                                        .questionCount(sameQuestionAnswerCount.size())
                                        .optionList(
                                                sameQuestionAnswerCount.stream()
                                                        .collect(Collectors.groupingBy(s -> s))
                                                        .values().stream()
                                                        .map(optionStrs -> Pair.of(optionStrs.get(0),
                                                                optionStrs.size()))
                                                        .map(stringIntegerPair ->
                                                                InnerOption.builder()
                                                                        .optionContent(stringIntegerPair.getKey())
                                                                        .count(stringIntegerPair.getValue())
                                                                        .percent(((float) stringIntegerPair.getValue()) / ((float) sameQuestionAnswerCount.size()))
                                                                        .optionId(optionIndex.getAndIncrement())
                                                                        .build())
                                                        .toList())
                                        .build();
                            }).toList());
        } catch (NullPointerException e) {
            return ServiceResult.of(ServiceResultCode.FAILED, e.getMessage());
        }
    }

    /**
     * 获取指定问卷下的问题统计信息。
     *
     * @param qnnreId 问卷ID
     * @return 问题统计DTO列表(List < QuestionStatisticDTO >)
     * @throws NullPointerException 如果问卷下没有问题，则抛出空指针异常
     */
    private List<QuestionStatisticDTO> getQuestionStatisticDTOS(@NonNull String qnnreId) throws NullPointerException {
        val questions =
                Optional.ofNullable(questionMapper.selectByQnnrId(qnnreId)).orElseThrow(() -> new NullPointerException("该问卷下没有问题"));
        return questions.stream().map(question -> getQuestionStatisticDTO(question.getQnnreId(), question.getId())).toList();
    }

    /**
     * 获取指定问题的统计信息
     *
     * @param qnnreId    问卷ID
     * @param questionId 问题ID
     * @return QuestionStatisticDTO对象，包含问题的统计信息
     * @throws NullPointerException 如果问题不存在，则抛出空指针异常
     */
    private QuestionStatisticDTO getQuestionStatisticDTO(@NonNull String qnnreId, @NonNull Integer questionId) throws NullPointerException {
        val question = questionMapper.selectByQnnreIdAndQuestionId(qnnreId, questionId);
        val questionName =
                Optional.ofNullable(question).orElseThrow(() -> new NullPointerException("问题不存在")).getContent();
        val questionCount = (int) responseSheetDetailMapper.countByQnnreIdAndQuestionId(qnnreId, questionId);

        val options =
                Optional.ofNullable(optionMapper.selectByQnnreIdAndQuestionId(qnnreId, questionId)).orElseThrow(() -> new NullPointerException("该问题下没有选项"));
        val innerOptionList = options.stream().map(option -> {
            int innerOptionCount = (int) responseOptionMapper.countByQnnreIdAndQuestionIdAndOptionId(qnnreId,
                    questionId, option.getId());
            return InnerOption.builder().optionId(option.getId()).optionContent(option.getContent()).count(innerOptionCount).build();
        }).toList();

        // 计算percent
        val sum = innerOptionList.stream().mapToInt(InnerOption::getCount).sum();
        innerOptionList.forEach(innerOption -> innerOption.setPercent(((float) innerOption.getCount()) / ((float) sum)));

        return QuestionStatisticDTO.builder().questionId(questionId).questionName(questionName).questionCount(questionCount).optionList(innerOptionList).build();
    }

}
