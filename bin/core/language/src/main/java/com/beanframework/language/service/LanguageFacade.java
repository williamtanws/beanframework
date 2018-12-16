package com.beanframework.language.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.beanframework.language.domain.Language;

public interface LanguageFacade {

	Page<Language> page(Language language, int page, int size, Direction direction, String... properties);
}
