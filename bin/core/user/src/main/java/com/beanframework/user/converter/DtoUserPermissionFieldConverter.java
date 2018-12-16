package com.beanframework.user.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.language.converter.DtoLanguageConverter;
import com.beanframework.user.domain.UserPermissionField;


public class DtoUserPermissionFieldConverter  implements DtoConverter<UserPermissionField, UserPermissionField>{
	
	@Autowired
	private DtoLanguageConverter dtoLanguageConverter;

	@Override
	public UserPermissionField convert(UserPermissionField source) {
		return convert(source, new UserPermissionField());
	}
	
	public List<UserPermissionField> convert(List<UserPermissionField> sources) {
		List<UserPermissionField> convertedList = new ArrayList<UserPermissionField>();
		for (UserPermissionField source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}
	
	public UserPermissionField convert(UserPermissionField source, UserPermissionField prototype) {
		prototype.setLabel(source.getLabel());
		prototype.setValue(source.getValue());
		prototype.setLanguage(dtoLanguageConverter.convert(source.getLanguage()));
		
		return prototype;
	}

}
