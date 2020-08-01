package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.DynamicFieldTemplateDto;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;

@Component
public class DynamicFieldTemplateBasicPopulator extends AbstractPopulator<DynamicFieldTemplate, DynamicFieldTemplateDto> implements Populator<DynamicFieldTemplate, DynamicFieldTemplateDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DynamicFieldTemplateBasicPopulator.class);

	@Override
	public void populate(DynamicFieldTemplate source, DynamicFieldTemplateDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
			target.setName(source.getName());
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
