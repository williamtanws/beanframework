package com.beanframework.dynamicfield.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.domain.DynamicFieldEnumValue;

public class DtoDynamicFieldConverter implements DtoConverter<DynamicField, DynamicField> {
	
	@Autowired
	private ModelService modelService;

	@Override
	public DynamicField convert(DynamicField source) throws ConverterException {
		return convert(source, new DynamicField());
	}

	public List<DynamicField> convert(List<DynamicField> sources) throws ConverterException {
		List<DynamicField> convertedList = new ArrayList<DynamicField>();
		for (DynamicField source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private DynamicField convert(DynamicField source, DynamicField prototype) throws ConverterException {

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
			prototype.setFieldType(source.getFieldType());
			prototype.setFieldGroup(source.getFieldGroup());
			prototype.setLabel(source.getLabel());
			prototype.setLanguage(source.getLanguage());
			prototype.setValues(modelService.getDto(source.getValues(), DynamicFieldEnumValue.class));
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
