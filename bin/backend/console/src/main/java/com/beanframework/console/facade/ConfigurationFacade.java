package com.beanframework.console.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.console.data.ConfigurationDto;
import com.beanframework.console.data.ConfigurationSearch;

public interface ConfigurationFacade {

	public static interface PreAuthorizeEnum {
		public static final String READ = "hasAuthority('configuration_read')";
		public static final String CREATE = "hasAuthority('configuration_create')";
		public static final String UPDATE = "hasAuthority('configuration_update')";
		public static final String DELETE = "hasAuthority('configuration_delete')";
	}

	@PreAuthorize(PreAuthorizeEnum.READ)
	Page<ConfigurationDto> findPage(ConfigurationSearch search, PageRequest pageRequest) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	ConfigurationDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	ConfigurationDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.CREATE)
	ConfigurationDto create(ConfigurationDto model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.UPDATE)
	ConfigurationDto update(ConfigurationDto model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.READ)
	List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

	List<ConfigurationDto> findAllDtoConfigurations() throws Exception;
}
