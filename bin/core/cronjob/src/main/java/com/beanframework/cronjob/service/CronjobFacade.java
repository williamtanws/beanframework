package com.beanframework.cronjob.service;

import java.util.UUID;

import com.beanframework.cronjob.domain.Cronjob;

public interface CronjobFacade {

	void trigger(Cronjob cronjob);

	Cronjob addCronjobData(UUID uuid, String name, String value) throws Exception;

}
