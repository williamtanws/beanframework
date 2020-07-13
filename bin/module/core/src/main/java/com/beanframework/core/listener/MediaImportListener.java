package com.beanframework.core.listener;

import javax.annotation.PostConstruct;

import com.beanframework.core.ImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class MediaImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setType(ImportListenerConstants.MediaImport.TYPE);
		setName(ImportListenerConstants.MediaImport.NAME);
		setSort(ImportListenerConstants.MediaImport.SORT);
		setDescription(ImportListenerConstants.MediaImport.DESCRIPTION);
		setClassCsv(ImportListenerConstants.MediaImport.CLASS_CSV);
		setClassEntity(ImportListenerConstants.MediaImport.CLASS_ENTITY);
	}

}
