package com.beanframework.user.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.language.converter.DtoLanguageConverter;
import com.beanframework.user.domain.UserPermissionLang;


public class DtoUserPermissionLangConverter  implements DtoConverter<UserPermissionLang, UserPermissionLang>{
	
	@Autowired
	private DtoLanguageConverter dtoLanguageConverter;

	@Override
	public UserPermissionLang convert(UserPermissionLang source) {
		return convert(source, new UserPermissionLang());
	}
	
	public List<UserPermissionLang> convert(List<UserPermissionLang> sources) {
		List<UserPermissionLang> convertedList = new ArrayList<UserPermissionLang>();
		for (UserPermissionLang source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}
	
	public UserPermissionLang convert(UserPermissionLang source, UserPermissionLang prototype) {
		prototype.setName(source.getName());
		prototype.setLanguage(dtoLanguageConverter.convert(source.getLanguage()));
		
		return prototype;
	}

}
