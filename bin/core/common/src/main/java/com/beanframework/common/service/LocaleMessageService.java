package com.beanframework.common.service;

import java.util.Locale;

public interface LocaleMessageService{

	public String getMessage(String code);

	public String getMessage(String code, Object[] args);

	public String getMessageByLocale(String code, Locale locale);

	public String getMessageByLocale(String code, Object[] args, Locale locale);
}
