package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.LanguageDto;

public interface LanguageFacade {

	public static interface LanguagePreAuthorizeEnum {
		public static final String AUTHORITY_READ = "language_read";
		public static final String AUTHORITY_CREATE = "language_create";
		public static final String AUTHORITY_UPDATE = "language_update";
		public static final String AUTHORITY_DELETE = "language_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}

	@PreAuthorize(LanguagePreAuthorizeEnum.HAS_READ)
	LanguageDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(LanguagePreAuthorizeEnum.HAS_READ)
	LanguageDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(LanguagePreAuthorizeEnum.HAS_CREATE)
	LanguageDto create(LanguageDto model) throws BusinessException;

	@PreAuthorize(LanguagePreAuthorizeEnum.HAS_UPDATE)
	LanguageDto update(LanguageDto model) throws BusinessException;

	@PreAuthorize(LanguagePreAuthorizeEnum.HAS_DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(LanguagePreAuthorizeEnum.HAS_READ)
	Page<LanguageDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(LanguagePreAuthorizeEnum.HAS_READ)
	int count() throws Exception;

	@PreAuthorize(LanguagePreAuthorizeEnum.HAS_READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(LanguagePreAuthorizeEnum.HAS_READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(LanguagePreAuthorizeEnum.HAS_CREATE)
	LanguageDto createDto() throws Exception;
}
