package com.beanframework.cronjob.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.cronjob.domain.Cronjob;

public interface CronjobFacade {
	
	public static interface PreAuthorizeEnum {
		public static final String READ = "hasAuthority('cronjob_read')";
		public static final String CREATE = "hasAuthority('cronjob_create')";
		public static final String UPDATE = "hasAuthority('cronjob_update')";
		public static final String DELETE = "hasAuthority('cronjob_delete')";
	}

	@PreAuthorize(PreAuthorizeEnum.UPDATE)
	void trigger(Cronjob cronjob) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.UPDATE)
	Cronjob addCronjobData(UUID uuid, String name, String value) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.READ)
	Page<Cronjob> findPage(Specification<Cronjob> findByCriteria, PageRequest of) throws Exception;

	Cronjob create() throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	Cronjob findOneDtoByProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.CREATE)
	void createDto(Cronjob cronjobCreate) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.UPDATE)
	void updateDto(Cronjob cronjobUpdate) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

}
