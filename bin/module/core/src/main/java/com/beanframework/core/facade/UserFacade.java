package com.beanframework.core.facade;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.UserDto;

public interface UserFacade {
	
	public static interface UserPreAuthorizeEnum {

		public static final String AUTHORITY_READ = "user_read";
		public static final String AUTHORITY_CREATE = "user_create";
		public static final String AUTHORITY_UPDATE = "user_update";
		public static final String AUTHORITY_DELETE = "user_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}

	UserDto findOneByUuid(UUID uuid) throws Exception;

	UserDto getCurrentUser() throws Exception;

	UserDto saveProfile(UserDto user) throws BusinessException;

	Page<UserDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;
}
