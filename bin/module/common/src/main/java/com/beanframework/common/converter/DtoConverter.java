package com.beanframework.common.converter;

import com.beanframework.common.exception.ConverterException;

public interface DtoConverter<S, T> extends Converter {
	T convert(S source, InterceptorContext context) throws ConverterException;
}
