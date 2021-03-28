package com.beanframework.core.listenerimport;

import javax.annotation.PostConstruct;

import com.beanframework.core.ImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class CompanyImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setType(ImportListenerConstants.CompanyImport.TYPE);
		setName(ImportListenerConstants.CompanyImport.NAME);
		setSort(ImportListenerConstants.CompanyImport.SORT);
		setDescription(ImportListenerConstants.CompanyImport.DESCRIPTION);
		setClassCsv(ImportListenerConstants.CompanyImport.CLASS_CSV);
		setClassEntity(ImportListenerConstants.CompanyImport.CLASS_ENTITY);
	}

}
