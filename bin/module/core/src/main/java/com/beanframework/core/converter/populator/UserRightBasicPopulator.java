package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.UserRightDto;
import com.beanframework.user.domain.UserRight;

@Component
public class UserRightBasicPopulator extends AbstractPopulator<UserRight, UserRightDto> implements Populator<UserRight, UserRightDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(UserRightBasicPopulator.class);

	@Override
	public void populate(UserRight source, UserRightDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
			target.setName(source.getName());
			target.setSort(source.getSort());
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
