package com.beanframework.history.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.beanframework.history.domain.History;

public interface HistoryService {

	History create();

	History initDefaults(History history);

	History save(History history);

	void delete(UUID uuid);

	void deleteAll();

	Optional<History> findEntityByUuid(UUID uuid);

	History findByUuid(UUID uuid);

	Page<History> page(History history, Pageable pageable);

}
