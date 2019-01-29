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
		public static final String READ = "hasAuthority('cronjob_read')";
		public static final String CREATE = "hasAuthority('cronjob_create')";
		public static final String UPDATE = "hasAuthority('cronjob_update')";
		public static final String DELETE = "hasAuthority('cronjob_delete')";
	}

	@PreAuthorize(CronjobPreAuthorizeEnum.READ)
	CronjobDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(CronjobPreAuthorizeEnum.READ)
	CronjobDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(CronjobPreAuthorizeEnum.CREATE)
	CronjobDto create(CronjobDto model) throws BusinessException;

	@PreAuthorize(CronjobPreAuthorizeEnum.UPDATE)
	CronjobDto update(CronjobDto model) throws BusinessException;

	@PreAuthorize(CronjobPreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	Page<CronjobDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	@PreAuthorize(CronjobPreAuthorizeEnum.UPDATE)
	void trigger(CronjobDto model) throws BusinessException;

	@PreAuthorize(CronjobPreAuthorizeEnum.UPDATE)
	CronjobDto addCronjobData(UUID uuid, String name, String value) throws BusinessException;

	@PreAuthorize(CronjobPreAuthorizeEnum.UPDATE)
	void updateCronjobData(UUID cronjobUuid, CronjobDataDto cronjobData) throws BusinessException;

	@PreAuthorize(CronjobPreAuthorizeEnum.UPDATE)
	void removeCronjobData(UUID cronjobUuid, UUID cronjobDataUuid) throws BusinessException;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int countHistory(DataTableRequest dataTableRequest) throws Exception;

}
