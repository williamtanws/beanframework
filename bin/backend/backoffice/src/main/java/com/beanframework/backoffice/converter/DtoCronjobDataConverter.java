package com.beanframework.backoffice.converter;

import java.util.ArrayList;
import java.util.List;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.converter.ModelAction;
import com.beanframework.core.data.CronjobDataDto;
import com.beanframework.cronjob.domain.CronjobData;

public class DtoCronjobDataConverter implements DtoConverter<CronjobData, CronjobDataDto> {

	@Override
	public CronjobDataDto convert(CronjobData source, ModelAction action) {
		return convert(source, new CronjobDataDto(), action);
	}

	public List<CronjobDataDto> convert(List<CronjobData> sources, ModelAction action) {
		List<CronjobDataDto> convertedList = new ArrayList<CronjobDataDto>();
		for (CronjobData source : sources) {
			convertedList.add(convert(source, action));
		}
		return convertedList;
	}

	private CronjobDataDto convert(CronjobData source, CronjobDataDto prototype, ModelAction action) {

		prototype.setUuid(source.getUuid());
		prototype.setName(source.getName());
		prototype.setValue(source.getValue());

		return prototype;
	}

}
