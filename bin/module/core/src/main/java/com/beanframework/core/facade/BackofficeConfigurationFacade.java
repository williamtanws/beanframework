package com.beanframework.core.facade;

import com.beanframework.core.data.BackofficeConfigurationDto;

public interface BackofficeConfigurationFacade {

	BackofficeConfigurationDto findOneDtoById(String id) throws Exception;

}
