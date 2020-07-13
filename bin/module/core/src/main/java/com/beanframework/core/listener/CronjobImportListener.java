package com.beanframework.core.listener;

import javax.annotation.PostConstruct;

import com.beanframework.core.ImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class CronjobImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setType(ImportListenerConstants.CronjobImport.TYPE);
		setName(ImportListenerConstants.CronjobImport.NAME);
		setSort(ImportListenerConstants.CronjobImport.SORT);
		setDescription(ImportListenerConstants.CronjobImport.DESCRIPTION);
		setClassCsv(ImportListenerConstants.CronjobImport.CLASS_CSV);
		setClassEntity(ImportListenerConstants.CronjobImport.CLASS_ENTITY);
	}
}
