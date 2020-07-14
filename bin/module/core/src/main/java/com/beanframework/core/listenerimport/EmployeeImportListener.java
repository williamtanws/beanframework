package com.beanframework.core.listenerimport;

import javax.annotation.PostConstruct;

import com.beanframework.core.ImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class EmployeeImportListener extends ImportListener {
	
	@PostConstruct
	public void importer() {
		setType(ImportListenerConstants.EmployeeImport.TYPE);
		setName(ImportListenerConstants.EmployeeImport.NAME);
		setSort(ImportListenerConstants.EmployeeImport.SORT);
		setDescription(ImportListenerConstants.EmployeeImport.DESCRIPTION);
		setClassCsv(ImportListenerConstants.EmployeeImport.CLASS_CSV);
		setClassEntity(ImportListenerConstants.EmployeeImport.CLASS_ENTITY);
	}
}
