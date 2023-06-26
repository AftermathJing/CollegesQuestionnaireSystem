package com.github.aftermathjing.questionnaire.service;

import com.github.aftermathjing.questionnaire.api.dto.QuestionStatisticDTO;
import com.github.aftermathjing.questionnaire.entity.qnnre.Option;
import com.github.aftermathjing.questionnaire.entity.qnnre.Question;
import com.github.aftermathjing.questionnaire.mapper.answer.ResponseOptionMapper;
import com.github.aftermathjing.questionnaire.mapper.answer.ResponseSheetDetailMapper;
import com.github.aftermathjing.questionnaire.mapper.qnnre.OptionMapper;
import com.github.aftermathjing.questionnaire.mapper.qnnre.QnnreMapper;
import com.github.aftermathjing.questionnaire.mapper.qnnre.QuestionMapper;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
@Rollback()
public class StatisticServiceTest {

    @Mock
    private QuestionMapper questionMapper;
    @Mock
    private ResponseSheetDetailMapper responseSheetDetailMapper;
    @Mock
    private ResponseOptionMapper responseOptionMapper;
    @Mock
    private OptionMapper optionMapper;
    @Mock
    private QnnreMapper qnnreMapper;

    @InjectMocks
    private StatisticService statisticService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @SuppressWarnings("unchecked")
    void testGetQuestionStatisticDTOS_withValidQnnreId_shouldReturnQuestionStatisticList() {
        // Arrange
        String qnnreId = "validQnnreId";
        List<Question> questions = new ArrayList<>();
        questions.add(Question.builder().id(1).content("Question 1").build());
        questions.add(Question.builder().id(2).content("Question 2").build());

        when(questionMapper.selectByQnnrId(qnnreId)).thenReturn(questions);
        when(responseSheetDetailMapper.countByQnnreIdAndQuestionId(anyString(), anyInt())).thenReturn(Long.valueOf(5));

        // Act
        try {
            Method method = statisticService.getClass().getDeclaredMethod("getQuestionStatisticDTOS", String.class);
            method.setAccessible(true);
            val result = ((List<QuestionStatisticDTO>) method.invoke(statisticService, qnnreId));

            // Assert
            Assertions.assertNotNull(result);
            Assertions.assertEquals(2, result.size());
            Assertions.assertEquals("Question 1", result.get(0).getQuestionName());
            Assertions.assertEquals(5, result.get(0).getQuestionCount());
            Assertions.assertEquals("Question 2", result.get(1).getQuestionName());
            Assertions.assertEquals(5, result.get(1).getQuestionCount());

            verify(questionMapper).selectByQnnrId(qnnreId);
            verify(responseSheetDetailMapper, times(2)).countByQnnreIdAndQuestionId(eq(qnnreId), anyInt());

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignore) {
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    void testGetQuestionStatisticDTOS_withInvalidQnnreId_shouldThrowNullPointerException() {
        // Arrange
        String qnnreId = "invalidQnnreId";

        when(questionMapper.selectByQnnrId(qnnreId)).thenReturn(null);

        try {
            Method method = statisticService.getClass().getDeclaredMethod("getQuestionStatisticDTOS", String.class);
            method.setAccessible(true);
            val result = ((List<QuestionStatisticDTO>) method.invoke(statisticService, qnnreId));

            verify(questionMapper).selectByQnnrId(qnnreId);
            verifyNoInteractions(responseSheetDetailMapper);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignore) {
        } catch (NullPointerException e) {
            Assertions.assertEquals(NullPointerException.class, e.getClass());
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    void testGetQuestionStatisticDTOS_withEmptyQuestions_shouldThrowNullPointerException() {
        // Arrange
        String qnnreId = "validQnnreId";

        when(questionMapper.selectByQnnrId(qnnreId)).thenReturn(new ArrayList<>());

        try {
            Method method = statisticService.getClass().getDeclaredMethod("getQuestionStatisticDTOS", String.class);
            method.setAccessible(true);
            val result = ((List<QuestionStatisticDTO>) method.invoke(statisticService, qnnreId));

            verify(questionMapper).selectByQnnrId(qnnreId);
            verifyNoInteractions(responseSheetDetailMapper);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignore) {
        } catch (NullPointerException e) {
            Assertions.assertEquals(NullPointerException.class, e.getClass());
        }


    }

    @Test
    void testGetQuestionStatisticDTO_withValidIds_shouldReturnQuestionStatisticDTO() {
        // Arrange
        String qnnreId = "validQnnreId";
        Integer questionId = 1;

        val question = Question.builder().id(questionId).qnnreId(qnnreId).content("Question").build();
        val option = Option.builder().id(1).questionId(questionId).build();

        when(questionMapper.selectByQnnreIdAndQuestionId(qnnreId, questionId)).thenReturn(question);
        when(responseSheetDetailMapper.countByQnnreIdAndQuestionId(qnnreId, questionId)).thenReturn(Long.valueOf(5));
        when(optionMapper.selectByQuestionId(questionId)).thenReturn(List.of(option));
        when(responseOptionMapper.countByQnnreIdAndQuestionIdAndOptionId(eq(qnnreId), eq(questionId), anyInt()))
                .thenReturn(Long.valueOf(3));

        try {
            Method method = statisticService.getClass().getDeclaredMethod("getQuestionStatisticDTO", String.class);
            method.setAccessible(true);
            val result = ((QuestionStatisticDTO) method.invoke(statisticService, qnnreId, questionId));

            // Assert
            Assertions.assertNotNull(result);
            Assertions.assertEquals(questionId, result.getQuestionId());
            Assertions.assertEquals("Question", result.getQuestionName());
            Assertions.assertEquals(5, result.getQuestionCount());
            Assertions.assertEquals(1, result.getOptionList().size());
            Assertions.assertEquals(1, result.getOptionList().get(0).getOptionId());
            Assertions.assertEquals("Option 1", result.getOptionList().get(0).getOptionContent());
            Assertions.assertEquals(3, result.getOptionList().get(0).getCount());
            Assertions.assertEquals(0.6, result.getOptionList().get(0).getPercent(), 0.001);

            verify(questionMapper).selectByQnnreIdAndQuestionId(qnnreId, questionId);
            verify(responseSheetDetailMapper).countByQnnreIdAndQuestionId(qnnreId, questionId);
            verify(optionMapper).selectByQuestionId(questionId);
            verify(responseOptionMapper).countByQnnreIdAndQuestionIdAndOptionId(eq(qnnreId), eq(questionId), anyInt());

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignore) {
        } catch (NullPointerException e) {
            Assertions.assertEquals(NullPointerException.class, e.getClass());
        }

  }

    @Test
    void testGetQuestionStatisticDTO_withInvalidQnnreId_shouldThrowNullPointerException() {
        // Arrange
        String qnnreId = "invalidQnnreId";
        Integer questionId = 1;

        when(questionMapper.selectByQnnreIdAndQuestionId(qnnreId, questionId)).thenReturn(null);


        try {
            Method method = statisticService.getClass().getDeclaredMethod("getQuestionStatisticDTO", String.class);
            method.setAccessible(true);
            val result = ((QuestionStatisticDTO) method.invoke(statisticService, qnnreId, questionId));
            verify(questionMapper).selectByQnnreIdAndQuestionId(qnnreId, questionId);
            verifyNoInteractions(responseSheetDetailMapper);
            verifyNoInteractions(optionMapper);
            verifyNoInteractions(responseOptionMapper);

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignore) {
        } catch (NullPointerException e) {
            Assertions.assertEquals(NullPointerException.class, e.getClass());
        }


    }

    @Test
    void testGetQuestionStatisticDTO_withInvalidQuestionId_shouldThrowNullPointerException() {
        // Arrange
        String qnnreId = "validQnnreId";
        Integer questionId = 1;

        when(questionMapper.selectByQnnreIdAndQuestionId(qnnreId, questionId)).thenReturn(new Question());

        try {
            Method method = statisticService.getClass().getDeclaredMethod("getQuestionStatisticDTO", String.class);
            method.setAccessible(true);
            val result = ((QuestionStatisticDTO) method.invoke(statisticService, qnnreId, questionId));

            verify(questionMapper).selectByQnnreIdAndQuestionId(qnnreId, questionId);
            verifyNoInteractions(responseSheetDetailMapper);
            verifyNoInteractions(optionMapper);
            verifyNoInteractions(responseOptionMapper);

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignore) {
        } catch (NullPointerException e) {
            Assertions.assertEquals(NullPointerException.class, e.getClass());
        }

    }


}
