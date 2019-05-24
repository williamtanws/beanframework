package com.beanframework.console.listener;

import javax.annotation.PostConstruct;

import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class MediaImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setKey(ConsoleImportListenerConstants.MediaImport.KEY);
		setName(ConsoleImportListenerConstants.MediaImport.NAME);
		setSort(ConsoleImportListenerConstants.MediaImport.SORT);
		setDescription(ConsoleImportListenerConstants.MediaImport.DESCRIPTION);
		setClassCsv(ConsoleImportListenerConstants.MediaImport.CLASS_CSV);
		setClassEntity(ConsoleImportListenerConstants.MediaImport.CLASS_ENTITY);
	}

}
