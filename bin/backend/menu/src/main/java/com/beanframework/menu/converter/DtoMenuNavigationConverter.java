package com.beanframework.menu.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.domain.MenuNavigation;

@Component
public class DtoMenuNavigationConverter implements DtoConverter<Menu, MenuNavigation> {
	
	private static Logger LOGGER = LoggerFactory.getLogger(DtoMenuNavigationConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public MenuNavigation convert(Menu source) throws ConverterException {
		return convert(source, new MenuNavigation());
	}

	public List<MenuNavigation> convert(List<Menu> sources) throws ConverterException {
		List<MenuNavigation> convertedList = new ArrayList<MenuNavigation>();
		for (Menu source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private MenuNavigation convert(Menu source, MenuNavigation prototype) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setIcon(source.getIcon());
		prototype.setPath(source.getPath());
		prototype.setSort(source.getSort());
		prototype.setTarget(source.getTarget());
		prototype.setEnabled(source.getEnabled());
		try {
			prototype.setUserGroups(modelService.getDto(source.getUserGroups()));
			prototype.setMenuFields(modelService.getDto(source.getMenuFields()));
			prototype.setNavigationChilds(modelService.getDto(source.getChilds()));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}
}
