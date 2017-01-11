package com.dxj.reflect;

/**
 * 测试用被测试类
 * Created by yueguang on 17-1-10.
 */
public class TestClass {
    public String field = "aaa";
    private String field1 = "bbb";
    private static String field2 = "ccc";
    private static final String field3 = "ddd";

    public String append1(String a) {
        return field + a;
    }

    private final String append2(String a) {
        return field1 + a;
    }

    private static String append(String a, String b) {
        return a + b;
    }
}
