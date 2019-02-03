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
		public static final String READ = "hasAuthority('dynamicfieldtemplate_read')";
		public static final String CREATE = "hasAuthority('dynamicfieldtemplate_create')";
		public static final String UPDATE = "hasAuthority('dynamicfieldtemplate_update')";
		public static final String DELETE = "hasAuthority('dynamicfieldtemplate_delete')";
	}

	@PreAuthorize(DynamicFieldTemplatePreAuthorizeEnum.READ)
	DynamicFieldTemplateDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(DynamicFieldTemplatePreAuthorizeEnum.READ)
	DynamicFieldTemplateDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(DynamicFieldTemplatePreAuthorizeEnum.CREATE)
	DynamicFieldTemplateDto create(DynamicFieldTemplateDto model) throws BusinessException;

	@PreAuthorize(DynamicFieldTemplatePreAuthorizeEnum.UPDATE)
	DynamicFieldTemplateDto update(DynamicFieldTemplateDto model) throws BusinessException;

	@PreAuthorize(DynamicFieldTemplatePreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(DynamicFieldTemplatePreAuthorizeEnum.READ)
	Page<DynamicFieldTemplateDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(DynamicFieldTemplatePreAuthorizeEnum.READ)
	int count() throws Exception;

	@PreAuthorize(DynamicFieldTemplatePreAuthorizeEnum.READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(DynamicFieldTemplatePreAuthorizeEnum.READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(DynamicFieldTemplatePreAuthorizeEnum.CREATE)
	DynamicFieldTemplateDto createDto() throws Exception;

}
