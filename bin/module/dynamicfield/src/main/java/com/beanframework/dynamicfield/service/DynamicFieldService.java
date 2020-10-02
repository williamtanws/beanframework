package com.beanframework.dynamicfield.service;

import java.util.List;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.enumuration.domain.Enumeration;
import com.beanframework.internationalization.domain.Language;

public interface DynamicFieldService {

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;

	void removeLanguageRel(Language model) throws Exception;

	void removeEnumerationsRel(Enumeration enumeration) throws Exception;
}
