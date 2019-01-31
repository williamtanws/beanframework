package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.data.AuditorDto;
import com.beanframework.common.data.DataTableRequest;

public interface AuditorFacade {

	public static interface PreAuthorizeEnum {
		public static final String READ = "hasAuthority('auditor_read')";
	}

	@PreAuthorize(PreAuthorizeEnum.READ)
	AuditorDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	AuditorDto findOneProperties(Map<String, Object> properties) throws Exception;

	Page<AuditorDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	List<AuditorDto> findAllDtoAuditors() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int countHistory(DataTableRequest dataTableRequest) throws Exception;

}
