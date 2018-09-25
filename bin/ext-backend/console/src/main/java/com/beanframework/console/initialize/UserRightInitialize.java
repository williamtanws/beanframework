package com.beanframework.console.initialize;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.domain.Initializer;
import com.beanframework.console.WebPlatformConstants;
import com.beanframework.user.service.UserRightFacade;

public class UserRightInitialize extends Initializer {
	protected final Logger logger = LoggerFactory.getLogger(UserRightInitialize.class);

	@Autowired
	private UserRightFacade userRightFacade;

	@PostConstruct
	public void initializer() {
		setKey(WebPlatformConstants.Initialize.UserRight.KEY);
		setName(WebPlatformConstants.Initialize.UserRight.NAME);
		setSort(WebPlatformConstants.Initialize.UserRight.SORT);
		setDescription(WebPlatformConstants.Initialize.UserRight.DESCRIPTION);
	}

	@Override
	public void initialize() {
		userRightFacade.deleteAll();
	}

}
