package com.beanframework.backoffice.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.backoffice.data.LanguageDto;
import com.beanframework.common.exception.BusinessException;

public interface LanguageFacade {

	public static interface PreAuthorizeEnum {
		public static final String READ = "hasAuthority('language_read')";
		public static final String CREATE = "hasAuthority('language_create')";
		public static final String UPDATE = "hasAuthority('language_update')";
		public static final String DELETE = "hasAuthority('language_delete')";
	}

	@PreAuthorize(PreAuthorizeEnum.READ)
	public Page<LanguageDto> findPage(Specification<LanguageDto> specification, PageRequest pageable) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	public LanguageDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	public LanguageDto findOneByProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.CREATE)
	public LanguageDto create(LanguageDto model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.UPDATE)
	public LanguageDto update(LanguageDto model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.DELETE)
	public void delete(UUID uuid) throws BusinessException;
	
	@PreAuthorize(PreAuthorizeEnum.READ)
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;


}
