package com.beanframework.menu.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.beanframework.menu.domain.Menu;

@Component
public class SaveMenuValidator implements Validator {

//	@Autowired
//	private MenuService menuService;
//
//	@Autowired
//	private LocaleMessageService localMessageService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Menu.class == clazz;
	}

	@Override
	public void validate(final Object target, final Errors errors) {
//		final Menu admin = (Menu) target;
	}

}
