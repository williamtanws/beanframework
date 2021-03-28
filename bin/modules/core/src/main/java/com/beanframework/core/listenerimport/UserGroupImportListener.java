package com.beanframework.core.listenerimport;

import javax.annotation.PostConstruct;

import com.beanframework.core.ImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class UserGroupImportListener extends ImportListener {
	
	@PostConstruct
	public void importer() {
		setType(ImportListenerConstants.UserGroupImport.TYPE);
		setName(ImportListenerConstants.UserGroupImport.NAME);
		setSort(ImportListenerConstants.UserGroupImport.SORT);
		setDescription(ImportListenerConstants.UserGroupImport.DESCRIPTION);
		setClassCsv(ImportListenerConstants.UserGroupImport.CLASS_CSV);
		setClassEntity(ImportListenerConstants.UserGroupImport.CLASS_ENTITY);
	}
}
