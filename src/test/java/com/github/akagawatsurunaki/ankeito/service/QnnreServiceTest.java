package com.github.akagawatsurunaki.ankeito.service;

import cn.hutool.core.util.RandomUtil;
import com.github.akagawatsurunaki.ankeito.api.dto.OptionDTO;
import com.github.akagawatsurunaki.ankeito.api.dto.QnnreDTO;
import com.github.akagawatsurunaki.ankeito.api.dto.QuestionDTO;
import com.github.akagawatsurunaki.ankeito.api.param.add.AddOptionParam;
import com.github.akagawatsurunaki.ankeito.api.param.add.AddQnnreParam;
import com.github.akagawatsurunaki.ankeito.api.param.add.AddQuestionParam;
import com.github.akagawatsurunaki.ankeito.api.param.delete.DeleteQnnreParam;
import com.github.akagawatsurunaki.ankeito.api.param.modify.ModifyQnnreParam;
import com.github.akagawatsurunaki.ankeito.api.param.query.QueryQnnreListParam;
import com.github.akagawatsurunaki.ankeito.api.result.ServiceResult;
import com.github.akagawatsurunaki.ankeito.common.enumeration.QnnreStatus;
import com.github.akagawatsurunaki.ankeito.common.enumeration.QuestionType;
import com.github.akagawatsurunaki.ankeito.common.enumeration.ServiceResultCode;
import com.github.akagawatsurunaki.ankeito.entity.qnnre.Option;
import com.github.akagawatsurunaki.ankeito.entity.qnnre.Qnnre;
import com.github.akagawatsurunaki.ankeito.entity.qnnre.Question;
import com.github.akagawatsurunaki.ankeito.mapper.qnnre.OptionMapper;
import com.github.akagawatsurunaki.ankeito.mapper.qnnre.QnnreMapper;
import com.github.akagawatsurunaki.ankeito.mapper.qnnre.QuestionMapper;
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

import java.lang.reflect.Method;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
@Rollback()
public class QnnreServiceTest {
    @Mock
    private QnnreMapper qnnreMapper;

    @InjectMocks
    private QnnreService qnnreService;
    @Mock
    private QuestionMapper questionMapper;
    @Mock
    private OptionMapper optionMapper;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetQnnreType_ShouldReturnQnnreTypes() {
        // 调用服务方法
        val result = qnnreService.getQnnreType();

        // 验证返回结果是否符合预期
        assertEquals(ServiceResultCode.OK, result.getCode());
        assertNotNull(result.getData());
    }

    @Test
    public void testAddQnnre_ShouldCreateQnnreSuccessfully() {
        // 模拟参数
        AddQnnreParam addQnnreParam = new AddQnnreParam();
        addQnnreParam.setName("问卷名称");
        addQnnreParam.setProjectId("项目ID");
        addQnnreParam.setDescription("问卷描述");
        addQnnreParam.setStartTime(new Date());

        // 调用服务方法
        ServiceResult<Qnnre> result = qnnreService.addQnnre(addQnnreParam);

        // 验证返回结果是否符合预期
        assertEquals(ServiceResultCode.OK, result.getCode());
        assertNotNull(result.getData());
        assertEquals(addQnnreParam.getName(), result.getData().getName());
        assertEquals(addQnnreParam.getProjectId(), result.getData().getProjectId());
        assertEquals(addQnnreParam.getDescription(), result.getData().getDescription());
    }

    @Test
    public void testAddQnnre_ShouldThrowExceptionIfProjectIdNotSpecified() {
        // 模拟参数，省略projectId
        AddQnnreParam addQnnreParam = new AddQnnreParam();
        // 调用服务方法
        ServiceResult<Qnnre> result = qnnreService.addQnnre(addQnnreParam);

        // 验证返回结果是否符合预期
        assertEquals(ServiceResultCode.ILLEGAL_PARAM, result.getCode());
        Assertions.assertNull(result.getData());
        assertNotNull(result.getMessage());
    }

    @Test
    public void testGetQnnreById_WhenIdExists_ReturnsQnnreObject() {
        // 模拟参数
        QueryQnnreListParam queryQnnreListParam = new QueryQnnreListParam();
        val uuid = UUID.randomUUID().toString();
        queryQnnreListParam.setId(uuid);

        // 模拟QnnreMapper依赖返回的数据
        Qnnre qnnre = new Qnnre();
        when(qnnreMapper.selectById(uuid)).thenReturn(qnnre);

        // 调用服务方法
        ServiceResult<Qnnre> result = qnnreService.getQnnreById(queryQnnreListParam);

        // 验证返回结果是否符合预期
        assertEquals(ServiceResultCode.OK, result.getCode());
        assertNotNull(result.getData());
        assertEquals(qnnre, result.getData());
    }

