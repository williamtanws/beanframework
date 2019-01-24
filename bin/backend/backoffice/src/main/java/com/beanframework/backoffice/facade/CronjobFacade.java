package com.beanframework.backoffice.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.backoffice.data.CronjobDataDto;
import com.beanframework.backoffice.data.CronjobDto;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;

public interface CronjobFacade {

	public static interface PreAuthorizeEnum {
		public static final String READ = "hasAuthority('cronjob_read')";
		public static final String CREATE = "hasAuthority('cronjob_create')";
		public static final String UPDATE = "hasAuthority('cronjob_update')";
		public static final String DELETE = "hasAuthority('cronjob_delete')";
	}

	@PreAuthorize(PreAuthorizeEnum.READ)
	CronjobDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	CronjobDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.CREATE)
	CronjobDto create(CronjobDto model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.UPDATE)
	CronjobDto update(CronjobDto model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	Page<CronjobDto> findPage(DataTableRequest<CronjobDto> dataTableRequest) throws Exception;

	int count() throws Exception;

	@PreAuthorize(PreAuthorizeEnum.UPDATE)
	void trigger(CronjobDto model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.UPDATE)
	CronjobDto addCronjobData(UUID uuid, String name, String value) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.UPDATE)
	void updateCronjobData(UUID cronjobUuid, CronjobDataDto cronjobData) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.UPDATE)
	void removeCronjobData(UUID cronjobUuid, UUID cronjobDataUuid) throws BusinessException;

	List<Object[]> findHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception;

	int countHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception;

}
