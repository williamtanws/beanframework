package com.beanframework.configuration.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.beanframework.configuration.domain.Configuration;

public interface ConfigurationFacade {

	Page<Configuration> page(Configuration admin, int page, int size, Direction direction, String... properties);
}
