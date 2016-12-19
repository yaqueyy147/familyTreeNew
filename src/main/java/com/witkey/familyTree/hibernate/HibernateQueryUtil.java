/**
 * Copyright 2007-2008. Chongqing First Information & Network Co., Ltd. All
 * rights reserved. <a>http://www.cqfirst.com.cn</a>
 */
package com.witkey.familyTree.hibernate;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * @author 长江 创建于 2007-9-14
 */
@SuppressWarnings("unchecked")
public class HibernateQueryUtil {
	public static final String TYPE_LONG = "java.lang.Long";

	public static final String TYPE_INTEGER = "java.lang.Integer";
	
	public static final String TYPE_DATE = "java.util.Date";

	public static String getTypeOfField(Class clazz, String fieldName) {
		Field field = null;
		try {
			if (fieldName.indexOf('.') == -1) {
				field = clazz.getDeclaredField(fieldName);
			} else {
				field = clazz.getDeclaredField(StringUtils.substringBefore(fieldName, "."));
				return getTypeOfField(field.getType(), StringUtils.substringAfter(fieldName, "."));
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return field != null ? field.getType().getName() : "";
	}

	public static Map changeStringToClassFieldTypeOfMap(Class clazz, Map map) {
		Set<String> keys = map.keySet();
		for (String key : keys) {
			Object value = map.get(key);
			if (value instanceof String) {
				String str = (String) map.get(key);
				if (TYPE_LONG.equals(getTypeOfField(clazz, key))) {
					map.put(key, Long.valueOf(str));
				} else if (TYPE_INTEGER.equals(getTypeOfField(clazz, key))) {
					map.put(key, Integer.valueOf(str));
				}
			}
		}
		return map;
	}

	public static Map changeStringToClassFieldTypeOfMap(Class clazz, Map map, Boolean forceClear) {
		Set<String> keys = map.keySet();
		for (String key : keys) {
			String srcKey = key;
			if (forceClear) {
				srcKey = srcKey.replaceAll("\\>|\\<|\\=|\\!|\\ |\\t", "");
			}
			Object value = map.get(key);
			if (value instanceof String) {
				String str = (String) map.get(key);
				if (TYPE_LONG.equals(getTypeOfField(clazz, srcKey))) {
					str = str.trim();
					map.put(key, Long.valueOf(str));
				} else if (TYPE_INTEGER.equals(getTypeOfField(clazz, srcKey))) {
					str = str.trim();
					map.put(key, Integer.valueOf(str));
				}else if (TYPE_DATE.equals(getTypeOfField(clazz, srcKey))) {
					str = str.trim();
					map.put(key, new Date(Long.valueOf(str)));
				}
			}
		}
		return map;
	}
}
