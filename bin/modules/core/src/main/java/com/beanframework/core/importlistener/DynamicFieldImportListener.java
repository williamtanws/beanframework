package com.beanframework.core.importlistener;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.beanframework.core.CoreImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

@Component
public class DynamicFieldImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setType(CoreImportListenerConstants.DynamicFieldImport.TYPE);
		setName(CoreImportListenerConstants.DynamicFieldImport.NAME);
		setSort(CoreImportListenerConstants.DynamicFieldImport.SORT);
		setDescription(CoreImportListenerConstants.DynamicFieldImport.DESCRIPTION);
		setClassCsv(CoreImportListenerConstants.DynamicFieldImport.CLASS_CSV);
		setClassEntity(CoreImportListenerConstants.DynamicFieldImport.CLASS_ENTITY);
	}
}
