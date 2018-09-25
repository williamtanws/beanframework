package com.beanframework.cronjob.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.beanframework.cronjob.domain.CronjobData;

@Component
public class DtoCronjobDataConverter implements Converter<CronjobData, CronjobData> {

	@Override
	public CronjobData convert(CronjobData source) {
		return convert(source, new CronjobData());
	}

	public List<CronjobData> convert(List<CronjobData> sources) {
		List<CronjobData> convertedList = new ArrayList<CronjobData>();
		for (CronjobData source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private CronjobData convert(CronjobData source, CronjobData prototype) {

		prototype.setUuid(source.getUuid());
		prototype.setName(source.getName());
		prototype.setValue(source.getValue());

		return prototype;
	}

}
