package com.beanframework.core.importlistener;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.beanframework.core.CoreImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

@Component
public class CustomerImportListener extends ImportListener {
	
	@PostConstruct
	public void importer() {
		setType(CoreImportListenerConstants.CustomerImport.TYPE);
		setName(CoreImportListenerConstants.CustomerImport.NAME);
		setSort(CoreImportListenerConstants.CustomerImport.SORT);
		setDescription(CoreImportListenerConstants.CustomerImport.DESCRIPTION);
		setClassCsv(CoreImportListenerConstants.CustomerImport.CLASS_CSV);
		setClassEntity(CoreImportListenerConstants.CustomerImport.CLASS_ENTITY);
	}
}
