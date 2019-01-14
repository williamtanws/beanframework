package com.beanframework.backoffice.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.user.domain.UserRight;

public interface UserRightFacade {

	public static interface PreAuthorizeEnum {
		public static final String READ = "hasAuthority('userright_read')";
		public static final String CREATE = "hasAuthority('userright_create')";
		public static final String UPDATE = "hasAuthority('userright_update')";
		public static final String DELETE = "hasAuthority('userright_delete')";
	}

	@PreAuthorize(PreAuthorizeEnum.READ)
	Page<UserRight> findPage(Specification<UserRight> specification, PageRequest pageRequest) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	UserRight findOneDtoByUuid(UUID uuid) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	UserRight findOneDtoByProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.CREATE)
	UserRight createDto(UserRight model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.UPDATE)
	UserRight updateDto(UserRight model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.READ)
	List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	List<Object[]> findFieldHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;
}
