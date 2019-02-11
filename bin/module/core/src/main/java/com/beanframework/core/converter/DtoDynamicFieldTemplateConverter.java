package com.beanframework.core.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.converter.InterceptorContext;
import com.beanframework.common.data.AuditorDto;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.DynamicFieldSlotDto;
import com.beanframework.core.data.DynamicFieldTemplateDto;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;

public class DtoDynamicFieldTemplateConverter implements DtoConverter<DynamicFieldTemplate, DynamicFieldTemplateDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoDynamicFieldTemplateConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public DynamicFieldTemplateDto convert(DynamicFieldTemplate source, InterceptorContext context) throws ConverterException {
		return convert(source, new DynamicFieldTemplateDto(), context);
	}

	public List<DynamicFieldTemplateDto> convert(List<DynamicFieldTemplate> sources, InterceptorContext context) throws ConverterException {
		List<DynamicFieldTemplateDto> convertedList = new ArrayList<DynamicFieldTemplateDto>();
		for (DynamicFieldTemplate source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

	private DynamicFieldTemplateDto convert(DynamicFieldTemplate source, DynamicFieldTemplateDto prototype, InterceptorContext context) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setName(source.getName());

		try {
			InterceptorContext disableInitialCollectionContext = new InterceptorContext();
			disableInitialCollectionContext.setInitializeCollection(false);

			prototype.setCreatedBy(modelService.getDto(source.getCreatedBy(), disableInitialCollectionContext, AuditorDto.class));
			prototype.setLastModifiedBy(modelService.getDto(source.getLastModifiedBy(), disableInitialCollectionContext, AuditorDto.class));

			if (context.isInitializeCollection()) {
				prototype.setDynamicFieldSlots(modelService.getDto(source.getDynamicFieldSlots(), context, DynamicFieldSlotDto.class));
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
