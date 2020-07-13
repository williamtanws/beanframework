package com.beanframework.core.listener;

import javax.annotation.PostConstruct;

import com.beanframework.core.ImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class VendorImportListener extends ImportListener {
	@PostConstruct
	public void importer() {
		setType(ImportListenerConstants.VendorImport.TYPE);
		setName(ImportListenerConstants.VendorImport.NAME);
		setSort(ImportListenerConstants.VendorImport.SORT);
		setDescription(ImportListenerConstants.VendorImport.DESCRIPTION);
		setClassCsv(ImportListenerConstants.VendorImport.CLASS_CSV);
		setClassEntity(ImportListenerConstants.VendorImport.CLASS_ENTITY);
	}
}
