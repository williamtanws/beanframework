package com.beanframework.console.listener;

import javax.annotation.PostConstruct;

import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.console.registry.ImportListener;

public class CronjobImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setKey(ConsoleImportListenerConstants.CronjobImport.KEY);
		setName(ConsoleImportListenerConstants.CronjobImport.NAME);
		setSort(ConsoleImportListenerConstants.CronjobImport.SORT);
		setDescription(ConsoleImportListenerConstants.CronjobImport.DESCRIPTION);
		setClassCsv(ConsoleImportListenerConstants.CronjobImport.CLASS_CSV);
		setClassEntity(ConsoleImportListenerConstants.CronjobImport.CLASS_ENTITY);
	}
}
