package com.beanframework.console.listener;

import javax.annotation.PostConstruct;

import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.console.registry.ImportListener;

public class EmployeeImportListener extends ImportListener {
	
	@PostConstruct
	public void importer() {
		setKey(ConsoleImportListenerConstants.EmployeeImport.KEY);
		setName(ConsoleImportListenerConstants.EmployeeImport.NAME);
		setSort(ConsoleImportListenerConstants.EmployeeImport.SORT);
		setDescription(ConsoleImportListenerConstants.EmployeeImport.DESCRIPTION);
		setClassCsv(ConsoleImportListenerConstants.EmployeeImport.CLASS_CSV);
		setClassEntity(ConsoleImportListenerConstants.EmployeeImport.CLASS_ENTITY);
	}
}
