package com.my.mybatis.plugin;

import com.my.mybatis.anno.DESField;
import com.my.mybatis.handles.AESHandle;
import com.my.mybatis.handles.DESHandle;
import lombok.AllArgsConstructor;

import java.lang.reflect.Field;
import java.util.*;

abstract class AbstractMybatisPlugin {

    private static final List<DESHandle> HANDLES = new ArrayList<>();

    private static final Map<String, SpecialMethodInfo> CLASS_METHOD_PARAM = new HashMap<>();

    static {
        HANDLES.add(new AESHandle());
    }

    DESHandle getDESHandle(String flag) {
        Iterator<DESHandle> iterator = HANDLES.iterator();
        while (iterator.hasNext()) {
            DESHandle desHandle = iterator.next();
            if (desHandle.support(flag)) {
                return desHandle;
            }
        }
        throw new RuntimeException("系统内部错误");
    }

    void encrypt(Object parameterObject, Class<?> aClass) {
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            DESField desField = field.getAnnotation(DESField.class);
            if (desField != null) {
                DESHandle desHandle = getDESHandle(desField.value());
                try {
                    Object value = field.get(parameterObject);
                    if (value instanceof String) {
                        field.set(parameterObject, desHandle.encrypt((String) value));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    throw new RuntimeException("内部错误");
                }
            }
        }
    }

    void decrypt(ArrayList list, Class<?> aClass) {
        Field[] declaredFields = aClass.getDeclaredFields();;
        for (Object result : list) {
            handle(declaredFields, result);
        }
    }

    void decrypt(Object result, Class<?> aClass) {
        Field[] declaredFields = aClass.getDeclaredFields();
        handle(declaredFields, result);
    }

    private void handle(Field[] declaredFields, Object result) {
        for (Field field : declaredFields) {
            field.setAccessible(true);
            DESField desField = field.getAnnotation(DESField.class);
            if (desField != null) {
                DESHandle desHandle = getDESHandle(desField.value());
                try {
                    Object value = field.get(result);
                    if (value instanceof String) {
                        field.set(result, desHandle.decrypt((String) value));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    throw new RuntimeException("内部错误");
                }
            }
        }
    }

    void putClassMethodParam(String id, SpecialMethodInfo specialMethodInfo) {
        CLASS_METHOD_PARAM.put(id, specialMethodInfo);
    }

    SpecialMethodInfo getClassMethodParam(String id) {
        return CLASS_METHOD_PARAM.get(id);
    }

    /**
     * 特殊处理信息
     */
    @AllArgsConstructor
    class SpecialMethodInfo {
        Boolean need;
        List<Class<?>> classes;
    }
}
