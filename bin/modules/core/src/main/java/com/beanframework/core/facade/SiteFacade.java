package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.SiteDto;

public interface SiteFacade {

	SiteDto findOneByUuid(UUID uuid) throws Exception;

	SiteDto findOneProperties(Map<String, Object> properties) throws Exception;

	SiteDto create(SiteDto model) throws BusinessException;

	SiteDto update(SiteDto model) throws BusinessException;

	void delete(UUID uuid) throws BusinessException;

	Page<SiteDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	SiteDto createDto() throws Exception;
}
