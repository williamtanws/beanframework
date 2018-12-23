package com.beanframework.user.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.user.domain.UserGroup;

public interface UserGroupFacade {
	
	public static interface PreAuthorizeEnum {
		public static final String READ = "hasAuthority('usergroup_read')";
		public static final String CREATE = "hasAuthority('usergroup_create')";
		public static final String UPDATE = "hasAuthority('usergroup_update')";
		public static final String DELETE = "hasAuthority('usergroup_delete')";
	}

	@PreAuthorize(PreAuthorizeEnum.READ)
	Page<UserGroup> findPage(Specification<UserGroup> specification, PageRequest pageRequest) throws Exception;

	UserGroup create() throws Exception;

	@PreAuthorize(PreAuthorizeEnum.READ)
	UserGroup findOneDtoByProperties(Map<String, Object> properties) throws Exception;

	@PreAuthorize(PreAuthorizeEnum.CREATE)
	void createDto(UserGroup model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.UPDATE)
	void updateDto(UserGroup model) throws BusinessException;

	@PreAuthorize(PreAuthorizeEnum.DELETE)
	void delete(UUID uuid) throws BusinessException;

	List<UserGroup> findDtoBySorts(Map<String, Direction> sorts) throws Exception;

}
