package com.beanframework.backoffice.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.backoffice.data.AuditorDto;
import com.beanframework.backoffice.data.AuditorSearch;

public interface AuditorFacade {

	public static interface PreAuthorizeEnum {
		public static final String READ = "hasAuthority('auditor_read')";
	}

	@PreAuthorize(PreAuthorizeEnum.READ)
	Page<AuditorDto> findPage(AuditorSearch search, PageRequest pageRequest) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	AuditorDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	AuditorDto findOneProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

	List<AuditorDto> findAllDtoAuditors() throws Exception;

}
