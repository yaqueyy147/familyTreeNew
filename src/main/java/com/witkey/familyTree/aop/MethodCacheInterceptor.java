/**
 * Copyright 2008-2009. Chongqing Communications Industry Services Co.,Ltd Information Technology Branch. All rights
 * reserved. <a>http://www.cqcis.com</a>
 */
package com.witkey.familyTree.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuhz create 2009-1-5
 */
public class MethodCacheInterceptor implements MethodInterceptor {
	Log logger = LogFactory.getLog(this.getClass());

	private Map<String, Object> cache = new HashMap<String, Object>();

	public Object invoke(MethodInvocation invocation) throws Throwable {
		String targetName = invocation.getThis().getClass().getName();
		Method invocationMethod = invocation.getMethod();
		NeedCache nc = invocationMethod.getAnnotation(NeedCache.class);
		if (nc == null) {
			return invocation.proceed();
		}

		String methodName = invocation.getMethod().getName();
		Object[] arguments = invocation.getArguments();
		Object result;

		String cacheKey = getCacheKey(targetName, methodName, arguments);
		Object element = cache.get(cacheKey);
		if (element == null) {
			result = invocation.proceed();
			cache.put(cacheKey, result);
		}
		return element;
	}

	private String getCacheKey(String targetName, String methodName, Object[] arguments) {
		StringBuffer sb = new StringBuffer();
		sb.append(targetName).append(".").append(methodName);
		if ((arguments != null) && (arguments.length != 0)) {
			for (int i = 0; i < arguments.length; i++) {
				sb.append(".").append(arguments[i]);
			}
		}
		return sb.toString();
	}

}
