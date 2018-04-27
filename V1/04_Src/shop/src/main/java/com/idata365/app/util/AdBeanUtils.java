package com.idata365.app.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdBeanUtils extends BeanUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdBeanUtils.class);
	
	public static void copyOtherPropToStr(Object dest, Object orig)
	{
		if (null == orig)
		{
			LOGGER.error("orig bean is null");
			return;
		}
		AdBeanUtilsBean.getInstance().copyOtherPropToStr(dest, orig);
	}
	
	public static void copyNotNullProperties(Object dest, Object orig)
	{
		AdBeanUtilsBean.getInstance().copyNotNullProperties(dest, orig);
	}
	
	/**
     * 去除bean中所有String字段两边的空格
     * @param bean
     * @return
     */
    public static void trimPojo(Object bean) {
    	if (null == bean) {
    		return;
    	}
        Class clazz = bean.getClass();
        Field[] fieldArr = clazz.getDeclaredFields();
        for (Field field : fieldArr) {
            try {
                field.setAccessible(true);
                Object fieldVal = field.get(bean);
                if (null != fieldVal && fieldVal instanceof String) {
                    String fieldName = field.getName();
                    String setMethodName =
                        new StringBuilder("set").append(fieldName.substring(0, 1).toUpperCase())
                            .append(fieldName.substring(1))
                            .toString();
                    Method method = clazz.getMethod(setMethodName, String.class);
                    method.invoke(bean, ((String)fieldVal).trim());
                }
            }
            catch (IllegalArgumentException e) {
                throw new RuntimeException(e);
            }
            catch (IllegalAccessException e) {
            	throw new RuntimeException(e);
            }
            catch (SecurityException e) {
            	throw new RuntimeException(e);
            }
            catch (NoSuchMethodException e) {
            	throw new RuntimeException(e);
            }
            catch (InvocationTargetException e) {
            	throw new RuntimeException(e);
            }
        }
    }
}
