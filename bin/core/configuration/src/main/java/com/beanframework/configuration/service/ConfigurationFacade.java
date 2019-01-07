package com.beanframework.configuration.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.configuration.domain.Configuration;

public interface ConfigurationFacade {

	Page<Configuration> findDtoPage(Specification<Configuration> findByCriteria, PageRequest of) throws Exception;

	Configuration create() throws Exception;

	Configuration findOneDtoByUuid(UUID uuid) throws Exception;

	Configuration findOneDtoById(String id) throws Exception;

	Configuration createDto(Configuration configurationCreate) throws BusinessException;

	Configuration updateDto(Configuration configurationUpdate) throws BusinessException;

	void delete(UUID uuid) throws BusinessException;

	Configuration saveEntity(Configuration model) throws BusinessException;

	void deleteById(String id) throws BusinessException;

	List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

}
