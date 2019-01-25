package com.beanframework.media.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.media.domain.Media;

public interface MediaService {

	Media create() throws Exception;

	Media findOneEntityByUuid(UUID uuid) throws Exception;

	Media findOneEntityByProperties(Map<String, Object> properties) throws Exception;

	List<Media> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception;
	
	Media saveEntity(Media model) throws BusinessException;

	void deleteByUuid(UUID uuid) throws BusinessException;

	<T> Page<Media> findEntityPage(DataTableRequest<T> dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception;
}
