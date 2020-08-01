package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.UserPermissionDto;
import com.beanframework.user.domain.UserPermission;

@Component
public class UserPermissionBasicPopulator extends AbstractPopulator<UserPermission, UserPermissionDto> implements Populator<UserPermission, UserPermissionDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(UserPermissionBasicPopulator.class);

	@Override
	public void populate(UserPermission source, UserPermissionDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
			target.setName(source.getName());
			target.setSort(source.getSort());

		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
