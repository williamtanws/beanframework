package com.beanframework.language.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.language.domain.Language;

public interface LanguageService {

	Language create() throws Exception;

	Language findOneEntityByUuid(UUID uuid) throws Exception;

	Language findOneEntityByProperties(Map<String, Object> properties) throws Exception;

	List<Language> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception;
	
	Language saveEntity(Language model) throws BusinessException;

	void deleteByUuid(UUID uuid) throws BusinessException;

	<T> Page<Language> findEntityPage(DataTableRequest<T> dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception;
}
