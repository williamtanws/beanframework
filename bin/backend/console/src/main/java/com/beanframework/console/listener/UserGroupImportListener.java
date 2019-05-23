package com.beanframework.console.listener;

import javax.annotation.PostConstruct;

import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.console.registry.ImportListener;

public class UserGroupImportListener extends ImportListener {
	
	@PostConstruct
	public void importer() {
		setKey(ConsoleImportListenerConstants.UserGroupImport.KEY);
		setName(ConsoleImportListenerConstants.UserGroupImport.NAME);
		setSort(ConsoleImportListenerConstants.UserGroupImport.SORT);
		setDescription(ConsoleImportListenerConstants.UserGroupImport.DESCRIPTION);
		setClassCsv(ConsoleImportListenerConstants.UserGroupImport.CLASS_CSV);
		setClassEntity(ConsoleImportListenerConstants.UserGroupImport.CLASS_ENTITY);
	}
}
