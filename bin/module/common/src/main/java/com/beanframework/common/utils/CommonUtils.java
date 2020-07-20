package com.beanframework.common.utils;

import static org.apache.commons.logging.LogFactory.getLog;

import java.io.File;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;

public abstract class CommonUtils {
	public static final Random r = new Random();
	public static final String DEFAULT_CHARSET = "UTF-8";
	public static final String SEPARATOR = "/";
	public static final String BLANK = "";
	protected final Log log = getLog(getClass());
	public static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

	public static Date getDate() {
		return new Date();
	}

	public static boolean notEmpty(String var) {
		return StringUtils.isNotBlank(var);
	}

	public static boolean empty(String var) {
		return StringUtils.isBlank(var);
	}

	public static boolean notEmpty(Object var) {
		return null != var;
	}

	public static boolean empty(Object var) {
		return null == var;
	}

	public static boolean isNotBlank(Object var) {
		return null != var;
	}

	public static boolean empty(File file) {
		return null == file || !file.exists();
	}

	public static boolean notEmpty(File file) {
		return null != file && file.exists();
	}

	public static boolean empty(Object[] var) {
		return null == var || 0 == var.length;
	}

	public static boolean notEmpty(Object[] var) {
		return null != var && 0 < var.length;
	}

	public static boolean isValidEmail(String email) {
		Pattern pat = Pattern.compile(EMAIL_REGEX);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	}
	
	public static String convertToWritableFileName(String fileName) {
		return fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
	}
}
