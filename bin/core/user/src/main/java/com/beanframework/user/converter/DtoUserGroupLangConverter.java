package com.beanframework.user.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.language.converter.DtoLanguageConverter;
import com.beanframework.user.domain.UserGroupLang;

@Component
public class DtoUserGroupLangConverter  implements DtoConverter<UserGroupLang, UserGroupLang>{
	
	@Autowired
	private DtoLanguageConverter dtoLanguageConverter;

	@Override
	public UserGroupLang convert(UserGroupLang source) {
		return convert(source, new UserGroupLang());
	}
	
	public List<UserGroupLang> convert(List<UserGroupLang> sources) {
		List<UserGroupLang> convertedList = new ArrayList<UserGroupLang>();
		for (UserGroupLang source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}
	
	public UserGroupLang convert(UserGroupLang source, UserGroupLang prototype) {
		prototype.setLanguage(dtoLanguageConverter.convert(source.getLanguage()));
		
		return prototype;
	}

}
