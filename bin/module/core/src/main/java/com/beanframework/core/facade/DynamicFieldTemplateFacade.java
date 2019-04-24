package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.DynamicFieldTemplateDto;

public interface DynamicFieldTemplateFacade {

	public static interface DynamicFieldTemplatePreAuthorizeEnum {
		public static final String AUTHORITY_READ = "dynamicfieldtemplate_read";
		public static final String AUTHORITY_CREATE = "dynamicfieldtemplate_create";
		public static final String AUTHORITY_UPDATE = "dynamicfieldtemplate_update";
		public static final String AUTHORITY_DELETE = "dynamicfieldtemplate_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}

	@PreAuthorize(DynamicFieldTemplatePreAuthorizeEnum.HAS_READ)
	DynamicFieldTemplateDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(DynamicFieldTemplatePreAuthorizeEnum.HAS_READ)
	DynamicFieldTemplateDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(DynamicFieldTemplatePreAuthorizeEnum.HAS_CREATE)
	DynamicFieldTemplateDto create(DynamicFieldTemplateDto model) throws BusinessException;

	@PreAuthorize(DynamicFieldTemplatePreAuthorizeEnum.HAS_UPDATE)
	DynamicFieldTemplateDto update(DynamicFieldTemplateDto model) throws BusinessException;

	@PreAuthorize(DynamicFieldTemplatePreAuthorizeEnum.HAS_DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(DynamicFieldTemplatePreAuthorizeEnum.HAS_READ)
	Page<DynamicFieldTemplateDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(DynamicFieldTemplatePreAuthorizeEnum.HAS_READ)
	int count() throws Exception;

	@PreAuthorize(DynamicFieldTemplatePreAuthorizeEnum.HAS_READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(DynamicFieldTemplatePreAuthorizeEnum.HAS_READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(DynamicFieldTemplatePreAuthorizeEnum.HAS_CREATE)
	DynamicFieldTemplateDto createDto() throws Exception;

}
