package com.github.akagawatsurunaki.ankeito.entity;

import cn.hutool.core.date.DateField;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.UUID;

import static com.github.akagawatsurunaki.ankeito.common.constant.StringValue.BASE_STRING;
import static com.github.akagawatsurunaki.ankeito.common.constant.StringValue.BASE_NAME;

@SpringBootTest
@Transactional
@Rollback
public class ProjectTest {

    /**
     * 随机生成一个项目对象
     *
     * @return 随机生成的项目对象
     */
    private Project genProjectByBuilder() {
        return Project.builder()
                .id(UUID.randomUUID().toString())
                .personInCharge(RandomUtil.randomString(BASE_NAME, 4))
                .projectName(RandomUtil.randomString(BASE_NAME, 8))
                .projectContent(RandomUtil.randomString(BASE_STRING, 32))
                .lastUpdatedBy(RandomUtil.randomString(BASE_NAME, 4))
                .createdBy(RandomUtil.randomString(BASE_NAME, 4))
                .createTime(RandomUtil.randomDate(new Date(), DateField.SECOND, Integer.MIN_VALUE, Integer.MAX_VALUE))
                .lastUpdateDate(RandomUtil.randomDate(new Date(), DateField.SECOND, Integer.MIN_VALUE, Integer.MAX_VALUE))
                .build();
    }

    private Project genProjectBySetters() {
        Project project = new Project();
        project.setId(UUID.randomUUID().toString());
        project.setPersonInCharge(RandomUtil.randomString(BASE_NAME, 4));
        project.setProjectName(RandomUtil.randomString(BASE_NAME, 8));
        project.setProjectContent(RandomUtil.randomString(BASE_STRING, 32));
        project.setLastUpdatedBy(RandomUtil.randomString(BASE_NAME, 4));
        project.setCreatedBy(RandomUtil.randomString(BASE_NAME, 4));
        project.setCreateTime(RandomUtil.randomDate(new Date(), DateField.SECOND, Integer.MIN_VALUE, Integer.MAX_VALUE));
        project.setLastUpdateDate(RandomUtil.randomDate(new Date(), DateField.SECOND, Integer.MIN_VALUE, Integer.MAX_VALUE));
        return project;
    }

    @Test
    public void testEquals() {
        val projectByBuilder = genProjectByBuilder();
        val projectBySetters = genProjectBySetters();

        Assertions.assertNotEquals(projectByBuilder, projectBySetters);

        Assertions.assertNotEquals(
                JSONObject.toJSONString(projectByBuilder),
                JSONObject.toJSONString(projectBySetters)
        );
        Assertions.assertEquals(
                JSONObject.toJSONString(projectByBuilder),
                JSONObject.toJSONString(projectByBuilder)
        );
        Assertions.assertEquals(
                JSONObject.toJSONString(projectBySetters),
                JSONObject.toJSONString(projectBySetters)
        );
    }

    @Test
    public void testEquals2() {
        val projectByBuilder1 = genProjectByBuilder();
        val projectByBuilder2 = Project.builder()
                .id(projectByBuilder1.getId())
                .personInCharge(projectByBuilder1.getPersonInCharge())
                .projectName(projectByBuilder1.getProjectName())
                .projectContent(projectByBuilder1.getProjectContent())
                .createdBy(projectByBuilder1.getCreatedBy())
                .lastUpdatedBy(projectByBuilder1.getLastUpdatedBy())
                .createTime(projectByBuilder1.getCreateTime())
                .lastUpdateDate(projectByBuilder1.getLastUpdateDate())
                .build();

        val projectBySetters1 = genProjectBySetters();
        val projectBySetters2 = new Project();
        projectBySetters2.setId(projectBySetters1.getId());
        projectBySetters2.setPersonInCharge(projectBySetters1.getPersonInCharge());
        projectBySetters2.setProjectName(projectBySetters1.getProjectName());
        projectBySetters2.setProjectContent(projectBySetters1.getProjectContent());
        projectBySetters2.setCreatedBy(projectBySetters1.getCreatedBy());
        projectBySetters2.setLastUpdatedBy(projectBySetters1.getLastUpdatedBy());
        projectBySetters2.setCreateTime(projectBySetters1.getCreateTime());
        projectBySetters2.setLastUpdateDate(projectBySetters1.getLastUpdateDate());

        Assertions.assertNotEquals(projectByBuilder1, projectBySetters1);
        Assertions.assertEquals(projectByBuilder1, projectByBuilder2);
        Assertions.assertEquals(projectBySetters1, projectBySetters2);

        // Equals and JSON serialization testing
        Assertions.assertNotEquals(JSONObject.toJSONString(projectByBuilder1), JSONObject.toJSONString(projectBySetters1));
        Assertions.assertEquals(JSONObject.toJSONString(projectByBuilder1), JSONObject.toJSONString(projectByBuilder1));
        Assertions.assertEquals(JSONObject.toJSONString(projectBySetters1), JSONObject.toJSONString(projectBySetters1));
        Assertions.assertEquals(JSONObject.toJSONString(projectByBuilder1), JSONObject.toJSONString(projectByBuilder2));
        Assertions.assertEquals(JSONObject.toJSONString(projectBySetters1), JSONObject.toJSONString(projectBySetters2));

        // Hash code testing
        val projectSet = new HashSet<Project>();
        projectSet.add(projectByBuilder1);

        // The following lines should pass since we have implemented equals/hashcode
        Assertions.assertTrue(projectSet.contains(projectByBuilder1));
        Assertions.assertTrue(projectSet.contains(projectByBuilder2));
    }


    @Test
    public void testToString() {
        val projectByBuilder = genProjectByBuilder();
        val projectBySetters = genProjectBySetters();
        System.out.println("projectBySetters = " + projectBySetters);
        System.out.println("projectByBuilder = " + projectByBuilder);
    }

}