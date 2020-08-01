package com.beanframework.common.converter;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.exception.ConverterException;

public interface DtoConverter<S, T> extends Converter {
	
	T convert(S source, DtoConverterContext context) throws ConverterException;
}
