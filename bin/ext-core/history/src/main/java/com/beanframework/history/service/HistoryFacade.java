package com.beanframework.history.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.Errors;

import com.beanframework.history.domain.History;

public interface HistoryFacade {

	History create();

	History initDefaults(History history);

	History save(History history, Errors bindingResult);

	void delete(UUID uuid, Errors bindingResult);

	void deleteAll();

	History findByUuid(UUID uuid);
	
	Page<History> page(History history, int page, int size, Direction direction, String... properties);
}
