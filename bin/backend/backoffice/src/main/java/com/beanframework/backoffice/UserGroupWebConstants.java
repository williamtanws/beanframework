package com.beanframework.backoffice;

public interface UserGroupWebConstants {

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.usergroup.checkid}";
			public static final String PAGE = "${path.api.usergroup.page}";
			public static final String HISTORY = "${path.api.usergroup.history}";
		}

		public static final String USERGROUP = "${path.usergroup}";
	}

	public interface View {
		public static final String LIST = "${view.usergroup.list}";
	}

	public interface ModelAttribute {
		public static final String USERGROUP_DTO = "usergroupDto";
	}
}
