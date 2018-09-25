package com.beanframework.console;

public class WebCacheConstants {

	public interface Path {
		public static final String CACHE = "${path.cache}";
		public static final String CACHE_CLEARALL = "${path.cache.clearall}";
	}

	public interface View {
		public static final String CACHE = "${view.cache}";
	}
	
	public interface Locale {
		public static final String CACHE_CLEARALL_SUCCESS = "module.cache.clearall.success";
	}
}
