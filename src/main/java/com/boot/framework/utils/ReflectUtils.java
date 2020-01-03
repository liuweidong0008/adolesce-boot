package com.boot.framework.utils;

import java.lang.reflect.Field;

/**
 * @author Administrator
 *	反射工具
 */
public class ReflectUtils {
    /**
     * 获取obj对象fieldName的Field
     * @param obj obj
     * @param fieldName fieldName
     * @return field
     */
    public static Field getFieldByFieldName(Object obj, String fieldName) {
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
                .getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
            }
        }
        return null;
    }

    /**
     * 获取obj对象fieldName的属性值
     * @param obj obj
     * @param fieldName fieldName
     * @return object
     * @throws SecurityException exception
     * @throws NoSuchFieldException exception
     * @throws IllegalArgumentException exception
     * @throws IllegalAccessException exception
     */
    public static Object getValueByFieldName(Object obj, String fieldName)
            throws SecurityException, NoSuchFieldException,
            IllegalArgumentException, IllegalAccessException {
        Field field = getFieldByFieldName(obj, fieldName);
        Object value = null;
        if(field!=null){
            if (field.isAccessible()) {
                value = field.get(obj);
            } else {
                field.setAccessible(true);
                value = field.get(obj);
                field.setAccessible(false);
            }
        }
        return value;
    }

    /**
     * 设置obj对象fieldName的属性值
     * @param obj obj
     * @param fieldName fieldName
     * @param value value
     * @throws SecurityException exception
     * @throws NoSuchFieldException exception
     * @throws IllegalArgumentException exception
     * @throws IllegalAccessException exception
     */
    public static void setValueByFieldName(Object obj, String fieldName,
                                           Object value) throws SecurityException, NoSuchFieldException,
            IllegalArgumentException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        if (field.isAccessible()) {
            field.set(obj, value);
        } else {
            field.setAccessible(true);
            field.set(obj, value);
            field.setAccessible(false);
        }
    }
}
