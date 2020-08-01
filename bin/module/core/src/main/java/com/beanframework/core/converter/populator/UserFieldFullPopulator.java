package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.DynamicFieldSlotDto;
import com.beanframework.core.data.UserFieldDto;
import com.beanframework.user.domain.UserField;

@Component
public class UserFieldFullPopulator extends AbstractPopulator<UserField, UserFieldDto> implements Populator<UserField, UserFieldDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(UserFieldFullPopulator.class);

	@Autowired
	private DynamicFieldSlotFullPopulator dynamicFieldSlotFullPopulator;

	@Override
	public void populate(UserField source, UserFieldDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
			target.setValue(source.getValue());
			target.setDynamicFieldSlot(modelService.getDto(source.getDynamicFieldSlot(), DynamicFieldSlotDto.class, new DtoConverterContext(dynamicFieldSlotFullPopulator)));

		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
