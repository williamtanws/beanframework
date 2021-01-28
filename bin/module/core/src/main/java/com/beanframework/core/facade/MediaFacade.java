package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.MediaDto;

public interface MediaFacade {

	MediaDto findOneByUuid(UUID uuid) throws Exception;

	MediaDto findOneProperties(Map<String, Object> properties) throws Exception;

	MediaDto create(MediaDto model) throws BusinessException;

	MediaDto update(MediaDto model) throws BusinessException;

	void delete(UUID uuid) throws BusinessException;

	Page<MediaDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	MediaDto createDto() throws Exception;

}