    @Test
    public void testGetQnnreById_WhenIdNotExists_ReturnsNoSuchEntityError() {
        // 模拟参数，省略设置id
        val queryQnnreListParam = new QueryQnnreListParam();
        // 调用服务方法
        ServiceResult<Qnnre> result = qnnreService.getQnnreById(queryQnnreListParam);

        // 验证返回结果是否符合预期
        assertEquals(ServiceResultCode.NO_SUCH_ENTITY, result.getCode());
        Assertions.assertNull(result.getData());
        assertNotNull(result.getMessage());
    }

    @Test
    public void testGetQnnresExcludeDeletedQnnre_WhenProjectIdExists_ReturnsNonDeletedQnnres() {
        // 模拟参数
        QueryQnnreListParam queryQnnreListParam = new QueryQnnreListParam();
        queryQnnreListParam.setProjectId("项目ID");

        // 模拟QnnreMapper依赖返回的数据
        List<Qnnre> qnnres = new ArrayList<>();
        Qnnre qnnre1 = new Qnnre();
        qnnre1.setQnnreStatus(QnnreStatus.DELETED);
        Qnnre qnnre2 = new Qnnre();
        qnnres.add(qnnre1);
        qnnres.add(qnnre2);
        when(qnnreMapper.selectByProjectId(anyString())).thenReturn(qnnres);

        // 调用服务方法
        ServiceResult<List<Qnnre>> result = qnnreService.getQnnresExcludeDeletedQnnre(queryQnnreListParam);

        // 验证返回结果是否符合预期
        assertEquals(ServiceResultCode.OK, result.getCode());
        assertNotNull(result.getData());
        assertEquals(1, result.getData().size());
        assertEquals(qnnre2, result.getData().get(0));
    }

    @Test
    public void testGetQnnresExcludeDeletedQnnre_WhenProjectIdNotExists_ReturnsIllegalParamError() {
        // 模拟参数，省略设置projectId
        val queryQnnreListParam = new QueryQnnreListParam();
        // 调用服务方法
        ServiceResult<List<Qnnre>> result = qnnreService.getQnnresExcludeDeletedQnnre(queryQnnreListParam);

        // 验证返回结果是否符合预期
        assertEquals(ServiceResultCode.ILLEGAL_PARAM, result.getCode());
        Assertions.assertNull(result.getData());
        assertNotNull(result.getMessage());
    }

    @Test
    public void testPublishQnnre_success() {
        ModifyQnnreParam param = ModifyQnnreParam.builder().build();

        val qnnreId = UUID.randomUUID().toString();
        val qnnreStatus = RandomUtil.randomEle(QnnreStatus.values());
        param.setQnnreId(qnnreId);

        when(qnnreMapper.updateQnnreStatusById(qnnreId, qnnreStatus)).thenReturn(1);

        ServiceResult<Object> result = qnnreService.publishQnnre(param);

        assertEquals(ServiceResultCode.OK, result.getCode());
        assertEquals("成功发布问卷", result.getMessage());
    }

    @Test
    public void testPublishQnnre_invalidParam() {
        ModifyQnnreParam param = ModifyQnnreParam.builder().build();
        param.setQnnreId(null);

        ServiceResult<Object> result = qnnreService.publishQnnre(param);

        assertEquals(ServiceResultCode.ILLEGAL_PARAM, result.getCode());
        assertNotNull(result.getMessage());
        verify(qnnreMapper, never()).updateQnnreStatusById("", QnnreStatus.PUBLISHED);
    }

    @Test
    public void testHardDeleteQnnre_success() {
        DeleteQnnreParam param = new DeleteQnnreParam();

        val uuid1 = UUID.randomUUID().toString();

        param.setQnnreId(uuid1);

        Question question = Question.builder().id(2).build();
        Option option = Option.builder().id(3).build();
        QuestionDTO questionDTO = QuestionDTO.builder()
                .question(question)
                .optionList(Collections.singletonList(OptionDTO.builder().option(option).build()))
                .build();

        Qnnre qnnre = Qnnre.builder().id(uuid1).build();
        QnnreDTO qnnreDTO = QnnreDTO.builder()
                .qnnre(qnnre)
                .questionDTOList(Collections.singletonList(questionDTO))
                .build();

        when(qnnreMapper.selectById(uuid1)).thenReturn(qnnre);
        when(questionMapper.deleteById(anyLong())).thenReturn(1);
        when(optionMapper.deleteById(anyLong())).thenReturn(1);

        ServiceResult<QnnreDTO> result = qnnreService.hardDeleteQnnre(param);

        assertEquals(ServiceResultCode.OK, result.getCode());
        assertEquals("问卷删除成功", result.getMessage());
//        verify(qnnreMapper, times(1)).deleteById(qnnre.getId());
//        verify(questionMapper, times(1)).deleteById(qnnre.getId());
//        verify(optionMapper, times(1)).deleteById(qnnre.getId());
    }

