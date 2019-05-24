package com.beanframework.console.listener;

import javax.annotation.PostConstruct;

import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class VendorImportListener extends ImportListener {
	@PostConstruct
	public void importer() {
		setKey(ConsoleImportListenerConstants.VendorImport.KEY);
		setName(ConsoleImportListenerConstants.VendorImport.NAME);
		setSort(ConsoleImportListenerConstants.VendorImport.SORT);
		setDescription(ConsoleImportListenerConstants.VendorImport.DESCRIPTION);
		setClassCsv(ConsoleImportListenerConstants.VendorImport.CLASS_CSV);
		setClassEntity(ConsoleImportListenerConstants.VendorImport.CLASS_ENTITY);
	}
}
