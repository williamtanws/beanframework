package com.beanframework.core.importlistener;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.beanframework.core.CoreImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

@Component
public class SiteImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setType(CoreImportListenerConstants.SiteImport.TYPE);
		setName(CoreImportListenerConstants.SiteImport.NAME);
		setSort(CoreImportListenerConstants.SiteImport.SORT);
		setDescription(CoreImportListenerConstants.SiteImport.DESCRIPTION);
		setClassCsv(CoreImportListenerConstants.SiteImport.CLASS_CSV);
		setClassEntity(CoreImportListenerConstants.SiteImport.CLASS_ENTITY);
	}
}
