package com.beanframework.console.listener;

import javax.annotation.PostConstruct;

import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.console.registry.ImportListener;

public class ConfigurationImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setKey(ConsoleImportListenerConstants.ConfigurationImport.KEY);
		setName(ConsoleImportListenerConstants.ConfigurationImport.NAME);
		setSort(ConsoleImportListenerConstants.ConfigurationImport.SORT);
		setDescription(ConsoleImportListenerConstants.ConfigurationImport.DESCRIPTION);
		setClassCsv(ConsoleImportListenerConstants.ConfigurationImport.CLASS_CSV);
		setClassEntity(ConsoleImportListenerConstants.ConfigurationImport.CLASS_ENTITY);
	}
}
