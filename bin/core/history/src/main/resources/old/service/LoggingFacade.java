package com.beanframework.logging.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.beanframework.logging.domain.Logging;

public interface LoggingFacade {
	
	Page<Logging> getLogPage(Logging logging, int page, int size, Direction direction, String... properties);
	
	void log(String channel, String operate, String result);

	void log(String channel, String operate, Exception e);

	void log(String channel, String operate, String result, Exception e);

}
