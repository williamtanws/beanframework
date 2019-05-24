package com.beanframework.console.listener;

import javax.annotation.PostConstruct;

import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class UserRightImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setKey(ConsoleImportListenerConstants.UserRightImport.KEY);
		setName(ConsoleImportListenerConstants.UserRightImport.NAME);
		setSort(ConsoleImportListenerConstants.UserRightImport.SORT);
		setDescription(ConsoleImportListenerConstants.UserRightImport.DESCRIPTION);
		setClassCsv(ConsoleImportListenerConstants.UserRightImport.CLASS_CSV);
		setClassEntity(ConsoleImportListenerConstants.UserRightImport.CLASS_ENTITY);
	}
}
