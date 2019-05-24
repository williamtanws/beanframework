package com.beanframework.console.listener;

import javax.annotation.PostConstruct;

import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class LanguageImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setKey(ConsoleImportListenerConstants.LanguageImport.KEY);
		setName(ConsoleImportListenerConstants.LanguageImport.NAME);
		setSort(ConsoleImportListenerConstants.LanguageImport.SORT);
		setDescription(ConsoleImportListenerConstants.LanguageImport.DESCRIPTION);
		setClassCsv(ConsoleImportListenerConstants.LanguageImport.CLASS_CSV);
		setClassEntity(ConsoleImportListenerConstants.LanguageImport.CLASS_ENTITY);
	}

}
