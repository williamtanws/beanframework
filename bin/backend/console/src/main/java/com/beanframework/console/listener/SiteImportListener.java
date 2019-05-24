package com.beanframework.console.listener;

import javax.annotation.PostConstruct;

import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class SiteImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setKey(ConsoleImportListenerConstants.SiteImport.KEY);
		setName(ConsoleImportListenerConstants.SiteImport.NAME);
		setSort(ConsoleImportListenerConstants.SiteImport.SORT);
		setDescription(ConsoleImportListenerConstants.SiteImport.DESCRIPTION);
		setClassCsv(ConsoleImportListenerConstants.SiteImport.CLASS_CSV);
		setClassEntity(ConsoleImportListenerConstants.SiteImport.CLASS_ENTITY);
	}
}
