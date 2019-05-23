package com.beanframework.console.listener;

import javax.annotation.PostConstruct;

import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.console.registry.ImportListener;

public class AdminImportListener extends ImportListener {
	
	@PostConstruct
	public void importer() {
		setKey(ConsoleImportListenerConstants.AdminImport.KEY);
		setName(ConsoleImportListenerConstants.AdminImport.NAME);
		setSort(ConsoleImportListenerConstants.AdminImport.SORT);
		setDescription(ConsoleImportListenerConstants.AdminImport.DESCRIPTION);
		setClassCsv(ConsoleImportListenerConstants.AdminImport.CLASS_CSV);
		setClassEntity(ConsoleImportListenerConstants.AdminImport.CLASS_ENTITY);
	}
}
