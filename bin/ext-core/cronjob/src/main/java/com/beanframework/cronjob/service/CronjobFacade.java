package com.beanframework.cronjob.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.Errors;

import com.beanframework.cronjob.domain.Cronjob;

public interface CronjobFacade {

	Cronjob create();

	Cronjob initDefaults(Cronjob cronjob);

	void trigger(Cronjob cronjob, Errors bindingResult);

	Cronjob save(Cronjob cronjob, Errors bindingResult);

	Cronjob addCronjobData(UUID uuid, String name, String value, Errors bindingResult);

	void removeCronjobData(UUID uuid) throws Exception;

	Cronjob delete(UUID uuid, Errors bindingResult);

	Cronjob deleteCronjobByGroupAndName(String jobGroup, String jobName, Errors bindingResult);
	
	void deleteAll();

	boolean isGroupAndNameExists(String jobGroup, String jobName);

	Cronjob findByUuid(UUID uuid);
	
	Cronjob findById(String id);

	Cronjob findByJobGroupAndJobName(String jobGroup, String jobName);

	Page<Cronjob> page(Cronjob cronjob, int page, int size, Direction direction, String... properties);

}
