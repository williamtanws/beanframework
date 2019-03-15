package com.beanframework.core.bean;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.configuration.domain.Configuration;
import com.beanframework.configuration.service.ConfigurationService;

public class ConfigBeanImpl implements ConfigBean {

	@Autowired
	private ConfigurationService configurationService;

	@Override
	public String get(String id) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Configuration.ID, id);
		Configuration entity = configurationService.findOneEntityByProperties(properties);
		if (entity == null) {
			return null;
		} else {
			return entity.getValue();
		}
	}

	@Override
	public String get(String id, String defaultValue) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Configuration.ID, id);
		Configuration entity = configurationService.findOneEntityByProperties(properties);
		if (entity == null) {
			return defaultValue;
		} else {
			return entity.getValue();
		}
	}
}
