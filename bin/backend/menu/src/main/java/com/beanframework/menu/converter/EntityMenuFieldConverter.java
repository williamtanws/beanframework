package com.beanframework.menu.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicField;
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
			

			if (StringUtils.isNotBlank(source.getId()) && StringUtils.equals(source.getId(), prototype.getId()) == false)
				prototype.setId(StringUtils.strip(source.getId()));

			// Dynamic Field
			if (source.getDynamicField() == null) {
				if (prototype.getDynamicField() != null)
					prototype.setDynamicField(null);
			} else {
				boolean add = true;
				if (prototype.getDynamicField() != null) {
					if (source.getDynamicField().getUuid() == prototype.getDynamicField().getUuid()) {
						add = false;
					}
				}

				if (add) {
					DynamicField dynamicField = modelService.findOneEntityByUuid(source.getDynamicField().getUuid(), DynamicField.class);
					prototype.setDynamicField(dynamicField);
				}
			}

			if (StringUtils.equals(prototype.getValue(), source.getValue()) == false)
				prototype.setValue(StringUtils.strip(source.getValue()));
			
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);

		}

		return prototype;
	}

}
