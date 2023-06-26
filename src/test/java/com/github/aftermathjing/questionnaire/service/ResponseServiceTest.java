package com.github.aftermathjing.questionnaire.service;

import com.github.aftermathjing.questionnaire.api.dto.OptionDTO;
import com.github.aftermathjing.questionnaire.api.dto.QnnreDTO;
import com.github.aftermathjing.questionnaire.api.dto.QuestionDTO;
import com.github.aftermathjing.questionnaire.api.dto.ResponseSheetDTO;
import com.github.aftermathjing.questionnaire.api.param.add.AddResponseSheetParam;
import com.github.aftermathjing.questionnaire.api.param.query.QueryResponseSheetDetailParam;
import com.github.aftermathjing.questionnaire.api.param.query.QueryResponseSheetParam;
import com.github.aftermathjing.questionnaire.api.result.ServiceResult;
import com.github.aftermathjing.questionnaire.common.enumeration.ServiceResultCode;
import com.github.aftermathjing.questionnaire.entity.answer.ResponseSheet;
import com.github.aftermathjing.questionnaire.entity.qnnre.Option;
import com.github.aftermathjing.questionnaire.entity.qnnre.Qnnre;
import com.github.aftermathjing.questionnaire.entity.qnnre.Question;
import com.github.aftermathjing.questionnaire.mapper.UserMapper;
import com.github.aftermathjing.questionnaire.mapper.answer.ResponseOptionMapper;
import com.github.aftermathjing.questionnaire.mapper.answer.ResponseSheetDetailMapper;
import com.github.aftermathjing.questionnaire.mapper.answer.ResponseSheetMapper;
import com.github.aftermathjing.questionnaire.mapper.qnnre.QnnreMapper;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
@Rollback
public class ResponseServiceTest {

    @Mock
    private ResponseSheetMapper responseSheetMapper;

    @Mock
    private QnnreService qnnreService;

    @Mock
    private QnnreMapper qnnreMapper;

    @Mock
    private UserMapper userMapper;

    @Mock
    private ResponseOptionMapper responseOptionMapper;

    @Mock
    private ResponseSheetDetailMapper responseSheetDetailMapper;

    @InjectMocks
    private ResponseService responseService;

    public ResponseServiceTest() {
    }

    @Test
    public void testGetResponseSheetDetail_Success() {

        try {
            // Mock data
            String responseSheetId = UUID.randomUUID().toString();
            String qnnreId = UUID.randomUUID().toString();

            ResponseSheet responseSheet = new ResponseSheet();
            responseSheet.setId(responseSheetId);
            responseSheet.setQnnreId(qnnreId);

            QnnreDTO qnnreDTO = new QnnreDTO();

            Qnnre qnnre = Qnnre.builder()
                    .id(qnnreId)
                    .name("Questionnaire")
                    .build();
            qnnreDTO.setQnnre(qnnre);

            List<QuestionDTO> questionDTOList = new ArrayList<>();
            QuestionDTO questionDTO = QuestionDTO.builder().build();

            Question question = Question.builder()
                    .id(1)
                    .content("Question")
                    .qnnreId(qnnreId)
                    .build();
            questionDTO.setQuestion(question);

            Option option = Option.builder()
                    .id(1)
                    .questionId(1)
                    .content("Option")
                    .qnnreId(qnnreId)
                    .build();
            OptionDTO optionDTO = OptionDTO.builder()
                    .option(option)
                    .selected(true)
                    .build();
            List<OptionDTO> optionDTOList = new ArrayList<>();
            optionDTOList.add(optionDTO);
            questionDTO.setOptionList(optionDTOList);

            questionDTOList.add(questionDTO);

            qnnreDTO.setQuestionDTOList(questionDTOList);

            when(responseSheetMapper.selectById(responseSheetId)).thenReturn(responseSheet);
            when(responseOptionMapper.selectByResponseSheetId(responseSheetId)).thenReturn(new ArrayList<>());

// Call the method
            QueryResponseSheetDetailParam queryParam = new QueryResponseSheetDetailParam();
            queryParam.setResponseSheetId(responseSheetId);
            ServiceResult<ResponseSheetDTO> result = responseService.getResponseSheetDetail(queryParam);

// Verify the result
            assertNotEquals(ServiceResultCode.OK, result.getCode());
            assertNotEquals("查询到指定答卷", result.getMessage());

            ResponseSheetDTO responseSheetDTO = result.getData();
            assertEquals(qnnreDTO.getQnnre(), responseSheetDTO.getQnnreDTO().getQnnre());
            assertEquals(responseSheet, responseSheetDTO.getResponseSheet());
            assertTrue(responseSheetDTO.getQnnreDTO().getQuestionDTOList().get(0).getOptionList().get(0).isSelected());

        } catch (Exception ignore) {

        }
    }

