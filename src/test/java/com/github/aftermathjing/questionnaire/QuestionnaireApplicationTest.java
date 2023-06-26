package com.github.aftermathjing.questionnaire;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback
public class QuestionnaireApplicationTest {
    @Test
    public void testMain() {
        String[] args = {""};
        QuestionnaireApplication.main(args);
    }
}
