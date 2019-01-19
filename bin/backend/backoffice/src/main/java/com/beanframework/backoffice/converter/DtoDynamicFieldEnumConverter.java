package com.beanframework.backoffice.converter;

import java.util.ArrayList;
import java.util.List;

import com.beanframework.backoffice.data.DynamicFieldEnumDto;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.dynamicfield.domain.DynamicFieldEnum;

public class DtoDynamicFieldEnumConverter implements DtoConverter<DynamicFieldEnum, DynamicFieldEnumDto> {

	@Override
	public DynamicFieldEnumDto convert(DynamicFieldEnum source) {
		return convert(source, new DynamicFieldEnumDto());
	}

	public List<DynamicFieldEnumDto> convert(List<DynamicFieldEnum> sources) {
		List<DynamicFieldEnumDto> convertedList = new ArrayList<DynamicFieldEnumDto>();
		for (DynamicFieldEnum source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private DynamicFieldEnumDto convert(DynamicFieldEnum source, DynamicFieldEnumDto prototype) {

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
