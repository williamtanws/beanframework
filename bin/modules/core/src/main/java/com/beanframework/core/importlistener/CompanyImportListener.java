package com.beanframework.core.importlistener;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.beanframework.core.CoreImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

@Component
public class CompanyImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setType(CoreImportListenerConstants.CompanyImport.TYPE);
		setName(CoreImportListenerConstants.CompanyImport.NAME);
		setSort(CoreImportListenerConstants.CompanyImport.SORT);
		setDescription(CoreImportListenerConstants.CompanyImport.DESCRIPTION);
		setClassCsv(CoreImportListenerConstants.CompanyImport.CLASS_CSV);
		setClassEntity(CoreImportListenerConstants.CompanyImport.CLASS_ENTITY);
	}

}
