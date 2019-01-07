package com.beanframework.dynamicfield.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.dynamicfield.domain.DynamicField;

public interface DynamicFieldFacade {

	Page<DynamicField> findDtoPage(Specification<DynamicField> findByCriteria, PageRequest of) throws Exception;

	DynamicField create() throws Exception;

	DynamicField findOneDtoByUuid(UUID uuid) throws Exception;

	DynamicField findOneDtoById(String id) throws Exception;
	
	DynamicField createDto(DynamicField configurationCreate) throws BusinessException;

	DynamicField updateDto(DynamicField configurationUpdate) throws BusinessException;

	void delete(UUID uuid) throws BusinessException;

	DynamicField saveEntity(DynamicField model) throws BusinessException;

	void deleteById(String id) throws BusinessException;

}
