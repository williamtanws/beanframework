package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.DynamicFieldDto;
import com.beanframework.dynamicfield.domain.DynamicField;

@Component
public class DynamicFieldBasicPopulator extends AbstractPopulator<DynamicField, DynamicFieldDto> implements Populator<DynamicField, DynamicFieldDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DynamicFieldBasicPopulator.class);

	@Override
	public void populate(DynamicField source, DynamicFieldDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
			target.setName(source.getName());
			target.setType(source.getType());
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
