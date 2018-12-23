package com.beanframework.language.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.language.domain.Language;

public interface LanguageFacade {

	public static interface PreAuthorizeEnum {
		public static final String READ = "hasAuthority('language_read')";
		public static final String CREATE = "hasAuthority('language_create')";
		public static final String UPDATE = "hasAuthority('language_update')";
		public static final String DELETE = "hasAuthority('language_delete')";
	}

	@PreAuthorize(PreAuthorizeEnum.READ)
	public Page<Language> findPage(Specification<Language> specification, PageRequest pageable) throws Exception;

	public Language create() throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	public Language findOneDtoByProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.CREATE)
	public void createDto(Language model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.UPDATE)
	public void updateDto(Language model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.DELETE)
	public void delete(UUID uuid) throws BusinessException;
}
