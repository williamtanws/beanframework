package com.beanframework.menu.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.language.domain.Language;
import com.beanframework.menu.domain.MenuField;

public class EntityMenuFieldConverter implements EntityConverter<MenuField, MenuField> {

	@Autowired
	private ModelService modelService;

	public List<MenuField> convert(List<MenuField> sources) throws ConverterException {
		List<MenuField> convertedList = new ArrayList<MenuField>();
		for (MenuField source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	@Override
	public MenuField convert(MenuField source) throws ConverterException {

		MenuField prototype;
		try {

			if (source.getUuid() != null) {

				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(MenuField.UUID, source.getUuid());

				MenuField exists = modelService.findOneEntityByProperties(properties, MenuField.class);

				if (exists != null) {
					prototype = exists;
				} else {
					prototype = modelService.create(MenuField.class);
				}
			} else {
				prototype = modelService.create(MenuField.class);
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), this);
		}

		return convert(source, prototype);
	}

	private MenuField convert(MenuField source, MenuField prototype) throws ConverterException {

		try {
			if (source.getId() != null)
				prototype.setId(source.getId());
			prototype.setLastModifiedDate(new Date());

			if (source.getLanguage() == null) {
				prototype.setLanguage(null);
			} else {
				Language language = modelService.findOneEntityByUuid(source.getLanguage().getUuid(), Language.class);
				prototype.setLanguage(language);
			}
			if (source.getDynamicField() == null) {
				prototype.setDynamicField(null);
			} else {
				DynamicField dynamicField = modelService.findOneEntityByUuid(source.getDynamicField().getUuid(),
						DynamicField.class);
				prototype.setDynamicField(dynamicField);
			}
			prototype.setLabel(source.getLabel());
			prototype.setValue(source.getValue());
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);

		}

		return prototype;
	}

}
