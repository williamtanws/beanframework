package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.UserAuthorityDto;
import com.beanframework.user.domain.UserAuthority;

@Component
public class UserAuthorityBasicPopulator extends AbstractPopulator<UserAuthority, UserAuthorityDto> implements Populator<UserAuthority, UserAuthorityDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(UserAuthorityBasicPopulator.class);

	@Override
	public void populate(UserAuthority source, UserAuthorityDto target) throws PopulatorException {
		target.setEnabled(source.getEnabled());
	}

}
