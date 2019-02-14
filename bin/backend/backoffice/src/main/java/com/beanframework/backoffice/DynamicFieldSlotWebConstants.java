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
}
