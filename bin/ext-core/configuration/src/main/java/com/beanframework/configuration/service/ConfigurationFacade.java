package com.beanframework.configuration.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.Errors;

import com.beanframework.configuration.domain.Configuration;

public interface ConfigurationFacade {

	Configuration create();

	Configuration initDefaults(Configuration admin);

	Configuration save(Configuration admin, Errors bindingResult);

	void delete(UUID uuid, Errors bindingResult);

	void deleteAll();

	Configuration findByUuid(UUID uuid);

	Configuration findById(String id);

	Page<Configuration> page(Configuration admin, int page, int size, Direction direction, String... properties);
}
