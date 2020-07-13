package com.beanframework.core.listener;

import javax.annotation.PostConstruct;

import com.beanframework.core.ImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class ConfigurationImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setType(ImportListenerConstants.ConfigurationImport.TYPE);
		setName(ImportListenerConstants.ConfigurationImport.NAME);
		setSort(ImportListenerConstants.ConfigurationImport.SORT);
		setDescription(ImportListenerConstants.ConfigurationImport.DESCRIPTION);
		setClassCsv(ImportListenerConstants.ConfigurationImport.CLASS_CSV);
		setClassEntity(ImportListenerConstants.ConfigurationImport.CLASS_ENTITY);
	}
}
