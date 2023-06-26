package com.github.akagawatsurunaki.ankeito.param;

import com.github.akagawatsurunaki.ankeito.api.param.query.QueryUserListParam;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback
public class QueryUserListParamTest {
    
    @Test
    public void testHashCode() {
        val param1 = new QueryUserListParam();
        val param2 = new QueryUserListParam();

        if (param1.hashCode() == param2.hashCode()) {
            System.out.println("equal");
        }

        param1.setUsername("test");
        if (param1.hashCode() == param2.hashCode()) {
            System.out.println("equal");
        }

        param2.setUsername("test");
        if (param1.hashCode() == param2.hashCode()) {
            System.out.println("equal");
        }
    }

    @Test
    public void testEquals() {
        val param1 = new QueryUserListParam();
        val param2 = new QueryUserListParam();

        if (param1.equals(param2)) {
            System.out.println("equal");
        }

        param1.setPageNum(1);
        if (param1.equals(param2)) {
            System.out.println("equal");
        }

        param2.setPageNum(1);
        if (param1.equals(param2)) {
            System.out.println("equal");
        }
    }

    @Test
    public void testToString() {
        val param1 = new QueryUserListParam();
        val param2 = new QueryUserListParam();
        System.out.println("param1.toString() = " + param1);
        System.out.println("param2.toString() = " + param2);
    }
    
}