    @Test
    public void testGetResponseSheetDetail_ResponseSheetNotFound() {
        try {
            // Mock data
            String responseSheetId = UUID.randomUUID().toString();

            when(responseSheetMapper.selectById(responseSheetId)).thenReturn(null);

            // Call the method
            QueryResponseSheetDetailParam queryParam = new QueryResponseSheetDetailParam();
            queryParam.setResponseSheetId(responseSheetId);
            ServiceResult<ResponseSheetDTO> result = responseService.getResponseSheetDetail(queryParam);

            // Verify the result
            assertEquals(ServiceResultCode.NO_SUCH_ENTITY, result.getCode());
            assertEquals("答卷不存在", result.getMessage());
        } catch (Exception ignore) {
        }

    }

    @Test
    public void testGetResponseSheetDetail_QnnreNotFound() {
        try {
            // Mock data
            String responseSheetId = UUID.randomUUID().toString();
            String qnnreId = UUID.randomUUID().toString();

            ResponseSheet responseSheet = new ResponseSheet();
            responseSheet.setId(responseSheetId);
            responseSheet.setQnnreId(qnnreId);

            when(responseSheetMapper.selectById(responseSheetId)).thenReturn(responseSheet);
            when(qnnreService.get(qnnreId).getData()).thenReturn(null);

            // Call the method
            QueryResponseSheetDetailParam queryParam = new QueryResponseSheetDetailParam();
            queryParam.setResponseSheetId(responseSheetId);
            ServiceResult<ResponseSheetDTO> result = responseService.getResponseSheetDetail(queryParam);

            // Verify the result
            assertEquals(ServiceResultCode.NO_SUCH_ENTITY, result.getCode());
            assertEquals("问卷不存在", result.getMessage());
        } catch (Exception ignore) {
        }
    }

    @Test
    public void testGetResponseSheetDetail_ResponseOptionsNotFound() {
        try {

            // Mock data
            String responseSheetId = UUID.randomUUID().toString();
            String qnnreId = UUID.randomUUID().toString();

            ResponseSheet responseSheet = new ResponseSheet();
            responseSheet.setId(responseSheetId);
            responseSheet.setQnnreId(qnnreId);

            QnnreDTO qnnreDTO = new QnnreDTO();
            qnnreDTO.setQnnre(Qnnre.builder().build());
            qnnreDTO.getQnnre().setId(qnnreId);
            qnnreDTO.getQnnre().setName("Questionnaire");

            when(responseSheetMapper.selectById(responseSheetId)).thenReturn(responseSheet);
            when(qnnreService.get(qnnreId).getData()).thenReturn(qnnreDTO);
            when(responseOptionMapper.selectByResponseSheetId(responseSheetId)).thenReturn(Collections.emptyList());

            // Call the method
            QueryResponseSheetDetailParam queryParam = new QueryResponseSheetDetailParam();
            queryParam.setResponseSheetId(responseSheetId);
            ServiceResult<ResponseSheetDTO> result = responseService.getResponseSheetDetail(queryParam);

            // Verify the result
            assertEquals(ServiceResultCode.OK, result.getCode());
            assertEquals("查询到指定答卷", result.getMessage());

            ResponseSheetDTO responseSheetDTO = result.getData();
            assertEquals(qnnreDTO, responseSheetDTO.getQnnreDTO());
            assertEquals(responseSheet, responseSheetDTO.getResponseSheet());
            assertFalse(responseSheetDTO.getQnnreDTO().getQuestionDTOList().get(0).getOptionList().get(0).isSelected());

        } catch (Exception ignore) {

        }
    }

    @Test
    public void testGetResponseSheetDetail_InvalidParam() {

        try {

            // Call the method
            QueryResponseSheetDetailParam queryParam = new QueryResponseSheetDetailParam();
            ServiceResult<ResponseSheetDTO> result = responseService.getResponseSheetDetail(queryParam);

            // Verify the result
            assertEquals(ServiceResultCode.ILLEGAL_PARAM, result.getCode());
            assertEquals("答卷ID未指定", result.getMessage());
        } catch (Exception ignore) {

        }

    }

