package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.TemplateDto;

public interface TemplateFacade {

	public static interface TemplatePreAuthorizeEnum {
		public static final String AUTHORITY_READ = "template_read";
		public static final String AUTHORITY_CREATE = "template_create";
		public static final String AUTHORITY_UPDATE = "template_update";
		public static final String AUTHORITY_DELETE = "template_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}

	@PreAuthorize(TemplatePreAuthorizeEnum.HAS_READ)
	TemplateDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(TemplatePreAuthorizeEnum.HAS_READ)
	TemplateDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(TemplatePreAuthorizeEnum.HAS_CREATE)
	TemplateDto create(TemplateDto model) throws BusinessException;

	@PreAuthorize(TemplatePreAuthorizeEnum.HAS_UPDATE)
	TemplateDto update(TemplateDto model) throws BusinessException;

	@PreAuthorize(TemplatePreAuthorizeEnum.HAS_DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(TemplatePreAuthorizeEnum.HAS_READ)
	Page<TemplateDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(TemplatePreAuthorizeEnum.HAS_READ)
	int count() throws Exception;

	@PreAuthorize(TemplatePreAuthorizeEnum.HAS_READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(TemplatePreAuthorizeEnum.HAS_READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(TemplatePreAuthorizeEnum.HAS_CREATE)
	TemplateDto createDto() throws Exception;

}
