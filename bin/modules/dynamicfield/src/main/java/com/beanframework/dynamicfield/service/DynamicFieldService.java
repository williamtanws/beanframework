package com.beanframework.dynamicfield.service;

import com.beanframework.enumuration.domain.Enumeration;
import com.beanframework.internationalization.domain.Language;

public interface DynamicFieldService {

	void removeLanguageRel(Language model) throws Exception;

	void removeEnumerationsRel(Enumeration enumeration) throws Exception;
}
