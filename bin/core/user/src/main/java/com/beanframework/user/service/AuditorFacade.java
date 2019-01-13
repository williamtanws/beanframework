package com.beanframework.user.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.domain.Auditor;

public interface AuditorFacade {

	public static interface PreAuthorizeEnum {
		public static final String READ = "hasAuthority('auditor_read')";
	}

	@PreAuthorize(PreAuthorizeEnum.READ)
	public Page<Auditor> findDtoPage(Specification<Auditor> specification, PageRequest pageable) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	public Auditor findOneDtoByUuid(UUID uuid) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	public Auditor findOneDtoByProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

}
