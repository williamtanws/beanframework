package com.beanframework.core.converter.populator.history;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.converter.populator.AbstractPopulator;
import com.beanframework.core.data.DynamicFieldSlotDto;
import com.beanframework.core.data.MenuFieldDto;
import com.beanframework.menu.domain.MenuField;

@Component
public class MenuFieldHistoryPopulator extends AbstractPopulator<MenuField, MenuFieldDto> implements Populator<MenuField, MenuFieldDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(MenuFieldHistoryPopulator.class);

	@Autowired
	private DynamicFieldSlotHistoryPopulator dynamicFieldSlotHistoryPopulator;

	@Override
	public void populate(MenuField source, MenuFieldDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
			target.setValue(source.getValue());
			target.setDynamicFieldSlot(modelService.getDto(source.getDynamicFieldSlot(), DynamicFieldSlotDto.class, new DtoConverterContext(dynamicFieldSlotHistoryPopulator)));

		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
