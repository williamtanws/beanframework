package com.beanframework.console.listener;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class DynamicFieldTemplateImportListener extends ImportListener {
	protected static Logger LOGGER = LoggerFactory.getLogger(DynamicFieldTemplateImportListener.class);

	@PostConstruct
	public void importer() {
		setKey(ConsoleImportListenerConstants.DynamicFieldTemplateImport.KEY);
		setName(ConsoleImportListenerConstants.DynamicFieldTemplateImport.NAME);
		setSort(ConsoleImportListenerConstants.DynamicFieldTemplateImport.SORT);
		setDescription(ConsoleImportListenerConstants.DynamicFieldTemplateImport.DESCRIPTION);
		setClassCsv(ConsoleImportListenerConstants.DynamicFieldTemplateImport.CLASS_CSV);
		setClassEntity(ConsoleImportListenerConstants.DynamicFieldTemplateImport.CLASS_ENTITY);
	}

}
