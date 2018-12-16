package com.beanframework.user.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.UserRight;


public class DtoUserRightConverter implements DtoConverter<UserRight, UserRight> {
	
	@Autowired
	private ModelService modelService;
	
	@Autowired
	private DtoUserRightFieldConverter dtoUserRightFieldConverter;

	@Override
	public UserRight convert(UserRight source) {
		return convert(source, modelService.create(UserRight.class));
	}

	public List<UserRight> convert(List<UserRight> sources) {
		List<UserRight> convertedList = new ArrayList<UserRight>();
		for (UserRight source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private UserRight convert(UserRight source, UserRight prototype) {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());
		
		prototype.setSort(source.getSort());
		prototype.setUserRightFields(dtoUserRightFieldConverter.convert(source.getUserRightFields()));
		
//		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
//		sorts.put(Language.SORT, Sort.Direction.ASC);
//		
//		List<Language> languages = modelService.findBySorts(sorts, Language.class);
//		
//		for (Language language : languages) {
//			boolean addNewLanguage = true;
//			for (UserRightField userRightField : source.getUserRightFields()) {
//				if (userRightField.getLanguage().getUuid().equals(language.getUuid())) {
//					addNewLanguage = false;
//				}
//			}
//
//			if (addNewLanguage) {
//				UserRightField userRightField = new UserRightField();
//				userRightField.setLanguage(language);
//				userRightField.setUserRight(prototype);
//
//				prototype.getUserRightFields().add(userRightField);
//			}
//		}
		
		return prototype;
	}

}
