package com.beanframework.core.importlistener;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.beanframework.core.CoreImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

@Component
public class MediaImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setType(CoreImportListenerConstants.MediaImport.TYPE);
		setName(CoreImportListenerConstants.MediaImport.NAME);
		setSort(CoreImportListenerConstants.MediaImport.SORT);
		setDescription(CoreImportListenerConstants.MediaImport.DESCRIPTION);
		setClassCsv(CoreImportListenerConstants.MediaImport.CLASS_CSV);
		setClassEntity(CoreImportListenerConstants.MediaImport.CLASS_ENTITY);
	}

}
