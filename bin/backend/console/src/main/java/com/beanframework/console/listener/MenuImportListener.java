package com.beanframework.console.listener;

import javax.annotation.PostConstruct;

import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class MenuImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setKey(ConsoleImportListenerConstants.MenuImport.KEY);
		setName(ConsoleImportListenerConstants.MenuImport.NAME);
		setSort(ConsoleImportListenerConstants.MenuImport.SORT);
		setDescription(ConsoleImportListenerConstants.MenuImport.DESCRIPTION);
		setClassCsv(ConsoleImportListenerConstants.MenuImport.CLASS_CSV);
		setClassEntity(ConsoleImportListenerConstants.MenuImport.CLASS_ENTITY);
	}
}
