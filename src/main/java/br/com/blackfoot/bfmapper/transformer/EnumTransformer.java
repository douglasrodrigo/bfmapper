package br.com.blackfoot.bfmapper.transformer;

import java.lang.reflect.Method;

import org.apache.commons.lang.ClassUtils;

public class EnumTransformer implements SimpleTransformer {
	
	public <T>  T transform(Object value, Class<T> type) {
		Object returnValue = null;
		String stringValue = value != null ? value.toString() : null;

		if (stringValue == null || stringValue.equals("")) {
			return null;
		}
		
		if (type.isEnum()) { 
			if (Character.isDigit(stringValue.charAt(0))) {
				int ordinal = Integer.parseInt(stringValue);
				if (ordinal >= type.getEnumConstants().length) {
					throw new IllegalArgumentException("Impossible convert Enum, ordinal value invalid");
				}
				returnValue = type.getEnumConstants()[ordinal];
			} else {
				try {
					Method method = type.getMethod("valueOf", Class.class, String.class);
					returnValue = method.invoke(type, type, stringValue);
				} catch (Exception e) {
					throw new IllegalArgumentException("Impossible convert Enum, string value invalid");
				}
			}
		} else if (value.getClass().isEnum() && ClassUtils.isAssignable(type, String.class)) {
			returnValue = value.toString();
		} else {
			throw new IllegalArgumentException("Incorrect type for transformer class");
		}
			
		return type.cast(returnValue);
	}
}
