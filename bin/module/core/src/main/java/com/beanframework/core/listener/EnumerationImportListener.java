package com.beanframework.core.listener;

import javax.annotation.PostConstruct;

import com.beanframework.core.ImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class EnumerationImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setType(ImportListenerConstants.EnumerationImport.TYPE);
		setName(ImportListenerConstants.EnumerationImport.NAME);
		setSort(ImportListenerConstants.EnumerationImport.SORT);
		setDescription(ImportListenerConstants.EnumerationImport.DESCRIPTION);
		setClassCsv(ImportListenerConstants.EnumerationImport.CLASS_CSV);
		setClassEntity(ImportListenerConstants.EnumerationImport.CLASS_ENTITY);
	}
}
