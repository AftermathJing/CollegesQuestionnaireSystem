package com.github.akagawatsurunaki.ankeito;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback
public class AnkeitoApplicationTest {
    @Test
    public void testMain() {
        String[] args = {""};
        AnkeitoApplication.main(args);
    }
}
