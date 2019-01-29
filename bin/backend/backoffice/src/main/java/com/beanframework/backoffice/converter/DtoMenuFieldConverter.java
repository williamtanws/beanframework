package com.beanframework.backoffice.converter;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.DynamicFieldDto;
import com.beanframework.core.data.MenuFieldDto;
import com.beanframework.menu.domain.MenuField;

public class DtoMenuFieldConverter implements DtoConverter<MenuField, MenuFieldDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoMenuFieldConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public MenuFieldDto convert(MenuField source) throws ConverterException {
		return convert(source, new MenuFieldDto());
	}

	public List<MenuFieldDto> convert(List<MenuField> sources) throws ConverterException {
		List<MenuFieldDto> convertedList = new ArrayList<MenuFieldDto>();
		for (MenuField source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	public MenuFieldDto convert(MenuField source, MenuFieldDto prototype) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setValue(source.getValue());
		try {
			if (Hibernate.isInitialized(source.getDynamicField()))
				prototype.setDynamicField(modelService.getDto(source.getDynamicField(), DynamicFieldDto.class));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
