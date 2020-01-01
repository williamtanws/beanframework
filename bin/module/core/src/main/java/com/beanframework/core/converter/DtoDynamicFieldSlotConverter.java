package com.beanframework.core.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.ConvertRelationType;
import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.DynamicFieldDto;
import com.beanframework.core.data.DynamicFieldSlotDto;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;

public class DtoDynamicFieldSlotConverter extends AbstractDtoConverter<DynamicFieldSlot, DynamicFieldSlotDto> implements DtoConverter<DynamicFieldSlot, DynamicFieldSlotDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoDynamicFieldSlotConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public DynamicFieldSlotDto convert(DynamicFieldSlot source, DtoConverterContext context) throws ConverterException {
		return convert(source, new DynamicFieldSlotDto(), context);
	}

	public List<DynamicFieldSlotDto> convert(List<DynamicFieldSlot> sources, DtoConverterContext context) throws ConverterException {
		List<DynamicFieldSlotDto> convertedList = new ArrayList<DynamicFieldSlotDto>();
		for (DynamicFieldSlot source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

	private DynamicFieldSlotDto convert(DynamicFieldSlot source, DynamicFieldSlotDto prototype, DtoConverterContext context) throws ConverterException {

		try {
			convertCommonProperties(source, prototype, context);

			prototype.setName(source.getName());
			prototype.setSort(source.getSort());

			prototype.setDynamicField(modelService.getDto(source.getDynamicField(), DynamicFieldDto.class, new DtoConverterContext(ConvertRelationType.ALL)));

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
