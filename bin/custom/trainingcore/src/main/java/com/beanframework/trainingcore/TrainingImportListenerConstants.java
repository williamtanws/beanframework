package com.beanframework.trainingcore;

import com.beanframework.training.domain.Training;
import com.beanframework.trainingcore.csv.TrainingCsv;

public interface TrainingImportListenerConstants {

	public interface TrainingImport {
		public static final String TYPE = Training.class.getSimpleName();
		public static final String NAME = "Training";
		public static final int SORT = 500;
		public static final String DESCRIPTION = "Update/Remove Training Data";
		public static final Class<TrainingCsv> CLASS_CSV = TrainingCsv.class;
		public static final Class<Training> CLASS_ENTITY = Training.class;
	}
}
