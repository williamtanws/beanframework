package com.beanframework.console.listener;

import javax.annotation.PostConstruct;

import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class CustomerImportListener extends ImportListener {
	
	@PostConstruct
	public void importer() {
		setKey(ConsoleImportListenerConstants.CustomerImport.KEY);
		setName(ConsoleImportListenerConstants.CustomerImport.NAME);
		setSort(ConsoleImportListenerConstants.CustomerImport.SORT);
		setDescription(ConsoleImportListenerConstants.CustomerImport.DESCRIPTION);
		setClassCsv(ConsoleImportListenerConstants.CustomerImport.CLASS_CSV);
		setClassEntity(ConsoleImportListenerConstants.CustomerImport.CLASS_ENTITY);
	}
}
