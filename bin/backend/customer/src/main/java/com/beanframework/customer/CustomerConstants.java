package com.beanframework.customer;

public interface CustomerConstants {

	public interface Discriminator {
		public static final String CUSTOMER = "customer";
	}
	
	public static interface Customer {
	}
	
	public static interface Locale{
		public static final String ID_REQUIRED = "module.customer.id.required";
		public static final String ID_EXISTS = "module.customer.id.exists";
		public static final String UUID_NOT_EXISTS = "module.customer.uuid.notexists";
		public static final String ID_NOT_EXISTS = "module.customer.id.notexists";
		public static final String PASSWORD_REQUIRED = "module.customer.password.required";
		public static final String SAVE_CURRENT_CUSTOMER_ERROR = "module.customer.current.save.error";
	}

}
