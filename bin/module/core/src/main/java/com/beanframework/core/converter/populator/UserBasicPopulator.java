package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.UserDto;
import com.beanframework.user.domain.User;

@Component
public class UserBasicPopulator extends AbstractPopulator<User, UserDto> implements Populator<User, UserDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(UserBasicPopulator.class);

	@Override
	public void populate(User source, UserDto target) throws PopulatorException {
		convertCommonProperties(source, target);
		target.setName(source.getName());
		target.setType(source.getType());
		target.setAccountNonExpired(source.getAccountNonExpired());
		target.setAccountNonLocked(source.getAccountNonLocked());
		target.setCredentialsNonExpired(source.getCredentialsNonExpired());
		target.setEnabled(source.getEnabled());
	}

}
