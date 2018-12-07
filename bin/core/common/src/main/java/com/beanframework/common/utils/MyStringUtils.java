package com.beanframework.common.utils;

import java.util.regex.Pattern;

public class MyStringUtils extends CommonUtils {
    public static final Pattern HTML_PATTERN = Pattern.compile("<[^>]+>");

    public static String removeHtmlTag(String string) {
        if (notEmpty(string)) {
            return HTML_PATTERN.matcher(string).replaceAll(BLANK);
        }
        return string;
    }
}
