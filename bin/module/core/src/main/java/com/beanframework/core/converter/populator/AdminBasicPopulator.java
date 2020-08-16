package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.AdminDto;
import com.beanframework.user.domain.Admin;

@Component
public class AdminBasicPopulator extends AbstractPopulator<Admin, AdminDto> implements Populator<Admin, AdminDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(AdminBasicPopulator.class);

	@Override
	public void populate(Admin source, AdminDto target) throws PopulatorException {
		convertCommonProperties(source, target);
		target.setName(source.getName());
	}

}