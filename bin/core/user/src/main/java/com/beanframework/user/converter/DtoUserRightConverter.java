package com.beanframework.user.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.language.domain.Language;
import com.beanframework.language.service.LanguageService;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.domain.UserRightLang;
import com.beanframework.user.service.UserRightService;

@Component
public class DtoUserRightConverter implements DtoConverter<UserRight, UserRight> {

	@Autowired
	private UserRightService userRightService;

	@Autowired
	private LanguageService languageService;
	
	@Autowired
	private DtoUserRightLangConverter dtoUserRightLangConverter;

	@Override
	public UserRight convert(UserRight source) {
		return convert(source, userRightService.create());
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
		List<Language> languages = languageService.findByOrderBySortAsc();
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
