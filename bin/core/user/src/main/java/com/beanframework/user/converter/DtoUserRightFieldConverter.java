package com.beanframework.user.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.dynamicfield.converter.DtoDynamicFieldConverter;
import com.beanframework.language.converter.DtoLanguageConverter;
import com.beanframework.user.domain.UserRightField;


public class DtoUserRightFieldConverter  implements DtoConverter<UserRightField, UserRightField>{
	
	@Autowired
	private DtoLanguageConverter dtoLanguageConverter;
	
	@Autowired
	private DtoDynamicFieldConverter dtoDynamicFieldConverter;

	@Override
	public UserRightField convert(UserRightField source) {
		return convert(source, new UserRightField());
	}
	
	public List<UserRightField> convert(List<UserRightField> sources) {
		List<UserRightField> convertedList = new ArrayList<UserRightField>();
		for (UserRightField source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}
	
	public UserRightField convert(UserRightField source, UserRightField prototype) {
		prototype.setLabel(source.getLabel());
		prototype.setValue(source.getValue());
		prototype.setLanguage(dtoLanguageConverter.convert(source.getLanguage()));
		prototype.setDynamicField(dtoDynamicFieldConverter.convert(source.getDynamicField()));
		
		return prototype;
	}

}
