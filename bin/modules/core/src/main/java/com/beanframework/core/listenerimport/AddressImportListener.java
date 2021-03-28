package com.beanframework.core.listenerimport;

import javax.annotation.PostConstruct;

import com.beanframework.core.ImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class AddressImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setType(ImportListenerConstants.AddressImport.TYPE);
		setName(ImportListenerConstants.AddressImport.NAME);
		setSort(ImportListenerConstants.AddressImport.SORT);
		setDescription(ImportListenerConstants.AddressImport.DESCRIPTION);
		setClassCsv(ImportListenerConstants.AddressImport.CLASS_CSV);
		setClassEntity(ImportListenerConstants.AddressImport.CLASS_ENTITY);
	}

}
