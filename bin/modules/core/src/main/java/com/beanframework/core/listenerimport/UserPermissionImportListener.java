package com.beanframework.core.listenerimport;

import javax.annotation.PostConstruct;

import com.beanframework.core.ImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class UserPermissionImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setType(ImportListenerConstants.UserPermissionImport.TYPE);
		setName(ImportListenerConstants.UserPermissionImport.NAME);
		setSort(ImportListenerConstants.UserPermissionImport.SORT);
		setDescription(ImportListenerConstants.UserPermissionImport.DESCRIPTION);
		setClassCsv(ImportListenerConstants.UserPermissionImport.CLASS_CSV);
		setClassEntity(ImportListenerConstants.UserPermissionImport.CLASS_ENTITY);
	}
}
