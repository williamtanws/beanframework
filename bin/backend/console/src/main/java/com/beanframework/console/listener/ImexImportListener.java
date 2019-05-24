package com.beanframework.console.listener;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class ImexImportListener extends ImportListener {
	protected static Logger LOGGER = LoggerFactory.getLogger(ImexImportListener.class);

	@PostConstruct
	public void importer() {
		setKey(ConsoleImportListenerConstants.ImexImport.KEY);
		setName(ConsoleImportListenerConstants.ImexImport.NAME);
		setSort(ConsoleImportListenerConstants.ImexImport.SORT);
		setDescription(ConsoleImportListenerConstants.ImexImport.DESCRIPTION);
		setClassCsv(ConsoleImportListenerConstants.ImexImport.CLASS_CSV);
		setClassEntity(ConsoleImportListenerConstants.ImexImport.CLASS_ENTITY);
	}
}
