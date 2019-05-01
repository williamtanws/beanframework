package com.beanframework.language.service;

import java.util.List;

import com.beanframework.common.data.DataTableRequest;

public interface LanguageService {

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;
}
