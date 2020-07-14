package com.beanframework.core.listenerimport;

import javax.annotation.PostConstruct;

import com.beanframework.core.ImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class UserRightImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setType(ImportListenerConstants.UserRightImport.TYPE);
		setName(ImportListenerConstants.UserRightImport.NAME);
		setSort(ImportListenerConstants.UserRightImport.SORT);
		setDescription(ImportListenerConstants.UserRightImport.DESCRIPTION);
		setClassCsv(ImportListenerConstants.UserRightImport.CLASS_CSV);
		setClassEntity(ImportListenerConstants.UserRightImport.CLASS_ENTITY);
	}
}
