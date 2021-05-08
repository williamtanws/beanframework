package com.beanframework.trainingcore.listener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.beanframework.common.registry.AfterRemoveListenerRegistry;
import com.beanframework.common.registry.AfterSaveListenerRegistry;
import com.beanframework.common.registry.BeforeRemoveListenerRegistry;
import com.beanframework.common.registry.BeforeSaveListenerRegistry;
import com.beanframework.imex.ImexConstants;
import com.beanframework.imex.registry.ImportListenerRegistry;
import com.beanframework.trainingcore.TrainingImportListenerConstants;
import com.beanframework.trainingcore.importlistener.TrainingImportListener;

@Component
public class TrainingListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private BeforeSaveListenerRegistry beforeSaveListenerRegistry;

	@Autowired
	private AfterSaveListenerRegistry afterSaveListenerRegistry;

	@Autowired
	private BeforeRemoveListenerRegistry beforeRemoveListenerRegistry;

	@Autowired
	private AfterRemoveListenerRegistry afterRemoveListenerRegistry;

	@Autowired
	private TrainingBeforeSaveListener trainingBeforeSaveListener;

	@Autowired
	private TrainingAfterSaveListener trainingAfterSaveListener;

	@Autowired
	private TrainingBeforeRemoveListener trainingBeforeRemoveListener;

	@Autowired
	private TrainingAfterRemoveListener trainingAfterRemoveListener;

	@Autowired
	private ImportListenerRegistry importListenerRegistry;

	@Value(ImexConstants.IMEX_IMPORT_LISTENER_TYPES)
	private List<String> importListenerTypesList;

	@Autowired
	private TrainingImportListener trainingImportListener;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		beforeSaveListenerRegistry.addListener(trainingBeforeSaveListener);
		afterSaveListenerRegistry.addListener(trainingAfterSaveListener);
		beforeRemoveListenerRegistry.addListener(trainingBeforeRemoveListener);
		afterRemoveListenerRegistry.addListener(trainingAfterRemoveListener);

		if (importListenerTypesList.contains(TrainingImportListenerConstants.TrainingImport.TYPE))
			importListenerRegistry.addListener(trainingImportListener);
	}

}
