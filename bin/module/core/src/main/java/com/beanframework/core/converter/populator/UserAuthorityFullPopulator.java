package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.UserAuthorityDto;
import com.beanframework.core.data.UserPermissionDto;
import com.beanframework.core.data.UserRightDto;
import com.beanframework.user.domain.UserAuthority;

@Component
public class UserAuthorityFullPopulator extends AbstractPopulator<UserAuthority, UserAuthorityDto> implements Populator<UserAuthority, UserAuthorityDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(UserAuthorityFullPopulator.class);
	
	@Autowired
	private UserPermissionFullPopulator userPermissionFullPopulator;
	
	@Autowired
	private UserRightFullPopulator userRightFullPopulator;

	@Override
	public void populate(UserAuthority source, UserAuthorityDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
			target.setEnabled(source.getEnabled());
			target.setUserPermission(modelService.getDto(source.getUserPermission(), UserPermissionDto.class, new DtoConverterContext(userPermissionFullPopulator)));
			target.setUserRight(modelService.getDto(source.getUserRight(), UserRightDto.class, new DtoConverterContext(userRightFullPopulator)));
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
