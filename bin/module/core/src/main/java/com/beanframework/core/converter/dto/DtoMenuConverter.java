package com.beanframework.core.converter.dto;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.MenuDto;
import com.beanframework.menu.domain.Menu;

public class DtoMenuConverter extends AbstractDtoConverter<Menu, MenuDto> implements DtoConverter<Menu, MenuDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoMenuConverter.class);

	@Override
	public MenuDto convert(Menu source, DtoConverterContext context) throws ConverterException {
		try {
			MenuDto target = new MenuDto();
			populate(source, target, context);

			return target;
		} catch (PopulatorException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public List<MenuDto> convert(List<Menu> sources, DtoConverterContext context) throws ConverterException {
		List<MenuDto> convertedList = new ArrayList<MenuDto>();
		for (Menu source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}
}
