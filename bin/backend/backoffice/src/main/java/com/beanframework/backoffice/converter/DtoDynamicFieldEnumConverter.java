package com.beanframework.backoffice.converter;

import java.util.ArrayList;
import java.util.List;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.dynamicfield.domain.DynamicFieldEnum;

public class DtoDynamicFieldEnumConverter implements DtoConverter<DynamicFieldEnum, DynamicFieldEnum> {

	@Override
	public DynamicFieldEnum convert(DynamicFieldEnum source) {
		return convert(source, new DynamicFieldEnum());
	}

	public List<DynamicFieldEnum> convert(List<DynamicFieldEnum> sources) {
		List<DynamicFieldEnum> convertedList = new ArrayList<DynamicFieldEnum>();
		for (DynamicFieldEnum source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private DynamicFieldEnum convert(DynamicFieldEnum source, DynamicFieldEnum prototype) {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setEnumGroup(source.getEnumGroup());
		prototype.setName(source.getName());
		prototype.setSort(source.getSort());

		return prototype;
	}

}
