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
		public static final String READ = "hasAuthority('configuration_read')";
		public static final String CREATE = "hasAuthority('configuration_create')";
		public static final String UPDATE = "hasAuthority('configuration_update')";
		public static final String DELETE = "hasAuthority('configuration_delete')";
	}

	@PreAuthorize(ConfigurationPreAuthorizeEnum.READ)
	ConfigurationDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(ConfigurationPreAuthorizeEnum.READ)
	ConfigurationDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(ConfigurationPreAuthorizeEnum.CREATE)
	ConfigurationDto create(ConfigurationDto model) throws BusinessException;

	@PreAuthorize(ConfigurationPreAuthorizeEnum.UPDATE)
	ConfigurationDto update(ConfigurationDto model) throws BusinessException;

	@PreAuthorize(ConfigurationPreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	Page<ConfigurationDto> findPage(DataTableRequest<ConfigurationDto> dataTableRequest) throws Exception;

	int count() throws Exception;

	List<ConfigurationDto> findAllDtoConfigurations() throws Exception;

	List<Object[]> findHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception;

	int countHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception;

}