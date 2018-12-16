package com.beanframework.user.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.service.ModelService;
import com.beanframework.language.domain.Language;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.domain.UserRightLang;


public class DtoUserRightConverter implements DtoConverter<UserRight, UserRight> {
	
	@Autowired
	private ModelService modelService;
	
	@Autowired
	private DtoUserRightLangConverter dtoUserRightLangConverter;

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
		prototype.setSort(source.getSort());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		// Process User Right Lang
		prototype.setUserRightLangs(dtoUserRightLangConverter.convert(source.getUserRightLangs()));
		
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(Language.SORT, Sort.Direction.ASC);
		
		List<Language> languages = modelService.findBySorts(sorts, Language.class);
		
		for (Language language : languages) {
			boolean addNewLanguage = true;
			for (UserRightLang userRightLang : source.getUserRightLangs()) {
				if (userRightLang.getLanguage().getUuid().equals(language.getUuid())) {
					addNewLanguage = false;
				}
			}

			if (addNewLanguage) {
				UserRightLang userRightLang = new UserRightLang();
				userRightLang.setLanguage(language);
				userRightLang.setUserRight(prototype);

				prototype.getUserRightLangs().add(userRightLang);
			}
		}
		
		return prototype;
	}

}
