package com.beanframework.configuration.service;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.configuration.domain.Configuration;

public interface ConfigurationService {
	Configuration findOneEntityById(String id) throws Exception;

	Configuration saveEntity(Configuration model) throws BusinessException;

	void deleteById(String id) throws BusinessException;
}
