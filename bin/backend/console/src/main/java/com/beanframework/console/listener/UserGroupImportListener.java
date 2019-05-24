package com.beanframework.console.listener;

import javax.annotation.PostConstruct;

import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class UserGroupImportListener extends ImportListener {
	
	@PostConstruct
	public void importer() {
		setType(ConsoleImportListenerConstants.UserGroupImport.TYPE);
		setName(ConsoleImportListenerConstants.UserGroupImport.NAME);
		setSort(ConsoleImportListenerConstants.UserGroupImport.SORT);
		setDescription(ConsoleImportListenerConstants.UserGroupImport.DESCRIPTION);
		setClassCsv(ConsoleImportListenerConstants.UserGroupImport.CLASS_CSV);
		setClassEntity(ConsoleImportListenerConstants.UserGroupImport.CLASS_ENTITY);
	}
}