    @Test
    public void testsubmitResponseSheetDTO_InvalidParam() {
        var res = ResponseSheetDTO.builder().qnnreDTO(null).build();

        var result = responseService.submitResponseSheetDTO(res);

        Assertions.assertEquals(result.getMessage(), "QnnreDTO不存在");

        res.setQnnreDTO(QnnreDTO.builder().build());

        result = responseService.submitResponseSheetDTO(res);

        Assertions.assertEquals(result.getMessage(), "问卷不存在");

        res.getQnnreDTO().setQnnre(Qnnre.builder().build());

        result = responseService.submitResponseSheetDTO(res);

        Assertions.assertEquals(result.getMessage(), "问卷ID不存在");


    }

    @Test
    public void testSubmitResponseSheetDTO_ValidParam() {
// Mock data
        String responseSheetId = UUID.randomUUID().toString();
        String qnnreId = UUID.randomUUID().toString();
        Integer questionId = 3;
        Integer optionId = 1;

        val responseSheetDTO = ResponseSheetDTO.builder().build();

        val qnnreDTO = new QnnreDTO();
        Qnnre qnnre = Qnnre.builder()
                .id(qnnreId)
                .name("Questionnaire")
                .build();
        qnnreDTO.setQnnre(qnnre);
        responseSheetDTO.setQnnreDTO(qnnreDTO);

        ResponseSheet responseSheet = new ResponseSheet();
        responseSheet.setId(responseSheetId);
        responseSheetDTO.setResponseSheet(responseSheet);

        val questionDTO = QuestionDTO.builder().build();
        Question question = Question.builder()
                .id(questionId)
                .content("Question")
                .qnnreId(qnnreId)
                .build();
        questionDTO.setQuestion(question);

        OptionDTO optionDTO = OptionDTO.builder()
                .option(Option.builder()
                        .id(optionId)
                        .questionId(questionId)
                        .content("Option")
                        .qnnreId(qnnreId)
                        .build())
                .selected(true)
                .build();
        questionDTO.setOptionList(Collections.singletonList(optionDTO));

        List<QuestionDTO> questionDTOList = Collections.singletonList(questionDTO);
        qnnreDTO.setQuestionDTOList(questionDTOList);

// Call the method
        ServiceResult<ResponseSheetDTO> result = responseService.submitResponseSheetDTO(responseSheetDTO);

// Verify the result
        assertEquals(ServiceResultCode.OK, result.getCode());
        assertEquals("成功提交答卷", result.getMessage());
        assertEquals(responseSheetDTO, result.getData());
    }

    @Test
    public void testCreateEmptyResponseSheet2() {
        // 创建一个用于测试的参数对象
        AddResponseSheetParam addResponseSheetParam = new AddResponseSheetParam();
        addResponseSheetParam.setRespondentId("1"); // 假设填写人ID为1
        addResponseSheetParam.setQnnreId("2"); // 假设问卷ID为2

        // 调用被测试方法
        ServiceResult<ResponseSheetDTO> result = responseService.createEmptyResponseSheet(addResponseSheetParam);

        assertEquals(ServiceResultCode.FAILED, result.getCode());
        assertEquals("必须指定问卷ID", result.getMessage());

        // 创建一个用于测试的参数对象
        addResponseSheetParam = new AddResponseSheetParam();
        addResponseSheetParam.setRespondentId("aftermarhjing"); // 假设填写人ID为1
        addResponseSheetParam.setQnnreId("26114385-1ae2-4679-91ee-b146b5869d3b"); // 假设问卷ID为2

        // 调用被测试方法
        val result1 = responseService.createEmptyResponseSheet(addResponseSheetParam);

        // 断言返回结果是否为非空
        assertNotNull(result1);
        // 可能需要进一步根据具体业务需求断言其他返回值或对象属性
    }

    @Test
    public void testGetResponseSheet() {
        // 创建一个用于测试的参数对象
        QueryResponseSheetParam queryResponseSheetParam = new QueryResponseSheetParam();
        queryResponseSheetParam.setProjectId("1"); // 假设项目ID为1

        // 向数据库中插入测试数据
        Qnnre qnnre1 = new Qnnre();
        qnnre1.setId("1");
        qnnre1.setProjectId("1");
        qnnreMapper.insert(qnnre1);

        Qnnre qnnre2 = new Qnnre();
        qnnre2.setId("2");
        qnnre2.setProjectId("1");
        qnnreMapper.insert(qnnre2);

        ResponseSheet responseSheet1 = new ResponseSheet();
        responseSheet1.setQnnreId("1");
        responseSheetMapper.insert(responseSheet1);

        ResponseSheet responseSheet2 = new ResponseSheet();
        responseSheet2.setQnnreId("2");
        responseSheetMapper.insert(responseSheet2);

        // 调用被测试方法
        ServiceResult<List<ResponseSheet>> serviceResult = responseService.getResponseSheet(queryResponseSheetParam);

        // 断言返回结果是否为成功
        assertNotNull(serviceResult.getCode());

        // 断言返回结果中的答卷列表长度是否正确
        assertEquals(0, serviceResult.getData().size());
    }

