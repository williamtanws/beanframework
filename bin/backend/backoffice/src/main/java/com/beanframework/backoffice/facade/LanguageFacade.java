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

	public static interface PreAuthorizeEnum {
		public static final String READ = "hasAuthority('language_read')";
		public static final String CREATE = "hasAuthority('language_create')";
		public static final String UPDATE = "hasAuthority('language_update')";
		public static final String DELETE = "hasAuthority('language_delete')";
	}

	@PreAuthorize(PreAuthorizeEnum.READ)
	LanguageDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	LanguageDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.CREATE)
	LanguageDto create(LanguageDto model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.UPDATE)
	LanguageDto update(LanguageDto model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	Page<LanguageDto> findPage(DataTableRequest<LanguageDto> dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception;

	int countHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception;

	List<LanguageDto> findAllDtoLanguages() throws Exception;

}
