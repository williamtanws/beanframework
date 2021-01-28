package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.CronjobDataDto;
import com.beanframework.core.data.CronjobDto;

public interface CronjobFacade {

	CronjobDto findOneByUuid(UUID uuid) throws Exception;

	CronjobDto findOneProperties(Map<String, Object> properties) throws Exception;

	CronjobDto create(CronjobDto model) throws BusinessException;

	CronjobDto update(CronjobDto model) throws BusinessException;

	void delete(UUID uuid) throws BusinessException;

	Page<CronjobDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	void trigger(CronjobDto model) throws BusinessException;

	CronjobDto addCronjobData(UUID uuid, String name, String value) throws BusinessException;

	void createCronjobData(UUID cronjobUuid, CronjobDataDto cronjobData) throws BusinessException;

	void removeCronjobData(UUID cronjobUuid, UUID cronjobDataUuid) throws BusinessException;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	CronjobDto createDto() throws Exception;

}
