package com.beanframework.common.converter;

import java.util.List;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.exception.ConverterException;

public interface DtoConverter<S, T> extends Converter {
	
	T convert(S source, DtoConverterContext context) throws ConverterException;
}
