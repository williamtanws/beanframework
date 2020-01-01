package com.beanframework.console.listener;

import javax.annotation.PostConstruct;

import com.beanframework.console.TemplateConsoleImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class TemplateImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setType(TemplateConsoleImportListenerConstants.TemplateImport.TYPE);
		setName(TemplateConsoleImportListenerConstants.TemplateImport.NAME);
		setSort(TemplateConsoleImportListenerConstants.TemplateImport.SORT);
		setDescription(TemplateConsoleImportListenerConstants.TemplateImport.DESCRIPTION);
		setClassCsv(TemplateConsoleImportListenerConstants.TemplateImport.CLASS_CSV);
		setClassEntity(TemplateConsoleImportListenerConstants.TemplateImport.CLASS_ENTITY);
	}

}
