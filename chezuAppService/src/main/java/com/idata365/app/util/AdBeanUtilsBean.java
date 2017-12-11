package com.idata365.app.util;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdBeanUtilsBean extends BeanUtilsBean {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdBeanUtilsBean.class);
	
	private static AdBeanUtilsBean instance = new AdBeanUtilsBean();
	
	private AdBeanUtilsBean()
	{
	}
	
	public static AdBeanUtilsBean getInstance()
	{
		return instance;
	}
	
	public void copyOtherPropToStr(Object dest, Object orig){
        // Validate existence of the specified beans
        if (dest == null) {
            throw new IllegalArgumentException
                    ("No destination bean specified");
        }
        if (orig == null) {
            throw new IllegalArgumentException("No origin bean specified");
        }

        LOGGER.debug("BeanUtils.copyProperties({}, {})", dest, orig);

        // Copy the properties, converting as necessary
        if (orig instanceof DynaBean) {
            DynaProperty origDescriptors[] =
                ((DynaBean) orig).getDynaClass().getDynaProperties();
            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                if (getPropertyUtils().isWriteable(dest, name)) {
                    Object value = ((DynaBean) orig).get(name);
                    if (null == value)
                    {
                    	continue;
                    }
                    if (value instanceof Object)
                    {
                    	value = value.toString();
                    }
                    else
                    {
                    	value = String.valueOf(value);
                    }
                    
                    try {
						copyProperty(dest, name, value);
					} catch (IllegalAccessException | InvocationTargetException e) {
						throw new RuntimeException("copyNotNullProperties Exception", e);
					}
                }
            }
        } else if (orig instanceof Map) {
            Iterator names = ((Map) orig).keySet().iterator();
            while (names.hasNext()) {
                String name = (String) names.next();
                if (getPropertyUtils().isWriteable(dest, name)) {
                    Object value = ((Map) orig).get(name);
                    if (null == value)
                    {
                    	continue;
                    }
                    try {
						copyProperty(dest, name, value);
					} catch (IllegalAccessException | InvocationTargetException e) {
						throw new RuntimeException("copyNotNullProperties Exception", e);
					}
                }
            }
        } else /* if (orig is a standard JavaBean) */ {
            PropertyDescriptor origDescriptors[] =
                getPropertyUtils().getPropertyDescriptors(orig);
            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                if ("class".equals(name)) {
                    continue; // No point in trying to set an object's class
                }
                if (getPropertyUtils().isReadable(orig, name) &&
                    getPropertyUtils().isWriteable(dest, name)) {
                    try {
                        Object value =
                            getPropertyUtils().getSimpleProperty(orig, name);
                        if (null == value)
                        {
                        	continue;
                        }
                        copyProperty(dest, name, value);
                    } catch (NoSuchMethodException e) {
                    	throw new RuntimeException("copyNotNullProperties Exception", e);
                    } catch (IllegalAccessException e) {
                    	throw new RuntimeException("copyNotNullProperties Exception", e);
					} catch (InvocationTargetException e) {
						throw new RuntimeException("copyNotNullProperties Exception", e);
					}
                }
            }
        }
    
	    }
	
	public void copyNotNullProperties(Object dest, Object orig){
        // Validate existence of the specified beans
        if (dest == null) {
            throw new IllegalArgumentException
                    ("No destination bean specified");
        }
        if (orig == null) {
            throw new IllegalArgumentException("No origin bean specified");
        }

        LOGGER.debug("BeanUtils.copyProperties({}, {})", dest, orig);

        // Copy the properties, converting as necessary
        if (orig instanceof DynaBean) {
            DynaProperty origDescriptors[] =
                ((DynaBean) orig).getDynaClass().getDynaProperties();
            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                if (getPropertyUtils().isWriteable(dest, name)) {
                    Object value = ((DynaBean) orig).get(name);
                    if (null == value)
                    {
                    	continue;
                    }
                    try {
						copyProperty(dest, name, value);
					} catch (IllegalAccessException | InvocationTargetException e) {
						throw new RuntimeException("copyNotNullProperties Exception", e);
					}
                }
            }
        } else if (orig instanceof Map) {
            Iterator names = ((Map) orig).keySet().iterator();
            while (names.hasNext()) {
                String name = (String) names.next();
                if (getPropertyUtils().isWriteable(dest, name)) {
                    Object value = ((Map) orig).get(name);
                    if (null == value)
                    {
                    	continue;
                    }
                    try {
						copyProperty(dest, name, value);
					} catch (IllegalAccessException | InvocationTargetException e) {
						throw new RuntimeException("copyNotNullProperties Exception", e);
					}
                }
            }
        } else /* if (orig is a standard JavaBean) */ {
            PropertyDescriptor origDescriptors[] =
                getPropertyUtils().getPropertyDescriptors(orig);
            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                if ("class".equals(name)) {
                    continue; // No point in trying to set an object's class
                }
                if (getPropertyUtils().isReadable(orig, name) &&
                    getPropertyUtils().isWriteable(dest, name)) {
                    try {
                        Object value =
                            getPropertyUtils().getSimpleProperty(orig, name);
                        if (null == value)
                        {
                        	continue;
                        }
                        copyProperty(dest, name, value);
                    } catch (NoSuchMethodException e) {
                    	throw new RuntimeException("copyNotNullProperties Exception", e);
                    } catch (IllegalAccessException e) {
                    	throw new RuntimeException("copyNotNullProperties Exception", e);
					} catch (InvocationTargetException e) {
						throw new RuntimeException("copyNotNullProperties Exception", e);
					}
                }
            }
        }
    
	    }

}
