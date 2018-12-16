package com.beanframework.menu.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.service.ModelService;
import com.beanframework.language.domain.Language;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.domain.MenuLang;
import com.beanframework.user.domain.UserGroup;

public class EntityMenuConverter implements EntityConverter<Menu, Menu> {
	
	@Autowired
	private ModelService modelService;

	@Override
	public Menu convert(Menu source) {

		Menu prototype = modelService.create(Menu.class);
		if (source.getUuid() != null) {
			
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Menu.UUID, source.getUuid());
			
			Menu exists = modelService.findOneEntityByProperties(properties, Menu.class);
			
			if (exists != null) {
				prototype = exists;
			}
		}

		return convert(source, prototype);
	}

	public List<Menu> convert(List<Menu> sources) {
		List<Menu> convertedList = new ArrayList<Menu>();
		for (Menu source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private Menu convert(Menu source, Menu prototype) {

		prototype.setId(source.getId());
		prototype.setSort(source.getSort());
		prototype.setIcon(source.getIcon());
		prototype.setPath(source.getPath());
		prototype.setTarget(source.getTarget());
		prototype.setEnabled(source.isEnabled());
		prototype.setLastModifiedDate(new Date());

		if (source.getParent() != null) {
			if (source.getParent().getUuid() != null) {
				
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Menu.UUID, source.getParent().getUuid());
				
				Menu parent = modelService.findOneEntityByProperties(properties, Menu.class);
				
				prototype.setParent(parent == null ? null : parent);
			} else if (StringUtils.isNotEmpty(source.getParent().getId())) {
				
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Menu.ID, source.getParent().getId());
				
				Menu parent = modelService.findOneEntityByProperties(properties, Menu.class);
				
				prototype.setParent(parent == null ? null : parent);
			}
		} else {
			prototype.setParent(null);
		}

		prototype.getMenuLangs().clear();
		for (MenuLang menuLang : source.getMenuLangs()) {
			if (menuLang.getLanguage().getUuid() != null) {
				
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Language.UUID, menuLang.getLanguage().getUuid());
				
				Language language = modelService.findOneEntityByProperties(properties, Language.class);
				
				if (language != null) {
					menuLang.setLanguage(language);
					menuLang.setMenu(prototype);
					prototype.getMenuLangs().add(menuLang);
				}
			} else if (StringUtils.isNotEmpty(menuLang.getLanguage().getId())) {
				
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Language.ID, menuLang.getLanguage().getId());
				
				Language language = modelService.findOneEntityByProperties(properties, Language.class);
				
				if (language != null) {
					menuLang.setLanguage(language);
					menuLang.setMenu(prototype);
					prototype.getMenuLangs().add(menuLang);
				}
			}
		}

		Hibernate.initialize(prototype.getUserGroups());
		prototype.getUserGroups().clear();
		for (UserGroup userGroup : source.getUserGroups()) {
			if(userGroup.getUuid() != null) {
				
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(UserGroup.UUID, source.getUuid());
				UserGroup existingUserGroup = modelService.findOneEntityByProperties(properties, UserGroup.class);
				
				if (existingUserGroup != null) {
					prototype.getUserGroups().add(existingUserGroup);
				}
			}
			else if(StringUtils.isNotEmpty(userGroup.getId())) {
				
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(UserGroup.ID, source.getId());
				UserGroup existingUserGroup = modelService.findOneEntityByProperties(properties, UserGroup.class);
				
				if (existingUserGroup != null) {
					prototype.getUserGroups().add(existingUserGroup);
				}
			}
		}

		return prototype;
	}

}
