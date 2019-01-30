package com.beanframework.backoffice.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.converter.ModelAction;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.DynamicFieldDto;
import com.beanframework.core.data.DynamicFieldEnumDto;
import com.beanframework.core.data.LanguageDto;
import com.beanframework.dynamicfield.domain.DynamicField;

public class DtoDynamicFieldConverter implements DtoConverter<DynamicField, DynamicFieldDto> {

	@Autowired
	private ModelService modelService;

	@Override
	public DynamicFieldDto convert(DynamicField source, ModelAction action) throws ConverterException {
		return convert(source, new DynamicFieldDto(), action);
	}

	public List<DynamicFieldDto> convert(List<DynamicField> sources, ModelAction action) throws ConverterException {
		List<DynamicFieldDto> convertedList = new ArrayList<DynamicFieldDto>();
		for (DynamicField source : sources) {
			convertedList.add(convert(source, action));
		}
		return convertedList;
	}

	private DynamicFieldDto convert(DynamicField source, DynamicFieldDto prototype, ModelAction action) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setName(source.getName());
		prototype.setRequired(source.getRequired());
		prototype.setRule(source.getRule());
		prototype.setSort(source.getSort());
		prototype.setType(source.getType());
		prototype.setLabel(source.getLabel());

		if (action.isInitializeCollection()) {
			try {
				prototype.setLanguage(modelService.getDto(source.getLanguage(), action, LanguageDto.class));
				prototype.setEnums(modelService.getDto(source.getEnums(), action, DynamicFieldEnumDto.class));
			} catch (Exception e) {
				throw new ConverterException(e.getMessage(), e);
			}
		}

		return prototype;
	}

}
