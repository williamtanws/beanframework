package com.beanframework.backoffice.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.backoffice.data.DynamicFieldDto;
import com.beanframework.backoffice.data.DynamicFieldEnumDto;
import com.beanframework.backoffice.data.LanguageDto;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicField;

public class DtoDynamicFieldConverter implements DtoConverter<DynamicField, DynamicFieldDto> {

	@Autowired
	private ModelService modelService;

	@Override
	public DynamicFieldDto convert(DynamicField source) throws ConverterException {
		return convert(source, new DynamicFieldDto());
	}

	public List<DynamicFieldDto> convert(List<DynamicField> sources) throws ConverterException {
		List<DynamicFieldDto> convertedList = new ArrayList<DynamicFieldDto>();
		for (DynamicField source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private DynamicFieldDto convert(DynamicField source, DynamicFieldDto prototype) throws ConverterException {

		try {
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
			prototype.setLanguage(modelService.getDto(source.getLanguage(), LanguageDto.class));
			prototype.setEnums(modelService.getDto(source.getEnums(), DynamicFieldEnumDto.class));
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
