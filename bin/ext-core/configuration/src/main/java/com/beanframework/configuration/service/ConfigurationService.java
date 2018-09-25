package com.beanframework.configuration.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.beanframework.configuration.domain.Configuration;

public interface ConfigurationService {

	Configuration create();

	Configuration initDefaults(Configuration configuration);

	Configuration save(Configuration configuration);

	void delete(UUID uuid);

	void deleteAll();
	
	Optional<Configuration> findEntityByUuid(UUID uuid);

	Optional<Configuration> findEntityById(String id);

	Configuration findByUuid(UUID uuid);

	Configuration findById(String id);

	Page<Configuration> page(Configuration configuration, Pageable pageable);

}
