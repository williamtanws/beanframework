package com.beanframework.core.listenerimport;

import javax.annotation.PostConstruct;

import com.beanframework.core.ImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class CustomerImportListener extends ImportListener {
	
	@PostConstruct
	public void importer() {
		setType(ImportListenerConstants.CustomerImport.TYPE);
		setName(ImportListenerConstants.CustomerImport.NAME);
		setSort(ImportListenerConstants.CustomerImport.SORT);
		setDescription(ImportListenerConstants.CustomerImport.DESCRIPTION);
		setClassCsv(ImportListenerConstants.CustomerImport.CLASS_CSV);
		setClassEntity(ImportListenerConstants.CustomerImport.CLASS_ENTITY);
	}
}
