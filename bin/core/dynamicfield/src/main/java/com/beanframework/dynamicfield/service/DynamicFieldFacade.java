package com.beanframework.dynamicfield.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.dynamicfield.domain.DynamicField;

public interface DynamicFieldFacade {

	public static interface PreAuthorizeEnum {
		public static final String READ = "hasAuthority('dynamicfield_read')";
		public static final String CREATE = "hasAuthority('dynamicfield_create')";
		public static final String UPDATE = "hasAuthority('dynamicfield_update')";
		public static final String DELETE = "hasAuthority('dynamicfield_delete')";
	}

	@PreAuthorize(PreAuthorizeEnum.READ)
	Page<DynamicField> findDtoPage(Specification<DynamicField> findByCriteria, PageRequest of) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	DynamicField findOneDtoByUuid(UUID uuid) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	DynamicField findOneDtoByProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.CREATE)
	DynamicField createDto(DynamicField configurationCreate) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.UPDATE)
	DynamicField updateDto(DynamicField configurationUpdate) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	DynamicField create() throws Exception;

	DynamicField saveEntity(DynamicField model) throws BusinessException;

	void deleteById(String id) throws BusinessException;

	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

}
