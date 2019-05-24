package com.beanframework.console.listener;

import javax.annotation.PostConstruct;

import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class DynamicFieldImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setKey(ConsoleImportListenerConstants.DynamicFieldImport.KEY);
		setName(ConsoleImportListenerConstants.DynamicFieldImport.NAME);
		setSort(ConsoleImportListenerConstants.DynamicFieldImport.SORT);
		setDescription(ConsoleImportListenerConstants.DynamicFieldImport.DESCRIPTION);
		setClassCsv(ConsoleImportListenerConstants.DynamicFieldImport.CLASS_CSV);
		setClassEntity(ConsoleImportListenerConstants.DynamicFieldImport.CLASS_ENTITY);
	}
}
