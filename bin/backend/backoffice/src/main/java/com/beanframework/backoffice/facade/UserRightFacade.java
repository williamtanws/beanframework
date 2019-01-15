package com.beanframework.backoffice.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.backoffice.data.UserRightDto;
import com.beanframework.common.exception.BusinessException;

public interface UserRightFacade {

	public static interface PreAuthorizeEnum {
		public static final String READ = "hasAuthority('userright_read')";
		public static final String CREATE = "hasAuthority('userright_create')";
		public static final String UPDATE = "hasAuthority('userright_update')";
		public static final String DELETE = "hasAuthority('userright_delete')";
	}

	@PreAuthorize(PreAuthorizeEnum.READ)
	Page<UserRightDto> findPage(Specification<UserRightDto> specification, PageRequest pageRequest) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	UserRightDto findOneByUuid(UUID uuid) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	UserRightDto findOneByProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.CREATE)
	UserRightDto create(UserRightDto model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.UPDATE)
	UserRightDto update(UserRightDto model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.READ)
	List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	List<Object[]> findFieldHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception;

	List<UserRightDto> findAllDtoUserRights() throws Exception;
}
