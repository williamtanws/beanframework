package com.beanframework.backoffice;

public interface CommentWebConstants {

	public interface Path {

		public interface Api {
			public static final String CHECKID = "${path.api.comment.checkid}";
			public static final String PAGE = "${path.api.comment.page}";
			public static final String HISTORY = "${path.api.comment.history}";
		}

		public static final String COMMENT = "${path.comment}";
	}

	public interface View {
		public static final String LIST = "${view.comment.list}";
	}

	public interface ModelAttribute {

		public static final String CREATE = "commentCreate";
		public static final String UPDATE = "commentUpdate";
	}
}
