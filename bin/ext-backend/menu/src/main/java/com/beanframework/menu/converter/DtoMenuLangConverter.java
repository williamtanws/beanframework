package com.beanframework.menu.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.beanframework.language.converter.DtoLanguageConverter;
import com.beanframework.menu.domain.MenuLang;

@Component
public class DtoMenuLangConverter  implements Converter<MenuLang, MenuLang>{
	
	@Autowired
	private DtoLanguageConverter dtoLanguageConverter;

	@Override
	public MenuLang convert(MenuLang source) {
		return convert(source, new MenuLang());
	}
	
	public List<MenuLang> convert(List<MenuLang> sources) {
		List<MenuLang> convertedList = new ArrayList<MenuLang>();
		for (MenuLang source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}
	
	public MenuLang convert(MenuLang source, MenuLang prototype) {
		prototype.setName(source.getName());
		prototype.setLanguage(dtoLanguageConverter.convert(source.getLanguage()));
		
		return prototype;
	}

}
