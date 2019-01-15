package com.beanframework.backoffice.converter;

import java.util.ArrayList;
import java.util.List;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.dynamicfield.domain.DynamicFieldEnumValue;

public class DtoDynamicFieldEnumValueConverter implements DtoConverter<DynamicFieldEnumValue, DynamicFieldEnumValue> {

	@Override
	public DynamicFieldEnumValue convert(DynamicFieldEnumValue source) {
		return convert(source, new DynamicFieldEnumValue());
	}

	public List<DynamicFieldEnumValue> convert(List<DynamicFieldEnumValue> sources) {
		List<DynamicFieldEnumValue> convertedList = new ArrayList<DynamicFieldEnumValue>();
		for (DynamicFieldEnumValue source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private DynamicFieldEnumValue convert(DynamicFieldEnumValue source, DynamicFieldEnumValue prototype) {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());
		
		prototype.setValue(source.getValue());

		return prototype;
	}

}
