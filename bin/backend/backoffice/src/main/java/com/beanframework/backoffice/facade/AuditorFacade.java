package com.beanframework.backoffice.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.backoffice.data.AuditorDto;

public interface AuditorFacade {

	public static interface PreAuthorizeEnum {
		public static final String READ = "hasAuthority('auditor_read')";
	}

	@PreAuthorize(PreAuthorizeEnum.READ)
	public Page<AuditorDto> findPage(Specification<AuditorDto> specification, PageRequest pageable) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	public AuditorDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	public AuditorDto findOneByProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

}
