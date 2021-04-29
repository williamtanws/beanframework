package com.beanframework.core.facade;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.core.data.UserDto;
import com.beanframework.user.data.UserSession;

public interface UserFacade {

	UserDto findOneByUuid(UUID uuid) throws Exception;

	UserDto findOneProperties(Map<String, Object> properties) throws Exception;

	UserDto getCurrentUser() throws Exception;

	Page<UserDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	Set<UserSession> findAllSessions();

	void expireAllSessionsByUuid(UUID uuid);

	void expireAllSessions();
}
