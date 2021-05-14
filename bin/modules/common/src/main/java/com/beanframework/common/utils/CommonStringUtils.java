package com.beanframework.common.utils;

import java.util.regex.Pattern;

public class CommonStringUtils extends CommonUtils {
  public static final Pattern HTML_PATTERN = Pattern.compile("<[^>]+>");

  public static String removeHtmlTag(String string) {
    if (notEmpty(string)) {
      return HTML_PATTERN.matcher(string).replaceAll(BLANK);
    }
    return string;
  }

  public static boolean isStringInt(String s) {
    try {
      Integer.parseInt(s);
      return true;
    } catch (NumberFormatException ex) {
      return false;
    }
  }
}
