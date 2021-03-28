package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.MenuDto;
import com.beanframework.menu.domain.Menu;

public class DtoMenuConverter extends AbstractDtoConverter<Menu, MenuDto> implements DtoConverter<Menu, MenuDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoMenuConverter.class);

	@Override
	public MenuDto convert(Menu source) throws ConverterException {
		return super.convert(source, new MenuDto());
	}
}
