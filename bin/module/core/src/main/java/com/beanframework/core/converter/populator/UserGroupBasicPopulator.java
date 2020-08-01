package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.UserGroupDto;
import com.beanframework.user.domain.UserGroup;

@Component
public class UserGroupBasicPopulator extends AbstractPopulator<UserGroup, UserGroupDto> implements Populator<UserGroup, UserGroupDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(UserGroupBasicPopulator.class);

	@Override
	public void populate(UserGroup source, UserGroupDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
			target.setName(source.getName());
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