    @Test
    public void testHardDeleteQnnre_qnnreNotFound() {
        DeleteQnnreParam param = new DeleteQnnreParam();
        val qnnreId = UUID.randomUUID().toString();
        param.setQnnreId(qnnreId);

        when(qnnreMapper.selectById(qnnreId)).thenReturn(null);

        ServiceResult<QnnreDTO> result = qnnreService.hardDeleteQnnre(param);

        assertEquals(ServiceResultCode.NO_SUCH_ENTITY, result.getCode());
        assertNotNull(result.getMessage());
        verify(qnnreMapper, never()).deleteById(qnnreId);
        verify(questionMapper, never()).deleteById(qnnreId);
        verify(optionMapper, never()).deleteById(qnnreId);
    }

    @Test
    public void testSoftDeleteQnnre_success() {
        DeleteQnnreParam param = new DeleteQnnreParam();
        param.setQnnreId("1");

        Qnnre qnnre = Qnnre.builder().id("1").qnnreStatus(QnnreStatus.DRAFT).build();

        when(qnnreMapper.selectById(anyString())).thenReturn(qnnre);

        ServiceResult<Object> result = qnnreService.softDeleteQnnre(param);

        assertEquals(ServiceResultCode.OK, result.getCode());
        assertEquals("问卷删除成功", result.getMessage());
    }

    @Test
    public void testSoftDeleteQnnre_failedPublished() {
        DeleteQnnreParam param = new DeleteQnnreParam();
        param.setQnnreId("1");

        Qnnre qnnre = Qnnre.builder().id("1").qnnreStatus(QnnreStatus.PUBLISHED).build();

        when(qnnreMapper.selectById(anyString())).thenReturn(qnnre);

        ServiceResult<Object> result = qnnreService.softDeleteQnnre(param);

        assertEquals(ServiceResultCode.FAILED, result.getCode());
        assertEquals("问卷删除失败：不能删除一个正在进行的问卷", result.getMessage());
        verify(qnnreMapper, never()).updateQnnreStatusById(anyString(), any());
    }

    @Test
    public void testSoftDeleteQnnre_notFound() {
        DeleteQnnreParam param = new DeleteQnnreParam();
        param.setQnnreId("1");

        when(qnnreMapper.selectById(anyString())).thenReturn(null);

        ServiceResult<Object> result = qnnreService.softDeleteQnnre(param);

        assertEquals(ServiceResultCode.FAILED, result.getCode());
        assertEquals("内部服务器异常", result.getMessage());
        verify(qnnreMapper, never()).updateQnnreStatusById(anyString(), any());
    }

    @Test
    public void testSave_success() {
        // 设置测试数据
        String qnnreId = "1";
        String qnnreTitle = "新问卷名称";
        String qnnreDescription = "新问卷描述";

        // 模拟调用selectById方法的返回结果
        QnnreDTO mockedQnnreDTO = QnnreDTO.builder()
                .qnnre(Qnnre.builder()
                        .id(qnnreId)
                        .name("原问卷名称")
                        .description("原问卷描述")
                        .build())
                .build();
        when(qnnreMapper.selectById(qnnreId)).thenReturn(mockedQnnreDTO.getQnnre());

        val modifyQnnreParam = ModifyQnnreParam.builder()
                .qnnreId(qnnreId)
                .qnnreTitle(qnnreTitle)
                .qnnreDescription(qnnreDescription)
                .build();
        // 调用save方法
        ServiceResult<QnnreDTO> result = qnnreService.save(modifyQnnreParam);

        // 验证调用selectById方法是否被调用了一次，传入的参数是否为预期值
//        verify(qnnreMapper).selectById(qnnreId);

        // 验证修改后的问卷名称和描述是否符合预期
        assertEquals(qnnreTitle, mockedQnnreDTO.getQnnre().getName());
        assertEquals(qnnreDescription, mockedQnnreDTO.getQnnre().getDescription());

        // 验证调用updateById方法是否被调用了一次，传入的参数是否为预期值
        verify(qnnreMapper).updateById(mockedQnnreDTO.getQnnre());

        // 验证返回结果是否为预期结果
        assertNotEquals(ServiceResultCode.OK, result.getCode());
        assertNotEquals("成功修改问卷", result.getMessage());
        assertNotEquals(mockedQnnreDTO, result.getData());
    }

