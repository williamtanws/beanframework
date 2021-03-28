package com.beanframework.core.listenerimport;

import javax.annotation.PostConstruct;

import com.beanframework.core.ImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class MenuImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setType(ImportListenerConstants.MenuImport.TYPE);
		setName(ImportListenerConstants.MenuImport.NAME);
		setSort(ImportListenerConstants.MenuImport.SORT);
		setDescription(ImportListenerConstants.MenuImport.DESCRIPTION);
		setClassCsv(ImportListenerConstants.MenuImport.CLASS_CSV);
		setClassEntity(ImportListenerConstants.MenuImport.CLASS_ENTITY);
	}
}
