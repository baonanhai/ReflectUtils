package com.dxj.reflect;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * 单元测试
 * Created by yueguang on 17-1-11.
 */
public class ReflectUtilsTest {

    @Test
    public void getField() throws Exception {
        assertNotNull(ReflectUtils.getField(TestClass.class, "field1"));
    }

    @Test
    public void getFieldValue() throws Exception {
        assertEquals("bbb", ReflectUtils.getFieldValue(new TestClass(), "field1"));
    }

    @Test
    public void getStaticFieldValue() throws Exception {
        assertEquals("ccc", ReflectUtils.getStaticFieldValue(TestClass.class, "field2"));
    }

    @Test
    public void setFieldValue() throws Exception {
        TestClass testClass = new TestClass();
        ReflectUtils.setFieldValue(testClass, "field1", "eee");
        assertEquals("eee", ReflectUtils.getFieldValue(testClass, "field1"));
    }

    @Test
    public void setStaticFieldValue() throws Exception {
        ReflectUtils.setStaticFieldValue(TestClass.class, "field2", "fff");
        assertEquals("fff", ReflectUtils.getStaticFieldValue(TestClass.class, "field2"));
    }

    @Test
    public void isStatic() throws Exception {
        Field f = ReflectUtils.getField(TestClass.class, "field2");
        assertTrue(ReflectUtils.isStatic(f));
        Field f1 = ReflectUtils.getField(TestClass.class, "field");
        assertFalse(ReflectUtils.isStatic(f1));
        Method m = ReflectUtils.getMethod(TestClass.class, "append", String.class, String.class);
        assertTrue(ReflectUtils.isStatic(m));
        Method m1 = ReflectUtils.getMethod(TestClass.class, "append1", String.class);
        assertFalse(ReflectUtils.isStatic(m1));
    }

    @Test
    public void isFinal() throws Exception {
        Field f = ReflectUtils.getField(TestClass.class, "field3");
        assertTrue(ReflectUtils.isFinal(f));
        Field f1 = ReflectUtils.getField(TestClass.class, "field");
        assertFalse(ReflectUtils.isFinal(f1));
        Method m = ReflectUtils.getMethod(TestClass.class, "append", String.class, String.class);
        assertFalse(ReflectUtils.isFinal(m));
        Method m1 = ReflectUtils.getMethod(TestClass.class, "append2", String.class);
        assertTrue(ReflectUtils.isFinal(m1));
    }

    @Test
    public void isPublic() throws Exception {
        Field f = ReflectUtils.getField(TestClass.class, "field");
        assertTrue(ReflectUtils.isPublic(f));
        Field f1 = ReflectUtils.getField(TestClass.class, "field1");
        assertFalse(ReflectUtils.isPublic(f1));
        Method m = ReflectUtils.getMethod(TestClass.class, "append", String.class, String.class);
        assertFalse(ReflectUtils.isPublic(m));
        Method m1 = ReflectUtils.getMethod(TestClass.class, "append1", String.class);
        assertTrue(ReflectUtils.isPublic(m1));
    }

    @Test
    public void isPrivate() throws Exception {
        Field f = ReflectUtils.getField(TestClass.class, "field2");
        assertTrue(ReflectUtils.isPrivate(f));
        Field f1 = ReflectUtils.getField(TestClass.class, "field");
        assertFalse(ReflectUtils.isPrivate(f1));
        Method m = ReflectUtils.getMethod(TestClass.class, "append", String.class, String.class);
        assertTrue(ReflectUtils.isPrivate(m));
        Method m1 = ReflectUtils.getMethod(TestClass.class, "append1", String.class);
        assertFalse(ReflectUtils.isPrivate(m1));
    }

    @Test
    public void isProtected() throws Exception {
        Field f = ReflectUtils.getField(TestClass.class, "field2");
        assertFalse(ReflectUtils.isProtected(f));
        Field f1 = ReflectUtils.getField(TestClass.class, "field");
        assertFalse(ReflectUtils.isProtected(f1));
        Method m = ReflectUtils.getMethod(TestClass.class, "append", String.class, String.class);
        assertFalse(ReflectUtils.isProtected(m));
    }

    @Test
    public void getMethod() throws Exception {
        Method m = ReflectUtils.getMethod(TestClass.class, "append", String.class, String.class);
        assertNotNull(ReflectUtils.isStatic(m));
    }

    @Test
    public void invokeStaticMethod() throws Exception {
        assertEquals("xxxttt", ReflectUtils.invokeStaticMethod(TestClass.class, "append", "xxx", "ttt"));
    }

    @Test
    public void invokeMethod() throws Exception {
        assertEquals("aaaxxx", ReflectUtils.invokeMethod(new TestClass(), "append1", "xxx"));
    }
}