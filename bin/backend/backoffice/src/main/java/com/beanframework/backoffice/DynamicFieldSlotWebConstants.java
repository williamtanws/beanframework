package com.beanframework.backoffice;

public interface DynamicFieldSlotWebConstants {

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.dynamicfieldslot.checkid}";
			public static final String PAGE = "${path.api.dynamicfieldslot.page}";
			public static final String HISTORY = "${path.api.dynamicfieldslot.history}";
		}

		public static final String DYNAMICFIELDSLOT = "${path.dynamicfieldslot}";
	}

	public interface View {
		public static final String LIST = "${view.dynamicfieldslot.list}";
	}

	public interface ModelAttribute {
		public static final String DYNAMICFIELDSLOT_DTO = "dynamicfieldslotDto";
	}
	
	public interface DynamicFieldSlotPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "dynamicfieldslot_read";
		public static final String AUTHORITY_CREATE = "dynamicfieldslot_create";
		public static final String AUTHORITY_UPDATE = "dynamicfieldslot_update";
		public static final String AUTHORITY_DELETE = "dynamicfieldslot_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}
}
