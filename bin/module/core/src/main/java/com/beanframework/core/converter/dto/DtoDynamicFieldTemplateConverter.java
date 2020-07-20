package com.beanframework.core.converter.dto;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.context.ConvertRelationType;
import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.DynamicFieldSlotDto;
import com.beanframework.core.data.DynamicFieldTemplateDto;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;

public class DtoDynamicFieldTemplateConverter extends AbstractDtoConverter<DynamicFieldTemplate, DynamicFieldTemplateDto> implements DtoConverter<DynamicFieldTemplate, DynamicFieldTemplateDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoDynamicFieldTemplateConverter.class);

	@Override
	public DynamicFieldTemplateDto convert(DynamicFieldTemplate source, DtoConverterContext context) throws ConverterException {
		return convert(source, new DynamicFieldTemplateDto(), context);
	}

	public List<DynamicFieldTemplateDto> convert(List<DynamicFieldTemplate> sources, DtoConverterContext context) throws ConverterException {
		List<DynamicFieldTemplateDto> convertedList = new ArrayList<DynamicFieldTemplateDto>();
		for (DynamicFieldTemplate source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

	private DynamicFieldTemplateDto convert(DynamicFieldTemplate source, DynamicFieldTemplateDto prototype, DtoConverterContext context) throws ConverterException {

		try {
			convertCommonProperties(source, prototype, context);

			prototype.setName(source.getName());

			if (ConvertRelationType.ALL == context.getConverModelType()) {
				convertAll(source, prototype, context);
			} else if (ConvertRelationType.BASIC == context.getConverModelType()) {
				convertBasic(source, prototype, context);
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

	private void convertAll(DynamicFieldTemplate source, DynamicFieldTemplateDto prototype, DtoConverterContext context) throws Exception {
		prototype.setDynamicFieldSlots(modelService.getDto(source.getDynamicFieldSlots(), DynamicFieldSlotDto.class, new DtoConverterContext(ConvertRelationType.ALL)));
	}

	private void convertBasic(DynamicFieldTemplate source, DynamicFieldTemplateDto prototype, DtoConverterContext context) throws Exception {

	}

}