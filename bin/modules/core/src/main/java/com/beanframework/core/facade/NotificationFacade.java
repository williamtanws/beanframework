package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.NotificationDto;

public interface NotificationFacade {
	NotificationDto findOneByUuid(UUID uuid) throws Exception;

	NotificationDto findOneProperties(Map<String, Object> properties) throws Exception;

	NotificationDto create(NotificationDto model) throws BusinessException;

	void delete(UUID uuid) throws BusinessException;

	Page<NotificationDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	List<NotificationDto> findList(Map<String, Object> properties, Map<String, Direction> sorts, Integer firstResult, Integer maxResult) throws Exception;

	int count() throws Exception;

	NotificationDto createDto() throws Exception;
}
