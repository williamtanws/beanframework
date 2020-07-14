package com.beanframework.core.listenerimport;

import javax.annotation.PostConstruct;

import com.beanframework.core.ImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class AdminImportListener extends ImportListener {
	
	@PostConstruct
	public void importer() {
		setType(ImportListenerConstants.AdminImport.TYPE);
		setName(ImportListenerConstants.AdminImport.NAME);
		setSort(ImportListenerConstants.AdminImport.SORT);
		setDescription(ImportListenerConstants.AdminImport.DESCRIPTION);
		setClassCsv(ImportListenerConstants.AdminImport.CLASS_CSV);
		setClassEntity(ImportListenerConstants.AdminImport.CLASS_ENTITY);
	}
}
