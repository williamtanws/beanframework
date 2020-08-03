package com.beanframework.core.converter.populator.history;

import java.util.Collections;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.converter.populator.AbstractPopulator;
import com.beanframework.core.data.UserDto;
import com.beanframework.core.data.UserFieldDto;
import com.beanframework.core.data.UserGroupDto;
import com.beanframework.user.domain.User;

@Component
public class UserHistoryPopulator extends AbstractPopulator<User, UserDto> implements Populator<User, UserDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(UserHistoryPopulator.class);
	
	@Autowired
	private UserGroupHistoryPopulator userGroupHistoryPopulator;
	
	@Autowired
	private UserFieldHistoryPopulator userFieldHistoryPopulator;

	@Override
	public void populate(User source, UserDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
			target.setName(source.getName());
			target.setPassword("******");
			target.setAccountNonExpired(source.getAccountNonExpired());
			target.setAccountNonLocked(source.getAccountNonLocked());
			target.setCredentialsNonExpired(source.getCredentialsNonExpired());
			target.setEnabled(source.getEnabled());
			target.setUserGroups(modelService.getDto(source.getUserGroups(), UserGroupDto.class, new DtoConverterContext(userGroupHistoryPopulator)));

			target.setFields(modelService.getDto(source.getFields(), UserFieldDto.class, new DtoConverterContext(userFieldHistoryPopulator)));
			if (target.getFields() != null)
				Collections.sort(target.getFields(), new Comparator<UserFieldDto>() {
					@Override
					public int compare(UserFieldDto o1, UserFieldDto o2) {
						if (o1.getDynamicFieldSlot().getSort() == null)
							return o2.getDynamicFieldSlot().getSort() == null ? 0 : 1;

						if (o2.getDynamicFieldSlot().getSort() == null)
							return -1;

						return o1.getDynamicFieldSlot().getSort() - o2.getDynamicFieldSlot().getSort();
					}
				});
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
