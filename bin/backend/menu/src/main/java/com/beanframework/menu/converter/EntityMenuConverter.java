package com.beanframework.menu.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.language.domain.Language;
import com.beanframework.language.service.LanguageService;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.domain.MenuLang;
import com.beanframework.menu.service.MenuService;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.service.UserGroupService;

@Component
public class EntityMenuConverter implements EntityConverter<Menu, Menu> {

	@Autowired
	private MenuService menuService;

	@Autowired
	private LanguageService languageService;

	@Autowired
	private UserGroupService userGroupService;

	@Override
	public Menu convert(Menu source) {

		Optional<Menu> prototype = Optional.of(menuService.create());
		if (source.getUuid() != null) {
			Optional<Menu> exists = menuService.findEntityByUuid(source.getUuid());
			if (exists.isPresent()) {
				prototype = exists;
			}
		} else if (StringUtils.isNotEmpty(source.getId())) {
			Optional<Menu> exists = menuService.findEntityById(source.getId());
			if (exists.isPresent()) {
				prototype = exists;
			}
		}

		return convert(source, prototype.get());
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
				Optional<Menu> parent = menuService.findEntityByUuid(source.getParent().getUuid());
				prototype.setParent(parent.isPresent() ? parent.get() : null);
			} else if (StringUtils.isNotEmpty(source.getParent().getId())) {
				Optional<Menu> parent = menuService.findEntityById(source.getParent().getId());
				prototype.setParent(parent.isPresent() ? parent.get() : null);
			}
		} else {
			prototype.setParent(null);
		}

		prototype.getMenuLangs().clear();
		for (MenuLang menuLang : source.getMenuLangs()) {
			if (menuLang.getLanguage().getUuid() != null) {
				Optional<Language> language = languageService.findEntityByUuid(menuLang.getLanguage().getUuid());
				if (language.isPresent()) {
					menuLang.setLanguage(language.get());
					menuLang.setMenu(prototype);
					prototype.getMenuLangs().add(menuLang);
				}
			} else if (StringUtils.isNotEmpty(menuLang.getLanguage().getId())) {
				Optional<Language> language = languageService.findEntityById(menuLang.getLanguage().getId());
				if (language.isPresent()) {
					menuLang.setLanguage(language.get());
					menuLang.setMenu(prototype);
					prototype.getMenuLangs().add(menuLang);
				}
			}
		}

		Hibernate.initialize(prototype.getUserGroups());
		prototype.getUserGroups().clear();
		for (UserGroup userGroup : source.getUserGroups()) {
			if(userGroup.getUuid() != null) {
				Optional<UserGroup> existingUserGroup = userGroupService.findEntityByUuid(userGroup.getUuid());
				if (existingUserGroup.isPresent()) {
					prototype.getUserGroups().add(existingUserGroup.get());
				}
			}
			else if(StringUtils.isNotEmpty(userGroup.getId())) {
				Optional<UserGroup> existingUserGroup = userGroupService.findEntityById(userGroup.getId());
				if (existingUserGroup.isPresent()) {
					prototype.getUserGroups().add(existingUserGroup.get());
				}
			}
		}

		return prototype;
	}

}
