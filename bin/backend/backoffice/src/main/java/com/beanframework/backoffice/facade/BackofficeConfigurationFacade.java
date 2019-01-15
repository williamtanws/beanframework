package com.beanframework.backoffice.facade;

import com.beanframework.backoffice.data.BackofficeConfigurationDto;

public interface BackofficeConfigurationFacade {

	BackofficeConfigurationDto findOneDtoById(String id) throws Exception;

}
