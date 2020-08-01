package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.DynamicFieldSlotDto;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;

@Component
public class DynamicFieldSlotBasicPopulator extends AbstractPopulator<DynamicFieldSlot, DynamicFieldSlotDto> implements Populator<DynamicFieldSlot, DynamicFieldSlotDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DynamicFieldSlotBasicPopulator.class);

	@Override
	public void populate(DynamicFieldSlot source, DynamicFieldSlotDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
			target.setName(source.getName());
			target.setSort(source.getSort());
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
