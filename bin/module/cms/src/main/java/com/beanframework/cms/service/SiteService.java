package com.beanframework.cms.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.beanframework.cms.domain.Site;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;

public interface SiteService {

	Site findOneEntityByUuid(UUID uuid) throws Exception;

	Site findOneEntityByProperties(Map<String, Object> properties) throws Exception;

	List<Site> findEntityBySorts(Map<String, Direction> sorts) throws Exception;

	Site saveEntity(Site model) throws BusinessException;

	void deleteByUuid(UUID uuid) throws BusinessException;

	Page<Site> findEntityPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;
}
