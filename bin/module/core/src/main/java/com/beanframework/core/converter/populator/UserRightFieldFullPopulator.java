package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.DynamicFieldSlotDto;
import com.beanframework.core.data.UserRightFieldDto;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;
import com.beanframework.user.domain.UserRightField;

@Component
public class UserRightFieldFullPopulator extends AbstractPopulator<UserRightField, UserRightFieldDto> implements Populator<UserRightField, UserRightFieldDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(UserRightFieldFullPopulator.class);

	@Autowired
	private DynamicFieldSlotFullPopulator dynamicFieldSlotFullPopulator;

	@Override
	public void populate(UserRightField source, UserRightFieldDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
			target.setValue(source.getValue());
			if (source.getDynamicFieldSlot() != null) {
				DynamicFieldSlot entity = modelService.findOneByUuid(source.getDynamicFieldSlot(), DynamicFieldSlot.class);
				target.setDynamicFieldSlot(modelService.getDto(entity, DynamicFieldSlotDto.class, new DtoConverterContext(dynamicFieldSlotFullPopulator)));
			}		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}

	}

}