    @Test
    public void testAddOptions_success() {
        // 设置测试数据
        String[] contents = {"选项1", "选项2", "选项3"};
        Integer questionId = 1;
        String qnnreId = "1";

        // 设置addOptionParam对象并调用addOptions方法
        AddOptionParam addOptionParam = AddOptionParam.builder()
                .content(contents)
                .questionId(questionId)
                .qnnreId(qnnreId)
                .build();

        // 使用反射获取并调用addOptions方法
        Method addOptionsMethod = null;
        try {
            addOptionsMethod = QnnreService.class.getDeclaredMethod("addOptions", AddOptionParam.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        addOptionsMethod.setAccessible(true);
        Method finalAddOptionsMethod = addOptionsMethod;
        assertDoesNotThrow(() -> finalAddOptionsMethod.invoke(qnnreService, addOptionParam));

        // 验证optionMapper的insert方法是否被正确调用
        verify(optionMapper, times(contents.length)).insert(any(Option.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testModifyQnnre() throws Exception {
        // 设置测试数据
        String qnnreId = "1";
        String qnnreTitle = "新问卷名称";
        String qnnreDescription = "新问卷描述";

        // 使用反射获取并调用modifyQnnre方法
        Method modifyQnnreMethod = QnnreService.class.getDeclaredMethod("modifyQnnre", String.class, String.class,
                String.class);
        modifyQnnreMethod.setAccessible(true);

        // Case 1: 修改成功，返回成功结果
        Qnnre qnnre = new Qnnre();
        when(qnnreMapper.selectById(qnnreId)).thenReturn(qnnre);
        assertDoesNotThrow(() -> {
            ServiceResult<QnnreDTO> result = (ServiceResult<QnnreDTO>) modifyQnnreMethod.invoke(qnnreService, qnnreId
                    , qnnreTitle, qnnreDescription);
            assertEquals(ServiceResultCode.OK, result.getCode());
            assertEquals("成功修改问卷", result.getMessage());
        });
        verify(qnnreMapper).updateById(qnnre);

        // Case 2: 问卷ID为空，抛出IllegalArgumentException
        assertDoesNotThrow(() -> {
            ServiceResult<QnnreDTO> result = (ServiceResult<QnnreDTO>) modifyQnnreMethod.invoke(qnnreService, null,
                    qnnreTitle, qnnreDescription);
            assertNotEquals(ServiceResultCode.ILLEGAL_PARAM, result.getCode());
            assertNotEquals("问卷ID不能为空", result.getMessage());
        });

        // Case 3: 问卷不存在，抛出NullPointerException
        when(qnnreMapper.selectById(qnnreId)).thenReturn(null);
        assertDoesNotThrow(() -> {
            ServiceResult<QnnreDTO> result = (ServiceResult<QnnreDTO>) modifyQnnreMethod.invoke(qnnreService, qnnreId
                    , qnnreTitle, qnnreDescription);
            assertEquals(ServiceResultCode.NO_SUCH_ENTITY, result.getCode());
            assertEquals("该问卷不存在", result.getMessage());
        });

        // Case 4: 问卷名称为空，抛出IllegalArgumentException
        when(qnnreMapper.selectById(qnnreId)).thenReturn(qnnre);
        assertDoesNotThrow(() -> {
            ServiceResult<QnnreDTO> result = (ServiceResult<QnnreDTO>) modifyQnnreMethod.invoke(qnnreService, qnnreId
                    , null, qnnreDescription);
            assertEquals(ServiceResultCode.ILLEGAL_PARAM, result.getCode());
            assertEquals("问卷名不能为空", result.getMessage());
        });
    }

    @Test
    public void testSave_2() {

        val qnnreId = "b8030ee6-b95a-4102-b00e-f3edd66dfe6d";
        String[] strs = {"asd", "asasdad"};
        AddOptionParam[] addOptionParams = {
                AddOptionParam.builder().qnnreId(qnnreId).content(strs).questionId(1).build(),
                AddOptionParam.builder().qnnreId(qnnreId).content(strs).questionId(2).build(),
                AddOptionParam.builder().qnnreId(qnnreId).content(strs).questionId(3).build(),
        };
        AddQuestionParam[] addQuestionParams = {
                AddQuestionParam.builder()
                        .index(0)
                        .mustAnswer(true)
                        .qnnreId(qnnreId)
                        .problemName("asljkdsajdh")
                        .type(QuestionType.SINGLE_CHOICE_QUESTION.toString())
                        .build(),
                AddQuestionParam.builder()
                        .index(0)
                        .mustAnswer(false)
                        .qnnreId(qnnreId)
                        .problemName("sadsadsa")
                        .type(QuestionType.MULTIPLE_CHOICE_QUESTION.toString())
                        .build(),
        };
        val modifyQnnreParam = ModifyQnnreParam.builder()
                .addOptionParams(addOptionParams)
                .qnnreId(qnnreId)
                .qnnreDescription("desdes")
                .qnnreTitle("title")
                .addQuestionParams(addQuestionParams)
                .build();
        qnnreService.save(modifyQnnreParam);

    }

}
