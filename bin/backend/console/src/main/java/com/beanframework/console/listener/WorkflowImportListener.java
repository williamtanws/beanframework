package com.beanframework.console.listener;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class WorkflowImportListener extends ImportListener {
	protected static Logger LOGGER = LoggerFactory.getLogger(WorkflowImportListener.class);

	@PostConstruct
	public void importer() {
		setKey(ConsoleImportListenerConstants.WorkflowImport.KEY);
		setName(ConsoleImportListenerConstants.WorkflowImport.NAME);
		setSort(ConsoleImportListenerConstants.WorkflowImport.SORT);
		setDescription(ConsoleImportListenerConstants.WorkflowImport.DESCRIPTION);
		setClassCsv(ConsoleImportListenerConstants.WorkflowImport.CLASS_CSV);
		setClassEntity(ConsoleImportListenerConstants.WorkflowImport.CLASS_ENTITY);
	}
}
