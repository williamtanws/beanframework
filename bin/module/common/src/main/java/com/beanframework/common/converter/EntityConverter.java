package com.beanframework.common.converter;

import com.beanframework.common.context.EntityConverterContext;
import com.beanframework.common.exception.ConverterException;

public interface EntityConverter<S, T> extends Converter {
	T convert(S source, EntityConverterContext context) throws ConverterException;
}
