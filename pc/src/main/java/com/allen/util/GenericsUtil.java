package com.allen.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Allen on 2016/12/20.
 */
public class GenericsUtil {
    public GenericsUtil() {
    }

    public static Class getGenericClass(Class clazz) {
        return getGenericClass(clazz, 0);
    }

    public static Class getGenericClass(Class clazz, int index) {
        Type genType = clazz.getGenericSuperclass();
        if(genType instanceof ParameterizedType) {
            Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
            if(params != null && params.length >= index - 1) {
                return (Class)params[index];
            }
        }

        return null;
    }

    public static Class getSuperClassGenricType(Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    public static Class getSuperClassGenricType(Class clazz, int index) throws IndexOutOfBoundsException {
        Type genType = clazz.getGenericSuperclass();
        if(!(genType instanceof ParameterizedType)) {
            return Object.class;
        } else {
            Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
            return index < params.length && index >= 0?(!(params[index] instanceof Class)?Object.class:(Class)params[index]):Object.class;
        }
    }
}
