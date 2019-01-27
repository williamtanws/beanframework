package com.beanframework.email.domain;

public class EmailEnum {

	public enum Status {
		DRAFT("Draft"), PENDING("Pending"), PROCESSING("Processing"), SENT("Sent"), FAILED("Failed");
		
		private String value;

		Status(final String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static Status fromName(String name) {
			if (DRAFT.name().equalsIgnoreCase(name)) {
				return DRAFT;
			}
			else if (PENDING.name().equalsIgnoreCase(name)) {
				return PENDING;
			} else if (PROCESSING.name().equalsIgnoreCase(name)) {
				return PROCESSING;
			} else if (SENT.name().equalsIgnoreCase(name)) {
				return SENT;
			} else if (FAILED.name().equalsIgnoreCase(name)) {
				return FAILED;
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
