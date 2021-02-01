package com.beanframework.core.converter.populator;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.DynamicFieldTemplateDto;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;

public class DynamicFieldTemplatePopulator extends AbstractPopulator<DynamicFieldTemplate, DynamicFieldTemplateDto> implements Populator<DynamicFieldTemplate, DynamicFieldTemplateDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DynamicFieldTemplatePopulator.class);

	@Override
	public void populate(DynamicFieldTemplate source, DynamicFieldTemplateDto target) throws PopulatorException {
		try {
			populateGeneric(source, target);
			target.setName(source.getName());
			for (UUID uuid : source.getDynamicFieldSlots()) {
				target.getDynamicFieldSlots().add(populateDynamicFieldSlot(uuid));
			}
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
