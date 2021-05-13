package com.beanframework.common.converter;

import com.beanframework.common.exception.ConverterException;

public interface EntityCsvConverter<S, T> extends Converter {
  T convert(S source) throws ConverterException;
}
