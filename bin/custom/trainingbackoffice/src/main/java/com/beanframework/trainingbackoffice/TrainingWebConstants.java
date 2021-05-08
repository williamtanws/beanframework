package com.beanframework.trainingbackoffice;

public interface TrainingWebConstants {

	public interface Path {

		public interface Api {
			public static final String TRAINING = "${path.api.training}";
			public static final String TRAINING_HISTORY = "${path.api.training.history}";
			public static final String TRAINING_CHECKID = "${path.api.training.checkid}";
		}

		public static final String TRAINING = "${path.training}";
		public static final String TRAINING_FORM = "${path.training.form}";
	}

	public interface View {
		public static final String TRAINING = "${view.training}";
		public static final String TRAINING_FORM = "${view.training.form}";
	}

	public interface ModelAttribute {
		public static final String TRAINING_DTO = "trainingDto";
	}

	public interface TrainingPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "training_read";
		public static final String AUTHORITY_CREATE = "training_create";
		public static final String AUTHORITY_UPDATE = "training_update";
		public static final String AUTHORITY_DELETE = "training_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}
}
