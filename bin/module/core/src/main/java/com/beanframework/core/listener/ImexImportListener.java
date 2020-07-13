package com.beanframework.core.listener;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.core.ImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class ImexImportListener extends ImportListener {
	protected static Logger LOGGER = LoggerFactory.getLogger(ImexImportListener.class);

	@PostConstruct
	public void importer() {
		setType(ImportListenerConstants.ImexImport.TYPE);
		setName(ImportListenerConstants.ImexImport.NAME);
		setSort(ImportListenerConstants.ImexImport.SORT);
		setDescription(ImportListenerConstants.ImexImport.DESCRIPTION);
		setClassCsv(ImportListenerConstants.ImexImport.CLASS_CSV);
		setClassEntity(ImportListenerConstants.ImexImport.CLASS_ENTITY);
	}
}
