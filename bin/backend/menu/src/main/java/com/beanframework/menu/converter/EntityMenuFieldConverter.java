package com.beanframework.menu.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.converter.EntityDynamicFieldConverter;
import com.beanframework.menu.domain.MenuField;

public class EntityMenuFieldConverter implements EntityConverter<MenuField, MenuField> {

	@Autowired
	private ModelService modelService;
	
	@Autowired
	private EntityDynamicFieldConverter entityDynamicFieldConverter;

	@Override
	public MenuField convert(MenuField source) {

		MenuField prototype = modelService.create(MenuField.class);
		if (source.getUuid() != null) {

			MenuField exists = modelService.findOneEntityByUuid(source.getUuid(), MenuField.class);

			if (exists != null) {
				prototype = exists;
			}
		}

		return convert(source, prototype);
	}

	public List<MenuField> convert(List<MenuField> sources) {
		List<MenuField> convertedList = new ArrayList<MenuField>();
		for (MenuField source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private MenuField convert(MenuField source, MenuField prototype) {

		prototype.setId(source.getId());
		prototype.setLastModifiedDate(new Date());
		
		prototype.setDynamicField(entityDynamicFieldConverter.convert(source.getDynamicField()));

		return prototype;
	}

}
