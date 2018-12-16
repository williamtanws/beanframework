package com.beanframework.console.initialize;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.Initializer;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.WebPlatformConstants;
import com.beanframework.console.WebPlatformConstants.Initialize.UserRight;

public class UserRightInitialize extends Initializer {
	protected final Logger logger = LoggerFactory.getLogger(UserRightInitialize.class);

	@Autowired
	private ModelService modelService;

	@PostConstruct
	public void initializer() {
		setKey(WebPlatformConstants.Initialize.UserRight.KEY);
		setName(WebPlatformConstants.Initialize.UserRight.NAME);
		setSort(WebPlatformConstants.Initialize.UserRight.SORT);
		setDescription(WebPlatformConstants.Initialize.UserRight.DESCRIPTION);
	}

	@Override
	public void initialize() {
		modelService.removeAll(UserRight.class);
	}

}
