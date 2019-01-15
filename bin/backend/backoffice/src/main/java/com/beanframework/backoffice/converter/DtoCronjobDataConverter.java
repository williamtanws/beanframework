package com.beanframework.backoffice.converter;

import java.util.ArrayList;
import java.util.List;

import com.beanframework.backoffice.data.CronjobDataDto;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.cronjob.domain.CronjobData;

public class DtoCronjobDataConverter implements DtoConverter<CronjobData, CronjobDataDto> {

	@Override
	public CronjobDataDto convert(CronjobData source) {
		return convert(source, new CronjobDataDto());
	}

	public List<CronjobDataDto> convert(List<CronjobData> sources) {
		List<CronjobDataDto> convertedList = new ArrayList<CronjobDataDto>();
		for (CronjobData source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private CronjobDataDto convert(CronjobData source, CronjobDataDto prototype) {

		prototype.setUuid(source.getUuid());
		prototype.setName(source.getName());
		prototype.setValue(source.getValue());

		return prototype;
	}

}
