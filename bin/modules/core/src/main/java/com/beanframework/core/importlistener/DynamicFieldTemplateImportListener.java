package com.beanframework.core.importlistener;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.core.CoreImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

@Component
public class DynamicFieldTemplateImportListener extends ImportListener {
	protected static Logger LOGGER = LoggerFactory.getLogger(DynamicFieldTemplateImportListener.class);

	@PostConstruct
	public void importer() {
		setType(CoreImportListenerConstants.DynamicFieldTemplateImport.TYPE);
		setName(CoreImportListenerConstants.DynamicFieldTemplateImport.NAME);
		setSort(CoreImportListenerConstants.DynamicFieldTemplateImport.SORT);
		setDescription(CoreImportListenerConstants.DynamicFieldTemplateImport.DESCRIPTION);
		setClassCsv(CoreImportListenerConstants.DynamicFieldTemplateImport.CLASS_CSV);
		setClassEntity(CoreImportListenerConstants.DynamicFieldTemplateImport.CLASS_ENTITY);
	}

}
