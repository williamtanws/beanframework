package com.beanframework.cronjob.domain;

public class CronjobEnum {

	public enum JobTrigger {
		RUN_ONCE("Run Once"), START("Start"), PAUSE("Pause"), RESUME("Resume"), STOP("Stop");

		private String value;

		JobTrigger(final String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static JobTrigger fromName(String name) {
			if (RUN_ONCE.name().equalsIgnoreCase(name)) {
				return RUN_ONCE;
			} else if (START.name().equalsIgnoreCase(name)) {
				return START;
			} else if (PAUSE.name().equalsIgnoreCase(name)) {
				return PAUSE;
			} else if (RESUME.name().equalsIgnoreCase(name)) {
				return RESUME;
			} else if (STOP.name().equalsIgnoreCase(name)) {
				return STOP;
			} else {
				return null;
			}
		}

		@Override
		public String toString() {
			return this.getValue();
		}
	}

	public enum Status {
		CONTINUED("Continued"), RUNNING("Running"), PAUSED("Paused"), FINISHED("Finished"), ABORTED("Aborted");

		private String value;

		Status(final String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static Status fromName(String name) {
			if (CONTINUED.name().equalsIgnoreCase(name)) {
				return CONTINUED;
			} else if (RUNNING.name().equalsIgnoreCase(name)) {
				return RUNNING;
			} else if (PAUSED.name().equalsIgnoreCase(name)) {
				return PAUSED;
			} else if (FINISHED.name().equalsIgnoreCase(name)) {
				return FINISHED;
			} else if (ABORTED.name().equalsIgnoreCase(name)) {
				return ABORTED;
			} else {
				return null;
			}
		}

		@Override
		public String toString() {
			return this.getValue();
		}
	}

	public enum Result {
		SUCCESS("Success"), ERROR("Error");

		private String value;

		Result(final String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static Result fromName(String name) {
			if (SUCCESS.name().equalsIgnoreCase(name)) {
				return SUCCESS;
			} else if (ERROR.name().equalsIgnoreCase(name)) {
				return ERROR;
			} else {
				return null;
			}
		}

		@Override
		public String toString() {
			return this.getValue();
		}
	}

}
