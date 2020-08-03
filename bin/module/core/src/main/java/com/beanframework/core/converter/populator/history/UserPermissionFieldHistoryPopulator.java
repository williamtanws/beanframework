package com.beanframework.core.converter.populator.history;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.converter.populator.AbstractPopulator;
import com.beanframework.core.converter.populator.DynamicFieldSlotFullPopulator;
import com.beanframework.core.data.DynamicFieldSlotDto;
import com.beanframework.core.data.UserPermissionFieldDto;
import com.beanframework.user.domain.UserPermissionField;

@Component
public class UserPermissionFieldHistoryPopulator extends AbstractPopulator<UserPermissionField, UserPermissionFieldDto> implements Populator<UserPermissionField, UserPermissionFieldDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(UserPermissionFieldHistoryPopulator.class);

	@Autowired
	private DynamicFieldSlotFullPopulator dynamicFieldSlotFullPopulator;

	@Override
	public void populate(UserPermissionField source, UserPermissionFieldDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
			target.setValue(source.getValue());
			target.setDynamicFieldSlot(modelService.getDto(source.getDynamicFieldSlot(), DynamicFieldSlotDto.class, new DtoConverterContext(dynamicFieldSlotFullPopulator)));
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}

	}

}
