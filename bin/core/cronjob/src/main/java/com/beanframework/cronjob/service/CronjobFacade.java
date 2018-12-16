package com.beanframework.cronjob.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.beanframework.cronjob.domain.Cronjob;

public interface CronjobFacade {

	void trigger(Cronjob cronjob);

	Cronjob addCronjobData(UUID uuid, String name, String value) throws Exception;

	Page<Cronjob> page(Cronjob cronjob, int page, int size, Direction direction, String... properties);
}
