package com.beanframework.core.listenerimport;

import javax.annotation.PostConstruct;

import com.beanframework.core.ImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class SiteImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setType(ImportListenerConstants.SiteImport.TYPE);
		setName(ImportListenerConstants.SiteImport.NAME);
		setSort(ImportListenerConstants.SiteImport.SORT);
		setDescription(ImportListenerConstants.SiteImport.DESCRIPTION);
		setClassCsv(ImportListenerConstants.SiteImport.CLASS_CSV);
		setClassEntity(ImportListenerConstants.SiteImport.CLASS_ENTITY);
	}
}
