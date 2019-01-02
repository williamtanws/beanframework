package com.beanframework.menu.converter;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.domain.MenuField;

public class DtoMenuConverter implements DtoConverter<Menu, Menu> {
	
	protected static Logger LOGGER = LoggerFactory.getLogger(DtoMenuConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public Menu convert(Menu source) throws ConverterException {
		return convert(source, new Menu());
	}

	public List<Menu> convert(List<Menu> sources) throws ConverterException {
		List<Menu> convertedList = new ArrayList<Menu>();
		for (Menu source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private Menu convert(Menu source, Menu prototype) throws ConverterException {
		return convert(source, prototype, true);
	}

	private Menu convert(Menu source, Menu prototype, boolean initializeParent) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setParent(source.getParent());
		prototype.setIcon(source.getIcon());
		prototype.setPath(source.getPath());
		prototype.setSort(source.getSort());
		prototype.setTarget(source.getTarget());
		prototype.setEnabled(source.getEnabled());
		try {
			Hibernate.initialize(source.getChilds());
			Hibernate.initialize(source.getUserGroups());
			
			prototype.setChilds(modelService.getDto(source.getChilds(), Menu.class));
			prototype.setUserGroups(source.getUserGroups());
			prototype.setFields(modelService.getDto(source.getFields(), MenuField.class));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}
}
