package com.beanframework.user.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.beanframework.language.converter.DtoLanguageConverter;
import com.beanframework.user.domain.UserRightLang;

@Component
public class DtoUserRightLangConverter  implements Converter<UserRightLang, UserRightLang>{
	
	@Autowired
	private DtoLanguageConverter dtoLanguageConverter;

	@Override
	public UserRightLang convert(UserRightLang source) {
		return convert(source, new UserRightLang());
	}
	
	public List<UserRightLang> convert(List<UserRightLang> sources) {
		List<UserRightLang> convertedList = new ArrayList<UserRightLang>();
		for (UserRightLang source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}
	
	public UserRightLang convert(UserRightLang source, UserRightLang prototype) {
		prototype.setName(source.getName());
		prototype.setLanguage(dtoLanguageConverter.convert(source.getLanguage()));
		
		return prototype;
	}

}