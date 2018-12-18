package com.beanframework.cronjob.service;

import java.util.UUID;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.cronjob.domain.Cronjob;

public interface CronjobFacade {

	void trigger(Cronjob cronjob) throws BusinessException;

	Cronjob addCronjobData(UUID uuid, String name, String value) throws BusinessException;

}
