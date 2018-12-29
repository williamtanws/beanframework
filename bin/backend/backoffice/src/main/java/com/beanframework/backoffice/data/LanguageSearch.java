package com.beanframework.backoffice.data;

import com.beanframework.language.domain.Language;

public class LanguageSearch extends Language {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8780777783489567799L;
	private String searchAll;

	public String getSearchAll() {
		return searchAll;
	}

	public void setSearchAll(String searchAll) {
		this.searchAll = searchAll;
	}
}
