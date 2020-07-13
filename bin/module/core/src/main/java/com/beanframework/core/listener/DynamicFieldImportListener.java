package com.beanframework.core.listener;

import javax.annotation.PostConstruct;

import com.beanframework.core.ImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

public class DynamicFieldImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setType(ImportListenerConstants.DynamicFieldImport.TYPE);
		setName(ImportListenerConstants.DynamicFieldImport.NAME);
		setSort(ImportListenerConstants.DynamicFieldImport.SORT);
		setDescription(ImportListenerConstants.DynamicFieldImport.DESCRIPTION);
		setClassCsv(ImportListenerConstants.DynamicFieldImport.CLASS_CSV);
		setClassEntity(ImportListenerConstants.DynamicFieldImport.CLASS_ENTITY);
	}
}
