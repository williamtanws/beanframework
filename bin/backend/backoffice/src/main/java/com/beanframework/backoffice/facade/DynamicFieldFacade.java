package com.beanframework.backoffice.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.backoffice.data.DynamicFieldDto;
import com.beanframework.common.exception.BusinessException;

public interface DynamicFieldFacade {

	public static interface PreAuthorizeEnum {
		public static final String READ = "hasAuthority('dynamicfield_read')";
		public static final String CREATE = "hasAuthority('dynamicfield_create')";
		public static final String UPDATE = "hasAuthority('dynamicfield_update')";
		public static final String DELETE = "hasAuthority('dynamicfield_delete')";
	}

	@PreAuthorize(PreAuthorizeEnum.READ)
	Page<DynamicFieldDto> findPage(Specification<DynamicFieldDto> findByCriteria, PageRequest of) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	DynamicFieldDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	DynamicFieldDto findOneByProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.CREATE)
	DynamicFieldDto create(DynamicFieldDto model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.UPDATE)
	DynamicFieldDto update(DynamicFieldDto model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.READ)
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

	List<DynamicFieldDto> findAllDtoDynamicFields() throws Exception;

}
