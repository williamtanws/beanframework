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
import com.beanframework.core.converter.populator.UserAuthorityFullPopulator;
import com.beanframework.core.converter.populator.UserGroupFieldFullPopulator;
import com.beanframework.core.converter.populator.UserGroupFullPopulator;
import com.beanframework.core.data.UserAuthorityDto;
import com.beanframework.core.data.UserGroupDto;
import com.beanframework.core.data.UserGroupFieldDto;
import com.beanframework.user.domain.UserGroup;

@Component
public class UserGroupHistoryPopulator extends AbstractPopulator<UserGroup, UserGroupDto> implements Populator<UserGroup, UserGroupDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(UserGroupHistoryPopulator.class);
	
	@Autowired
	private UserAuthorityFullPopulator userAuthorityFullPopulator;
	
	@Autowired
	private UserGroupFullPopulator userGroupFullPopulator;
	
	@Autowired
	private UserGroupFieldFullPopulator userGroupFieldFullPopulator;

	@Override
	public void populate(UserGroup source, UserGroupDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
			target.setName(source.getName());
			target.setUserAuthorities(modelService.getDto(source.getUserAuthorities(), UserAuthorityDto.class, new DtoConverterContext(userAuthorityFullPopulator)));
			target.setUserGroups(modelService.getDto(source.getUserGroups(), UserGroupDto.class, new DtoConverterContext(userGroupFullPopulator)));
			target.setFields(modelService.getDto(source.getFields(), UserGroupFieldDto.class, new DtoConverterContext(userGroupFieldFullPopulator)));
			if (target.getFields() != null)
				Collections.sort(target.getFields(), new Comparator<UserGroupFieldDto>() {
					@Override
					public int compare(UserGroupFieldDto o1, UserGroupFieldDto o2) {
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
