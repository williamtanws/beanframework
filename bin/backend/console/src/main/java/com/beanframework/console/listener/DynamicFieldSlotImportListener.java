package com.beanframework.console.listener;

import javax.annotation.PostConstruct;

import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.console.registry.ImportListener;

public class DynamicFieldSlotImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setKey(ConsoleImportListenerConstants.DynamicFieldSlotImport.KEY);
		setName(ConsoleImportListenerConstants.DynamicFieldSlotImport.NAME);
		setSort(ConsoleImportListenerConstants.DynamicFieldSlotImport.SORT);
		setDescription(ConsoleImportListenerConstants.DynamicFieldSlotImport.DESCRIPTION);
		setClassCsv(ConsoleImportListenerConstants.DynamicFieldSlotImport.CLASS_CSV);
		setClassEntity(ConsoleImportListenerConstants.DynamicFieldSlotImport.CLASS_ENTITY);
	}

}
