package com.beanframework.console.listener;

import javax.annotation.PostConstruct;

import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class CronjobImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setType(ConsoleImportListenerConstants.CronjobImport.TYPE);
		setName(ConsoleImportListenerConstants.CronjobImport.NAME);
		setSort(ConsoleImportListenerConstants.CronjobImport.SORT);
		setDescription(ConsoleImportListenerConstants.CronjobImport.DESCRIPTION);
		setClassCsv(ConsoleImportListenerConstants.CronjobImport.CLASS_CSV);
		setClassEntity(ConsoleImportListenerConstants.CronjobImport.CLASS_ENTITY);
	}
}
