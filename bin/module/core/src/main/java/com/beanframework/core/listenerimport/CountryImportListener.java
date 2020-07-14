package com.beanframework.core.listenerimport;

import javax.annotation.PostConstruct;

import com.beanframework.core.ImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class CountryImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setType(ImportListenerConstants.CountryImport.TYPE);
		setName(ImportListenerConstants.CountryImport.NAME);
		setSort(ImportListenerConstants.CountryImport.SORT);
		setDescription(ImportListenerConstants.CountryImport.DESCRIPTION);
		setClassCsv(ImportListenerConstants.CountryImport.CLASS_CSV);
		setClassEntity(ImportListenerConstants.CountryImport.CLASS_ENTITY);
	}

}
