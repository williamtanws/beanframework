package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.CronjobDataDto;
import com.beanframework.core.data.CronjobDto;

public interface CronjobFacade {

	public static interface CronjobPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "cronjob_read";
		public static final String AUTHORITY_CREATE = "cronjob_create";
		public static final String AUTHORITY_UPDATE = "cronjob_update";
		public static final String AUTHORITY_DELETE = "cronjob_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}

	@PreAuthorize(CronjobPreAuthorizeEnum.HAS_READ)
	CronjobDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(CronjobPreAuthorizeEnum.HAS_READ)
	CronjobDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(CronjobPreAuthorizeEnum.HAS_CREATE)
	CronjobDto create(CronjobDto model) throws BusinessException;

	@PreAuthorize(CronjobPreAuthorizeEnum.HAS_UPDATE)
	CronjobDto update(CronjobDto model) throws BusinessException;

	@PreAuthorize(CronjobPreAuthorizeEnum.HAS_DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(CronjobPreAuthorizeEnum.HAS_READ)
	Page<CronjobDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(CronjobPreAuthorizeEnum.HAS_READ)
	int count() throws Exception;

	@PreAuthorize(CronjobPreAuthorizeEnum.HAS_UPDATE)
	void trigger(CronjobDto model) throws BusinessException;

	@PreAuthorize(CronjobPreAuthorizeEnum.HAS_UPDATE)
	CronjobDto addCronjobData(UUID uuid, String name, String value) throws BusinessException;

	@PreAuthorize(CronjobPreAuthorizeEnum.HAS_UPDATE)
	void updateCronjobData(UUID cronjobUuid, CronjobDataDto cronjobData) throws BusinessException;

	@PreAuthorize(CronjobPreAuthorizeEnum.HAS_UPDATE)
	void removeCronjobData(UUID cronjobUuid, UUID cronjobDataUuid) throws BusinessException;

	@PreAuthorize(CronjobPreAuthorizeEnum.HAS_READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(CronjobPreAuthorizeEnum.HAS_READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(CronjobPreAuthorizeEnum.HAS_CREATE)
	CronjobDto createDto() throws Exception;

}
