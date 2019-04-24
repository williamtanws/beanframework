package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.ConfigurationDto;

public interface ConfigurationFacade {

	public static interface ConfigurationPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "configuration_read";
		public static final String AUTHORITY_CREATE = "configuration_create";
		public static final String AUTHORITY_UPDATE = "configuration_update";
		public static final String AUTHORITY_DELETE = "configuration_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}

	@PreAuthorize(ConfigurationPreAuthorizeEnum.HAS_READ)
	ConfigurationDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(ConfigurationPreAuthorizeEnum.HAS_READ)
	ConfigurationDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(ConfigurationPreAuthorizeEnum.HAS_CREATE)
	ConfigurationDto create(ConfigurationDto model) throws BusinessException;

	@PreAuthorize(ConfigurationPreAuthorizeEnum.HAS_UPDATE)
	ConfigurationDto update(ConfigurationDto model) throws BusinessException;

	@PreAuthorize(ConfigurationPreAuthorizeEnum.HAS_DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(ConfigurationPreAuthorizeEnum.HAS_READ)
	Page<ConfigurationDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(ConfigurationPreAuthorizeEnum.HAS_READ)
	int count() throws Exception;

	@PreAuthorize(ConfigurationPreAuthorizeEnum.HAS_READ)
	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(ConfigurationPreAuthorizeEnum.HAS_READ)
	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	@PreAuthorize(ConfigurationPreAuthorizeEnum.HAS_CREATE)
	ConfigurationDto createDto() throws Exception;

	ConfigurationDto findOneDtoById(String id) throws Exception;

}
