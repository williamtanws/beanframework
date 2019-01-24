package com.beanframework.backoffice.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.backoffice.data.LanguageDto;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;

public interface LanguageFacade {

	public static interface LanguagePreAuthorizeEnum {
		public static final String READ = "hasAuthority('language_read')";
		public static final String CREATE = "hasAuthority('language_create')";
		public static final String UPDATE = "hasAuthority('language_update')";
		public static final String DELETE = "hasAuthority('language_delete')";
	}

	@PreAuthorize(LanguagePreAuthorizeEnum.READ)
	LanguageDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(LanguagePreAuthorizeEnum.READ)
	LanguageDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(LanguagePreAuthorizeEnum.CREATE)
	LanguageDto create(LanguageDto model) throws BusinessException;

	@PreAuthorize(LanguagePreAuthorizeEnum.UPDATE)
	LanguageDto update(LanguageDto model) throws BusinessException;

	@PreAuthorize(LanguagePreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	Page<LanguageDto> findPage(DataTableRequest<LanguageDto> dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception;

	int countHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception;

	List<LanguageDto> findAllDtoLanguages() throws Exception;

}