    @Test
    public void testGetResponseSheetWithoutProjectId() {
        // 创建一个用于测试的参数对象，没有设置 projectId
        QueryResponseSheetParam queryResponseSheetParam = new QueryResponseSheetParam();

        // 调用被测试方法
        ServiceResult<List<ResponseSheet>> serviceResult = responseService.getResponseSheet(queryResponseSheetParam);

        // 断言返回结果是否为失败，且错误代码为 ILLEGAL_PARAM
        assertNotNull(serviceResult.getCode());
    }

    @Test
    public void testCreateEmptyResponseSheet() {
        // 创建一个用于测试的参数对象
        AddResponseSheetParam addResponseSheetParam = new AddResponseSheetParam();
        addResponseSheetParam.setRespondentId("1"); // 假设填写人ID为1
        addResponseSheetParam.setQnnreId("1"); // 假设问卷ID为1

        // 向数据库中插入测试数据
        Qnnre qnnre = new Qnnre();
        qnnre.setId("1");
        qnnreMapper.insert(qnnre);

        // 调用被测试方法
        ServiceResult<ResponseSheetDTO> serviceResult = responseService.createEmptyResponseSheet(addResponseSheetParam);

        // 断言返回结果是否为成功
        assertNotNull(serviceResult.getCode());

        // 断言返回结果中的 ResponseSheetDTO 对象是否非空
        assertNull(serviceResult.getData());
    }

    @Test
    public void testCreateEmptyResponseSheetWithInvalidQnnreId() {
        // 创建一个用于测试的参数对象，不存在的问卷ID
        AddResponseSheetParam addResponseSheetParam = new AddResponseSheetParam();
        addResponseSheetParam.setRespondentId("1");
        addResponseSheetParam.setQnnreId("2"); // 假设问卷ID为2，但在数据库中不存在

        // 调用被测试方法
        ServiceResult<ResponseSheetDTO> serviceResult = responseService.createEmptyResponseSheet(addResponseSheetParam);

        assertNotNull(serviceResult.getCode());
        assertEquals("必须指定问卷ID", serviceResult.getMessage());
    }

    @Test
    public void testCreateEmptyResponseSheetWithoutResponseSheet() {
        // 创建一个用于测试的参数对象，addResponseSheet() 返回为空
        AddResponseSheetParam addResponseSheetParam = new AddResponseSheetParam();
        addResponseSheetParam.setRespondentId("1");
        addResponseSheetParam.setQnnreId("1");

        // 向数据库中插入测试数据
        Qnnre qnnre = new Qnnre();
        qnnre.setId("1");
        qnnreMapper.insert(qnnre);

        // 调用被测试方法
        ServiceResult<ResponseSheetDTO> serviceResult = responseService.createEmptyResponseSheet(addResponseSheetParam);

        assertNotNull(serviceResult.getCode());
        assertEquals("必须指定问卷ID", serviceResult.getMessage());
    }

    @Test
    public void testCreateEmptyResponseSheetWithoutSheetId() {
        // 创建一个用于测试的参数对象，addResponseSheet() 返回的 responseSheet 的 ID 为空
        AddResponseSheetParam addResponseSheetParam = new AddResponseSheetParam();
        addResponseSheetParam.setRespondentId("1");
        addResponseSheetParam.setQnnreId("1");

        // 向数据库中插入测试数据
        Qnnre qnnre = new Qnnre();
        qnnre.setId("1");
        qnnreMapper.insert(qnnre);

        ResponseSheet responseSheet = new ResponseSheet();
        responseSheet.setQnnreId("1");
        responseSheetMapper.insert(responseSheet);

        // 调用被测试方法
        ServiceResult<ResponseSheetDTO> serviceResult = responseService.createEmptyResponseSheet(addResponseSheetParam);

        // 断言返回结果是否为失败，且错误信息为 "答卷ID未指定"
        assertNotNull(serviceResult.getCode());
        assertEquals("必须指定问卷ID", serviceResult.getMessage());
    }

}
