package com.beanframework.menu.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.service.ModelService;
import com.beanframework.language.domain.Language;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.domain.MenuLang;
import com.beanframework.user.converter.DtoUserGroupConverter;

public class DtoMenuConverter implements DtoConverter<Menu, Menu> {
	
	@Autowired
	private ModelService modelService;

	@Autowired
	private DtoMenuLangConverter dtoMenuLangConverter;

	@Autowired
	private DtoUserGroupConverter dtoUserGroupConverter;

	@Override
	public Menu convert(Menu source) {
		return convert(source, modelService.create(Menu.class));
	}

	public List<Menu> convert(List<Menu> sources) {
		List<Menu> convertedList = new ArrayList<Menu>();
		for (Menu source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private Menu convert(Menu source, Menu prototype) {
		return convert(source, prototype, true);
	}

	private Menu convert(Menu source, Menu prototype, boolean initializeParent) {
		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setSort(source.getSort());
		prototype.setIcon(source.getIcon());
		prototype.setPath(source.getPath());
		prototype.setTarget(source.getTarget());
		prototype.setEnabled(source.isEnabled());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		// Process Menu Lang
		prototype.setMenuLangs(dtoMenuLangConverter.convert(source.getMenuLangs()));
		
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(Language.SORT, Sort.Direction.ASC);
		
		List<Language> languages = modelService.findBySorts(sorts, Language.class);
		
		for (Language language : languages) {
			boolean addNewLanguage = true;
			for (MenuLang menuLang : source.getMenuLangs()) {
				if (menuLang.getLanguage().getUuid().equals(language.getUuid())) {
					addNewLanguage = false;
				}
			}

			if (addNewLanguage) {
				MenuLang menuLang = new MenuLang();
				menuLang.setLanguage(language);
				menuLang.setMenu(prototype);

				prototype.getMenuLangs().add(menuLang);
			}
		}

		Hibernate.initialize(source.getUserGroups());
		prototype.setUserGroups(dtoUserGroupConverter.convert(source.getUserGroups()));

		if (source.getChilds().isEmpty() == false) {
			prototype.setChilds(convert(source.getChilds()));
		}

		return prototype;
	}
}
