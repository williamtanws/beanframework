package com.beanframework.common.utils;

import static org.apache.commons.logging.LogFactory.getLog;

import java.io.File;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;

public abstract class CommonUtils {
    public static final Random r = new Random();
    public static final String DEFAULT_CHARSET = "UTF-8";
    public static final String SEPARATOR = "/";
    public static final String BLANK = "";
    protected final Log log = getLog(getClass());

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
    
    public static boolean isNotEmpty(Object var){
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
}
