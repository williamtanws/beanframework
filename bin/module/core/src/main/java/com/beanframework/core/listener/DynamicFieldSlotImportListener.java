package com.beanframework.core.listener;

import javax.annotation.PostConstruct;

import com.beanframework.core.ImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class DynamicFieldSlotImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setType(ImportListenerConstants.DynamicFieldSlotImport.TYPE);
		setName(ImportListenerConstants.DynamicFieldSlotImport.NAME);
		setSort(ImportListenerConstants.DynamicFieldSlotImport.SORT);
		setDescription(ImportListenerConstants.DynamicFieldSlotImport.DESCRIPTION);
		setClassCsv(ImportListenerConstants.DynamicFieldSlotImport.CLASS_CSV);
		setClassEntity(ImportListenerConstants.DynamicFieldSlotImport.CLASS_ENTITY);
	}

}
