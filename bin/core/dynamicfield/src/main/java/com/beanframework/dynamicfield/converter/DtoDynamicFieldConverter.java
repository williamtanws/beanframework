package com.beanframework.dynamicfield.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.service.DynamicFieldService;

@Component
public class DtoDynamicFieldConverter implements Converter<DynamicField, DynamicField> {

	@Autowired
	private DynamicFieldService dynamicFieldService;

	@Override
	public DynamicField convert(DynamicField source) {
		return convert(source, dynamicFieldService.create());
	}

	public List<DynamicField> convert(List<DynamicField> sources) {
		List<DynamicField> convertedList = new ArrayList<DynamicField>();
		for (DynamicField source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private DynamicField convert(DynamicField source, DynamicField prototype) {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		return prototype;
	}

}
