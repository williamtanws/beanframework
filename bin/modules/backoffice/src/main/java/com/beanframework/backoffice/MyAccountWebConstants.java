package com.beanframework.backoffice;

public interface MyAccountWebConstants {

	public interface Path {
		public static final String MYACCOUNT = "${path.user.myaccount}";
	}

	public interface View {
		public static final String MYACCOUNT = "${view.user.myaccount}";
	}

	public interface ModelAttribute {
		public static final String MYACCOUNT = "myaccount";
	}
}
