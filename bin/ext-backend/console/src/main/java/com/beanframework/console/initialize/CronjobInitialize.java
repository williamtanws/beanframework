package com.beanframework.console.initialize;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.domain.Initializer;
import com.beanframework.console.WebPlatformConstants;
import com.beanframework.cronjob.service.CronjobFacade;

public class CronjobInitialize extends Initializer {
	protected final Logger logger = LoggerFactory.getLogger(CronjobInitialize.class);

	@Autowired
	private CronjobFacade cronjobFacade;

	@PostConstruct
	public void initializer() {
		setKey(WebPlatformConstants.Initialize.Cronjob.KEY);
		setName(WebPlatformConstants.Initialize.Cronjob.NAME);
		setSort(WebPlatformConstants.Initialize.Cronjob.SORT);
		setDescription(WebPlatformConstants.Initialize.Cronjob.DESCRIPTION);
	}

	@Override
	public void initialize() {
		cronjobFacade.deleteAll();
	}

}
