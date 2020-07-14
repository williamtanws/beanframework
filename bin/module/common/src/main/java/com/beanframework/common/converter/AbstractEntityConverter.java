package com.beanframework.common.converter;

import java.lang.reflect.Field;
import java.util.Date;

public class AbstractEntityConverter {
	public static void setValue(Object source, Object prototype, String fieldName) throws Exception {

		Class<?> sourceClass = source.getClass();
		Field sourceField = sourceClass.getDeclaredField(fieldName);
		sourceField.setAccessible(true);
		Object sourceValue = sourceField.get(source);

		Class<?> targetClass = prototype.getClass();
		Field targetField = targetClass.getDeclaredField(fieldName);
		targetField.setAccessible(true);
		Object targetValue = targetField.get(prototype);

		if (sourceValue != null)
			if (sourceValue.equals(targetValue) == Boolean.FALSE) {
				targetField.set(prototype, sourceValue);

				Field lastModificationField = targetClass.getDeclaredField("lastModifiedDate");
				lastModificationField.setAccessible(true);
				lastModificationField.set(prototype, new Date());

			}
	}
}
