package com.beanframework.console.listener;

import javax.annotation.PostConstruct;

import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class ConfigurationImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setType(ConsoleImportListenerConstants.ConfigurationImport.TYPE);
		setName(ConsoleImportListenerConstants.ConfigurationImport.NAME);
		setSort(ConsoleImportListenerConstants.ConfigurationImport.SORT);
		setDescription(ConsoleImportListenerConstants.ConfigurationImport.DESCRIPTION);
		setClassCsv(ConsoleImportListenerConstants.ConfigurationImport.CLASS_CSV);
		setClassEntity(ConsoleImportListenerConstants.ConfigurationImport.CLASS_ENTITY);
	}
}
