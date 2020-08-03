package com.beanframework.core.converter.populator.history;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.converter.populator.AbstractPopulator;
import com.beanframework.core.converter.populator.DynamicFieldFullPopulator;
import com.beanframework.core.data.DynamicFieldSlotDto;
import com.beanframework.core.data.DynamicFieldTemplateDto;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;

@Component
public class DynamicFieldTemplateHistoryPopulator extends AbstractPopulator<DynamicFieldTemplate, DynamicFieldTemplateDto> implements Populator<DynamicFieldTemplate, DynamicFieldTemplateDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DynamicFieldTemplateHistoryPopulator.class);

	@Autowired
	private DynamicFieldFullPopulator dynamicFieldFullPopulator;

	@Override
	public void populate(DynamicFieldTemplate source, DynamicFieldTemplateDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
			target.setName(source.getName());
			target.setDynamicFieldSlots(modelService.getDto(source.getDynamicFieldSlots(), DynamicFieldSlotDto.class, new DtoConverterContext(dynamicFieldFullPopulator)));
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
