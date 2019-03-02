package com.beanframework.common.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LocaleMessageServiceImpl implements LocaleMessageService {

	@Autowired
	private MessageSource messageSource;

	public String getMessage(String code) {
		Locale locale = LocaleContextHolder.getLocale();
		try {			
			return messageSource.getMessage(code, null, locale);
		} catch (NoSuchMessageException e) {
			return null;
		}
	}

	public String getMessage(String code, Object[] args) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(code, args, locale);
	}

	public String getMessageByLocale(String code, Locale locale) {
		return messageSource.getMessage(code, null, locale);
	}

	public String getMessageByLocale(String code, Object[] args, Locale locale) {
		return messageSource.getMessage(code, args, locale);
	}
}
