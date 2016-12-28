package com.dds.impl.advice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.dds.impl.DynamicDataSource;

public class DSIDAdvice implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object[] args = invocation.getArguments();
		Method m = invocation.getMethod();
		Annotation[][] annos = m.getParameterAnnotations();
		int annosLen = annos== null ? 0 : annos.length;
		String dsId = null;
		lable:
		for (int i = 0; i < annosLen; i++) {
			Annotation[] ans = annos[i];
			int anslen = ans == null ? 0 : ans.length;
			for (int j = 0; j < anslen; j++) {
				Annotation an = ans[j];
				if(an.annotationType().isAssignableFrom(Dsid.class)){
					String value = ((Dsid)an).value(); 
					Object obj = args[i];
					if(value != null && value.trim().length()>0){
						String[] props = value.split("\\.");
						Object retr = args[i];
						for (int k = 0; k < props.length; k++) {
							retr = findPropValue(retr, props[k]);
							if(retr == null){
								break;
							}
						}
						obj = retr;
						dsId = obj == null ? null : obj.toString();
						break lable;
					}
				}
			}
		}
		
		DynamicDataSource.setDSID(dsId);
		Object retrObj = invocation.proceed();
		DynamicDataSource.setDSID(null);
		return retrObj;
	}

	private Object findPropValue(Object obj, String propName) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Class<?> cls = obj.getClass();
		try {
			Field field = cls.getDeclaredField(propName);
			field.setAccessible(true);
			return field.get(obj);
		} catch (NoSuchFieldException | SecurityException e) {
			String methodName = constructGetMethodName(propName);
			try {
				Method m = cls.getDeclaredMethod(methodName);
				m.setAccessible(true);
				return m.invoke(obj);
			} catch (NoSuchMethodException | SecurityException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
	private String constructGetMethodName(String propName){
		StringBuilder sb = new StringBuilder("get");
		char[] chars = propName.toCharArray();
		sb.append(Character.toUpperCase(chars[0]));
		for (int i = 0; i < chars.length; i++) {
			sb.append(chars[i]);
		}
		return sb.toString();
	}


}
