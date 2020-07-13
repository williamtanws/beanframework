package com.beanframework.core.listener;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.core.ImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class WorkflowImportListener extends ImportListener {
	protected static Logger LOGGER = LoggerFactory.getLogger(WorkflowImportListener.class);

	@PostConstruct
	public void importer() {
		setType(ImportListenerConstants.WorkflowImport.TYPE);
		setName(ImportListenerConstants.WorkflowImport.NAME);
		setSort(ImportListenerConstants.WorkflowImport.SORT);
		setDescription(ImportListenerConstants.WorkflowImport.DESCRIPTION);
		setClassCsv(ImportListenerConstants.WorkflowImport.CLASS_CSV);
		setClassEntity(ImportListenerConstants.WorkflowImport.CLASS_ENTITY);
	}
}
