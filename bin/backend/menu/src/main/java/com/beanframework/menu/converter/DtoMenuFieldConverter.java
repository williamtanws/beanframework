package com.beanframework.menu.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.menu.domain.MenuField;

public class DtoMenuFieldConverter implements DtoConverter<MenuField, MenuField> {
	
	protected static Logger LOGGER = LoggerFactory.getLogger(DtoMenuFieldConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public MenuField convert(MenuField source) throws ConverterException {
		return convert(source, new MenuField());
	}

	public List<MenuField> convert(List<MenuField> sources) throws ConverterException {
		List<MenuField> convertedList = new ArrayList<MenuField>();
		for (MenuField source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	public MenuField convert(MenuField source, MenuField prototype) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setValue(source.getValue());
		try {
			prototype.setDynamicField(modelService.getDto(source.getDynamicField(), DynamicField.class));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
