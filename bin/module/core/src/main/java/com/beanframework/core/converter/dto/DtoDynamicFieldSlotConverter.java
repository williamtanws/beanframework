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
import com.beanframework.core.data.DynamicFieldSlotDto;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;

public class DtoDynamicFieldSlotConverter extends AbstractDtoConverter<DynamicFieldSlot, DynamicFieldSlotDto> implements DtoConverter<DynamicFieldSlot, DynamicFieldSlotDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoDynamicFieldSlotConverter.class);

	@Override
	public DynamicFieldSlotDto convert(DynamicFieldSlot source, DtoConverterContext context) throws ConverterException {
		try {
			DynamicFieldSlotDto target = new DynamicFieldSlotDto();
			populate(source, target, context);

			return target;
		} catch (PopulatorException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public List<DynamicFieldSlotDto> convert(List<DynamicFieldSlot> sources, DtoConverterContext context) throws ConverterException {
		List<DynamicFieldSlotDto> convertedList = new ArrayList<DynamicFieldSlotDto>();
		for (DynamicFieldSlot source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

}
