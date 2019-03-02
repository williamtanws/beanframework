package com.beanframework.core.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.DynamicFieldDto;
import com.beanframework.core.data.MenuFieldDto;
import com.beanframework.menu.domain.MenuField;

public class DtoMenuFieldConverter extends AbstractDtoConverter<MenuField, MenuFieldDto> implements DtoConverter<MenuField, MenuFieldDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoMenuFieldConverter.class);

	@Override
	public MenuFieldDto convert(MenuField source, DtoConverterContext context) throws ConverterException {
		return convert(source, new MenuFieldDto(), context);
	}

	public List<MenuFieldDto> convert(List<MenuField> sources, DtoConverterContext context) throws ConverterException {
		List<MenuFieldDto> convertedList = new ArrayList<MenuFieldDto>();
		for (MenuField source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

	public MenuFieldDto convert(MenuField source, MenuFieldDto prototype, DtoConverterContext context) throws ConverterException {
		try {

			convertGeneric(source, prototype, context);

			prototype.setValue(source.getValue());
			prototype.setSort(source.getSort());
			prototype.setDynamicField(modelService.getDto(source.getDynamicField(), DynamicFieldDto.class));

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
