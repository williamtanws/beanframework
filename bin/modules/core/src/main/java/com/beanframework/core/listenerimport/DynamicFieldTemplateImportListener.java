package com.beanframework.core.listenerimport;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.core.ImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class DynamicFieldTemplateImportListener extends ImportListener {
	protected static Logger LOGGER = LoggerFactory.getLogger(DynamicFieldTemplateImportListener.class);

	@PostConstruct
	public void importer() {
		setType(ImportListenerConstants.DynamicFieldTemplateImport.TYPE);
		setName(ImportListenerConstants.DynamicFieldTemplateImport.NAME);
		setSort(ImportListenerConstants.DynamicFieldTemplateImport.SORT);
		setDescription(ImportListenerConstants.DynamicFieldTemplateImport.DESCRIPTION);
		setClassCsv(ImportListenerConstants.DynamicFieldTemplateImport.CLASS_CSV);
		setClassEntity(ImportListenerConstants.DynamicFieldTemplateImport.CLASS_ENTITY);
	}

}
