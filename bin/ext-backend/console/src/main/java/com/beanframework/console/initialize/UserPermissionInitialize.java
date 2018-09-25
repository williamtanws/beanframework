package com.beanframework.console.initialize;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.domain.Initializer;
import com.beanframework.console.WebPlatformConstants;
import com.beanframework.user.service.UserPermissionFacade;

public class UserPermissionInitialize extends Initializer {
	protected final Logger logger = LoggerFactory.getLogger(UserPermissionInitialize.class);

	@Autowired
	private UserPermissionFacade userPermissionFacade;

	@PostConstruct
	public void initializer() {
		setKey(WebPlatformConstants.Initialize.UserPermission.KEY);
		setName(WebPlatformConstants.Initialize.UserPermission.NAME);
		setSort(WebPlatformConstants.Initialize.UserPermission.SORT);
		setDescription(WebPlatformConstants.Initialize.UserPermission.DESCRIPTION);
	}

	@Override
	public void initialize() {
		userPermissionFacade.deleteAll();
	}

}
