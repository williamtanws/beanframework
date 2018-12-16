package com.beanframework.configuration.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.beanframework.configuration.domain.Configuration;

public interface ConfigurationService {

	Page<Configuration> page(Configuration configuration, Pageable pageable);

}
