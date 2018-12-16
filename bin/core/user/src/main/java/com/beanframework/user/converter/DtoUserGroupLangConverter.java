package com.beanframework.user.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.language.converter.DtoLanguageConverter;
import com.beanframework.user.domain.UserGroupField;


public class DtoUserGroupLangConverter  implements DtoConverter<UserGroupField, UserGroupField>{
	
	@Autowired
	private DtoLanguageConverter dtoLanguageConverter;

	@Override
	public UserGroupField convert(UserGroupField source) {
		return convert(source, new UserGroupField());
	}
	
	public List<UserGroupField> convert(List<UserGroupField> sources) {
		List<UserGroupField> convertedList = new ArrayList<UserGroupField>();
		for (UserGroupField source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}
	
	public UserGroupField convert(UserGroupField source, UserGroupField prototype) {
		prototype.setLanguage(dtoLanguageConverter.convert(source.getLanguage()));
		
		return prototype;
	}

}
