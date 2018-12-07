package com.beanframework.menu.validator;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.menu.MenuConstants;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.service.MenuService;

@Component
public class DeleteMenuValidator implements Validator {

	@Autowired
	private MenuService menuService;

	@Autowired
	private LocaleMessageService localMessageService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Menu.class == clazz;
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		
		UUID uuid = (UUID) target;

		Menu menu = menuService.findByUuid(uuid);
		if(menu == null) {
			errors.reject(Menu.ID, localMessageService.getMessage(MenuConstants.Locale.UUID_NOT_EXISTS));
		}
	}

}
