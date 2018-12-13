package com.beanframework.common.exception;

public class ModelSavingException extends SystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6636238520043028136L;
	private ModelSavingException next;

	public ModelSavingException(String message, Throwable nested) {
		super(message, nested);
	}

	public ModelSavingException(String message) {
		super(message);
	}

	public ModelSavingException getNextException() {
		return this.next;
	}

	public void setNextException(ModelSavingException exception) {
		ModelSavingException current;
		for (current = this; current.next != null; current = current.next) {
			;
		}

		current.next = exception;
	}
}