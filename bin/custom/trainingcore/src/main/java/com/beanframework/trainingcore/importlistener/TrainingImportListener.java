package com.beanframework.trainingcore.importlistener;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.beanframework.imex.registry.ImportListener;
import com.beanframework.trainingcore.TrainingImportListenerConstants;

@Component
public class TrainingImportListener extends ImportListener {

	@PostConstruct
	public void importer() {
		setType(TrainingImportListenerConstants.TrainingImport.TYPE);
		setName(TrainingImportListenerConstants.TrainingImport.NAME);
		setSort(TrainingImportListenerConstants.TrainingImport.SORT);
		setDescription(TrainingImportListenerConstants.TrainingImport.DESCRIPTION);
		setClassCsv(TrainingImportListenerConstants.TrainingImport.CLASS_CSV);
		setClassEntity(TrainingImportListenerConstants.TrainingImport.CLASS_ENTITY);
	}

}
