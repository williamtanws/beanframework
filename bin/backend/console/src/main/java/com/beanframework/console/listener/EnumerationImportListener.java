package com.beanframework.console.listener;

import javax.annotation.PostConstruct;

import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class EnumerationImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setKey(ConsoleImportListenerConstants.EnumerationImport.KEY);
		setName(ConsoleImportListenerConstants.EnumerationImport.NAME);
		setSort(ConsoleImportListenerConstants.EnumerationImport.SORT);
		setDescription(ConsoleImportListenerConstants.EnumerationImport.DESCRIPTION);
		setClassCsv(ConsoleImportListenerConstants.EnumerationImport.CLASS_CSV);
		setClassEntity(ConsoleImportListenerConstants.EnumerationImport.CLASS_ENTITY);
	}
}
