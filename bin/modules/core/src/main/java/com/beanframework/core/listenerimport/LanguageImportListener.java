package com.beanframework.core.listenerimport;

import javax.annotation.PostConstruct;

import com.beanframework.core.ImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class LanguageImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setType(ImportListenerConstants.LanguageImport.TYPE);
		setName(ImportListenerConstants.LanguageImport.NAME);
		setSort(ImportListenerConstants.LanguageImport.SORT);
		setDescription(ImportListenerConstants.LanguageImport.DESCRIPTION);
		setClassCsv(ImportListenerConstants.LanguageImport.CLASS_CSV);
		setClassEntity(ImportListenerConstants.LanguageImport.CLASS_ENTITY);
	}

}
