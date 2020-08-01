package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.MenuDto;
import com.beanframework.menu.domain.Menu;

@Component
public class MenuBasicPopulator extends AbstractPopulator<Menu, MenuDto> implements Populator<Menu, MenuDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(MenuBasicPopulator.class);
	
	@Autowired
	private MenuBasicPopulator menuBasicPopulator;

	@Override
	public void populate(Menu source, MenuDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
			target.setName(source.getName());
			target.setParent(modelService.getDto(source.getParent(), MenuDto.class, new DtoConverterContext(menuBasicPopulator)));
			target.setIcon(source.getIcon());
			target.setPath(source.getPath());
			target.setSort(source.getSort());
			target.setTarget(source.getTarget());
			target.setEnabled(source.getEnabled());
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
