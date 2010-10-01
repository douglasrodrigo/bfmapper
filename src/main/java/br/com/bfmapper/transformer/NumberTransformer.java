package br.com.bfmapper.transformer;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ClassUtils;
import org.springframework.util.NumberUtils;

public class NumberTransformer implements SimpleTransformer {

	private static Class<?>[] decimalClasses = {Double.class, Float.class, BigDecimal.class};
	
	@Override
	@SuppressWarnings("unchecked")
	public <T>  T transform(Object value, Class<T> type) {
		Object returnValue = null;
		
		if (value == null || value.equals("")) {
			return null;
		}
		type = ClassUtils.primitiveToWrapper(type);
		
		if (type == null) {
			throw new IllegalArgumentException("Incorrect type for transformer class");
		}
		
		if (ClassUtils.isAssignable(type, Number.class) && value instanceof Number) {
			if (value.getClass().equals(type) || value.getClass().isAssignableFrom(type)) {
				returnValue = value;
			} else {
				List<Class<?>> decimalClassList = Arrays.asList(decimalClasses);
				
				if (decimalClassList.contains(value.getClass()) && !decimalClassList.contains(type)) {
					String[] split = value.toString().split("\\.");
					if (new Long(split[1]) == 0L) {
						returnValue = NumberUtils.parseNumber(split[0], type);	
					} else {
						throw new IllegalArgumentException("Incorrect type of transformer decimal to integer");
					}
				} else {
					returnValue = NumberUtils.convertNumberToTargetClass((Number) value, type);	
				}
			}
		} else if (ClassUtils.isAssignable(type, Number.class) && value instanceof String) {
			returnValue = NumberUtils.parseNumber((String) value, type);
		} else if (ClassUtils.isAssignable(type, String.class) && value instanceof Number) {
			returnValue = value.toString();
		} else {
			throw new IllegalArgumentException("Incorrect type for transformer class");
		}
		
		return type.cast(returnValue);
	}
	
}
