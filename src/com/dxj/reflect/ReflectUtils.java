package com.dxj.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射处理辅助类
 * Created by yueguang on 17-1-10.
 */
public class ReflectUtils {

    /**
     * 获取类属性（包含私有以及受保护的，父类也会检索）
     *
     * @param sourceClass 类
     * @param fieldName   属性名称
     * @return 属性
     */
    public static Field getField(Class<?> sourceClass, String fieldName) {
        Field field = null;
        try {
            field = sourceClass.getField(fieldName);
        } catch (NoSuchFieldException ignored) {

        }

        if (field == null) {
            try {
                field = sourceClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException ignored) {

            }
        }

        if (field == null) {
            Class<?> superClass = sourceClass.getSuperclass();
            if (superClass != null) {
                field = getField(superClass, fieldName);
            }
        }
        return field;
    }

    /**
     * 获取属性的值
     *
     * @param object    对象
     * @param fieldName 属性名
     * @return 属性值
     */
    public static Object getFieldValue(Object object, String fieldName) {
        Object value = null;
        if (object != null) {
            Field field = getField(object.getClass(), fieldName);
            if (field != null) {
                field.setAccessible(true);
                try {
                    value = field.get(object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("Field is not exist");
            }
        }
        return value;
    }

    /**
     * 获取静态属性值
     *
     * @param sourceClass 类
     * @param fieldName   属性名
     * @return 属性值
     */
    public static Object getStaticFieldValue(Class<?> sourceClass, String fieldName) {
        Field field = getField(sourceClass, fieldName);
        Object value = null;
        if (field != null) {
            field.setAccessible(true);
            if (isStatic(field)) {
                try {
                    value = field.get(null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("Field is not static");
            }
        } else {
            System.err.println("Field is not exist");
        }
        return value;
    }

    /**
     * 设置属性值
     *
     * @param object    对象
     * @param fieldName 属性名
     * @param newValue  新值
     */
    public static void setFieldValue(Object object, String fieldName, Object newValue) {
        if (object != null) {
            Field field = getField(object.getClass(), fieldName);
            if (field != null) {
                field.setAccessible(true);
                if (!isFinal(field)) {
                    try {
                        field.set(object, newValue);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.err.println("Field is final");
                }
            } else {
                System.err.println("Field is not exist");
            }
        }
    }

    /**
     * 设置静态属性值
     *
     * @param sourceClass 类
     * @param fieldName   属性名
     * @param newValue    新值
     */
    public static void setStaticFieldValue(Class<?> sourceClass, String fieldName, Object newValue) {
        Field field = getField(sourceClass, fieldName);
        if (field != null) {
            field.setAccessible(true);
            if (isStatic(field)) {
                if (!isFinal(field)) {
                    try {
                        field.set(null, newValue);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.err.println("Field is final");
                }
            } else {
                System.err.println("Field is not static");
            }
        } else {
            System.err.println("Field is not exist");
        }
    }

    /**
     * 是否静态（方法、属性。。。）
     *
     * @param field 要判断的对象
     * @return 是否
     */
    public static boolean isStatic(Object field) {
        return java.lang.reflect.Modifier.isStatic((Integer) invokeMethod(field, "getModifiers", null));
    }

    /**
     * 是否不可变/覆写（方法、属性。。。）
     *
     * @param field 要判断的对象
     * @return 是否
     */
    public static boolean isFinal(Object field) {
        return java.lang.reflect.Modifier.isFinal((Integer) invokeMethod(field, "getModifiers", null));
    }

    /**
     * 是否公共（方法、属性。。。）
     *
     * @param field 要判断的对象
     * @return 是否
     */
    public static boolean isPublic(Object field) {
        return java.lang.reflect.Modifier.isPublic((Integer) invokeMethod(field, "getModifiers", null));
    }

    /**
     * 是否私有（方法、属性。。。）
     *
     * @param field 要判断的对象
     * @return 是否
     */
    public static boolean isPrivate(Object field) {
        return java.lang.reflect.Modifier.isPrivate((Integer) invokeMethod(field, "getModifiers", null));
    }

    /**
     * 是否受保护（方法、属性。。。）
     *
     * @param field 要判断的对象
     * @return 是否
     */
    public static boolean isProtected(Object field) {
        return java.lang.reflect.Modifier.isProtected((Integer) invokeMethod(field, "getModifiers", null));
    }


    /**
     * 获取类方法（包含私有以及受保护的，父类也会检索）
     *
     * @param sourceClass 类
     * @param methodName  方法名
     * @param var2        方法的参数类型
     * @return 方法对象
     */
    public static Method getMethod(Class<?> sourceClass, String methodName, Class... var2) {
        Method method = null;
        try {
            method = sourceClass.getMethod(methodName, var2);
        } catch (NoSuchMethodException ignored) {

        }

        if (method == null) {
            try {
                method = sourceClass.getDeclaredMethod(methodName, var2);
            } catch (NoSuchMethodException ignored) {

            }
        }

        if (method == null) {
            Class<?> superClass = sourceClass.getSuperclass();
            if (superClass != null) {
                method = getMethod(superClass, methodName, var2);
            }
        }
        return method;
    }

    /**
     * 执行静态方法
     *
     * @param sourceClass 类
     * @param methodName  方法名
     * @param var2        参数
     * @return 执行结果
     */
    public static Object invokeStaticMethod(Class<?> sourceClass, String methodName, Object... var2) {
        Class<?>[] args = null;
        if (var2 != null) {
            args = new Class[var2.length];
            for (int i = 0; i < var2.length; i++) {
                args[i] = var2[i].getClass();
            }
        }
        Method method = getMethod(sourceClass, methodName, args);
        Object result = null;
        try {
            if (method != null) {
                if (isStatic(method)) {
                    method.setAccessible(true);
                    result = method.invoke(null, var2);
                } else {
                    System.err.println("method is not static");
                }
            } else {
                System.err.println("Method is not exist");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 执行类方法
     *
     * @param sourceObject 类对象
     * @param methodName   方法名
     * @param var2         参数
     * @return 执行结果
     */
    public static Object invokeMethod(Object sourceObject, String methodName, Object... var2) {
        Class<?>[] args = null;
        if (var2 != null) {
            args = new Class[var2.length];
            for (int i = 0; i < var2.length; i++) {
                args[i] = var2[i].getClass();
            }
        }
        Method method = getMethod(sourceObject.getClass(), methodName, args);
        Object result = null;
        try {
            if (method != null) {
                method.setAccessible(true);
                result = method.invoke(sourceObject, var2);
            } else {
                System.err.println("Method is not exist");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }
}
