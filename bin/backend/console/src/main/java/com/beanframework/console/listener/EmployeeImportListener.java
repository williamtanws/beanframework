package com.beanframework.console.listener;

import javax.annotation.PostConstruct;

import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class EmployeeImportListener extends ImportListener {
	
	@PostConstruct
	public void importer() {
		setType(ConsoleImportListenerConstants.EmployeeImport.TYPE);
		setName(ConsoleImportListenerConstants.EmployeeImport.NAME);
		setSort(ConsoleImportListenerConstants.EmployeeImport.SORT);
		setDescription(ConsoleImportListenerConstants.EmployeeImport.DESCRIPTION);
		setClassCsv(ConsoleImportListenerConstants.EmployeeImport.CLASS_CSV);
		setClassEntity(ConsoleImportListenerConstants.EmployeeImport.CLASS_ENTITY);
	}
}
