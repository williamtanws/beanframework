package com.beanframework.language.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort.Direction;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.language.domain.Language;

public interface LanguageService {

	public Language create() throws Exception;

	Language saveEntity(Language model) throws BusinessException;

	public void deleteById(String id) throws BusinessException;

	List<Language> findDtoBySorts(Map<String, Direction> sorts) throws Exception;
}
