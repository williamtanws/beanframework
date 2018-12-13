package com.beanframework.console.initialize;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.Initializer;
import com.beanframework.console.WebPlatformConstants;
import com.beanframework.customer.service.CustomerFacade;

public class CustomerInitialize extends Initializer {
	protected final Logger logger = LoggerFactory.getLogger(CustomerInitialize.class);

	@Autowired
	private CustomerFacade customerFacade;

	@PostConstruct
	public void initializer() {
		setKey(WebPlatformConstants.Initialize.Customer.KEY);
		setName(WebPlatformConstants.Initialize.Customer.NAME);
		setSort(WebPlatformConstants.Initialize.Customer.SORT);
		setDescription(WebPlatformConstants.Initialize.Customer.DESCRIPTION);
	}

	@Override
	public void initialize() {
		customerFacade.deleteAll();
	}

}
