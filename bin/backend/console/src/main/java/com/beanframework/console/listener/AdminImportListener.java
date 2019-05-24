package com.beanframework.console.listener;

import javax.annotation.PostConstruct;

import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class AdminImportListener extends ImportListener {
	
	@PostConstruct
	public void importer() {
		setType(ConsoleImportListenerConstants.AdminImport.TYPE);
		setName(ConsoleImportListenerConstants.AdminImport.NAME);
		setSort(ConsoleImportListenerConstants.AdminImport.SORT);
		setDescription(ConsoleImportListenerConstants.AdminImport.DESCRIPTION);
		setClassCsv(ConsoleImportListenerConstants.AdminImport.CLASS_CSV);
		setClassEntity(ConsoleImportListenerConstants.AdminImport.CLASS_ENTITY);
	}
}
