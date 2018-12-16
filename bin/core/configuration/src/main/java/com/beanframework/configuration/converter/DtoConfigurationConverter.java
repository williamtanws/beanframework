package com.beanframework.configuration.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.service.ModelService;
import com.beanframework.configuration.domain.Configuration;

public class DtoConfigurationConverter implements DtoConverter<Configuration, Configuration> {

	@Autowired
	private ModelService modelService;

	@Override
	public Configuration convert(Configuration source) {
		return convert(source, modelService.create(Configuration.class));
	}

	public List<Configuration> convert(List<Configuration> sources) {
		List<Configuration> convertedList = new ArrayList<Configuration>();
		for (Configuration source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private Configuration convert(Configuration source, Configuration prototype) {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setValue(source.getValue());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedDate(source.getLastModifiedDate());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		return prototype;
	}

}
