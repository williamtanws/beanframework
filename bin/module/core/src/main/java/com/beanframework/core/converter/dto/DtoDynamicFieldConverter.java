package com.beanframework.core.converter.dto;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.DynamicFieldDto;
import com.beanframework.dynamicfield.domain.DynamicField;

public class DtoDynamicFieldConverter extends AbstractDtoConverter<DynamicField, DynamicFieldDto> implements DtoConverter<DynamicField, DynamicFieldDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoDynamicFieldConverter.class);

	@Override
	public DynamicFieldDto convert(DynamicField source, DtoConverterContext context) throws ConverterException {
		try {
			DynamicFieldDto target = new DynamicFieldDto();
			populate(source, target, context);

			return target;
		} catch (PopulatorException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public List<DynamicFieldDto> convert(List<DynamicField> sources, DtoConverterContext context) throws ConverterException {
		List<DynamicFieldDto> convertedList = new ArrayList<DynamicFieldDto>();
		for (DynamicField source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}
}
