package com.beanframework.console.initialize;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.Initializer;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.WebPlatformConstants;
import com.beanframework.console.WebPlatformConstants.Initialize.UserGroup;

public class UserGroupInitialize extends Initializer {
	protected final Logger logger = LoggerFactory.getLogger(UserGroupInitialize.class);

	@Autowired
	private ModelService modelService;

	@PostConstruct
	public void initializer() {
		setKey(WebPlatformConstants.Initialize.UserGroup.KEY);
		setName(WebPlatformConstants.Initialize.UserGroup.NAME);
		setSort(WebPlatformConstants.Initialize.UserGroup.SORT);
		setDescription(WebPlatformConstants.Initialize.UserGroup.DESCRIPTION);
	}

	@Override
	public void initialize() {
		modelService.removeAll(UserGroup.class);
	}

}
