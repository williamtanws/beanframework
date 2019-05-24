package com.beanframework.console.listener;

import javax.annotation.PostConstruct;

import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class UserPermissionImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setType(ConsoleImportListenerConstants.UserPermissionImport.TYPE);
		setName(ConsoleImportListenerConstants.UserPermissionImport.NAME);
		setSort(ConsoleImportListenerConstants.UserPermissionImport.SORT);
		setDescription(ConsoleImportListenerConstants.UserPermissionImport.DESCRIPTION);
		setClassCsv(ConsoleImportListenerConstants.UserPermissionImport.CLASS_CSV);
		setClassEntity(ConsoleImportListenerConstants.UserPermissionImport.CLASS_ENTITY);
	}
}
