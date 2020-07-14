package com.beanframework.core.listenerimport;

import javax.annotation.PostConstruct;

import com.beanframework.core.ImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class CurrencyImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setType(ImportListenerConstants.CurrencyImport.TYPE);
		setName(ImportListenerConstants.CurrencyImport.NAME);
		setSort(ImportListenerConstants.CurrencyImport.SORT);
		setDescription(ImportListenerConstants.CurrencyImport.DESCRIPTION);
		setClassCsv(ImportListenerConstants.CurrencyImport.CLASS_CSV);
		setClassEntity(ImportListenerConstants.CurrencyImport.CLASS_ENTITY);
	}

}
