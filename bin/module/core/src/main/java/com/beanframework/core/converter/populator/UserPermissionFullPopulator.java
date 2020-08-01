package com.beanframework.core.converter.populator;

import java.util.Collections;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.UserPermissionDto;
import com.beanframework.core.data.UserPermissionFieldDto;
import com.beanframework.user.domain.UserPermission;

@Component
public class UserPermissionFullPopulator extends AbstractPopulator<UserPermission, UserPermissionDto> implements Populator<UserPermission, UserPermissionDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(UserPermissionFullPopulator.class);

	@Autowired
	private UserPermissionFieldFullPopulator userPermissionFieldFullPopulator;

	@Override
	public void populate(UserPermission source, UserPermissionDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
			target.setName(source.getName());
			target.setSort(source.getSort());

			target.setFields(modelService.getDto(source.getFields(), UserPermissionFieldDto.class, new DtoConverterContext(userPermissionFieldFullPopulator)));
			if (target.getFields() != null)
				Collections.sort(target.getFields(), new Comparator<UserPermissionFieldDto>() {
					@Override
					public int compare(UserPermissionFieldDto o1, UserPermissionFieldDto o2) {
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
