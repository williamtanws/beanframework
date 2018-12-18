package com.beanframework.dynamicfield.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.dynamicfield.domain.DynamicField;

@Component
public class DtoDynamicFieldConverter implements DtoConverter<DynamicField, DynamicField> {

	@Override
	public DynamicField convert(DynamicField source) {
		return convert(source, new DynamicField());
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
