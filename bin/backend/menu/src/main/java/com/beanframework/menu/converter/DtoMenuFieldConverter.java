package com.beanframework.menu.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.dynamicfield.converter.DtoDynamicFieldConverter;
import com.beanframework.language.converter.DtoLanguageConverter;
import com.beanframework.menu.domain.MenuField;

public class DtoMenuFieldConverter implements DtoConverter<MenuField, MenuField> {

	@Autowired
	private DtoLanguageConverter dtoLanguageConverter;

	@Autowired
	private DtoDynamicFieldConverter dtoDynamicFieldConverter;

	@Override
	public MenuField convert(MenuField source) {
		return convert(source, new MenuField());
	}

	public List<MenuField> convert(List<MenuField> sources) {
		List<MenuField> convertedList = new ArrayList<MenuField>();
		for (MenuField source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	public MenuField convert(MenuField source, MenuField prototype) {
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setId(source.getId());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());
		prototype.setUuid(source.getUuid());

		prototype.setLabel(source.getLabel());
		prototype.setValue(source.getValue());
		prototype.setLanguage(dtoLanguageConverter.convert(source.getLanguage()));
		prototype.setDynamicField(dtoDynamicFieldConverter.convert(source.getDynamicField()));

		return prototype;
	}

}
