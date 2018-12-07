package com.beanframework.logging.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.beanframework.logging.domain.Logging;

public interface LoggingService {

	Page<Logging> page(Logging logging, Pageable pageable);

	Logging save(Logging log);

	int deleteByLimit(int limit);

	int deleteByOldDay(int oldDay);
}
