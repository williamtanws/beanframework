package com.beanframework.console.listener;

import javax.annotation.PostConstruct;

import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class DynamicFieldSlotImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setType(ConsoleImportListenerConstants.DynamicFieldSlotImport.TYPE);
		setName(ConsoleImportListenerConstants.DynamicFieldSlotImport.NAME);
		setSort(ConsoleImportListenerConstants.DynamicFieldSlotImport.SORT);
		setDescription(ConsoleImportListenerConstants.DynamicFieldSlotImport.DESCRIPTION);
		setClassCsv(ConsoleImportListenerConstants.DynamicFieldSlotImport.CLASS_CSV);
		setClassEntity(ConsoleImportListenerConstants.DynamicFieldSlotImport.CLASS_ENTITY);
	}

}
