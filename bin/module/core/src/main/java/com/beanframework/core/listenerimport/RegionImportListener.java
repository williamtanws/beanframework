package com.beanframework.core.listenerimport;

import javax.annotation.PostConstruct;

import com.beanframework.core.ImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class RegionImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setType(ImportListenerConstants.RegionImport.TYPE);
		setName(ImportListenerConstants.RegionImport.NAME);
		setSort(ImportListenerConstants.RegionImport.SORT);
		setDescription(ImportListenerConstants.RegionImport.DESCRIPTION);
		setClassCsv(ImportListenerConstants.RegionImport.CLASS_CSV);
		setClassEntity(ImportListenerConstants.RegionImport.CLASS_ENTITY);
	}

}
