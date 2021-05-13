package com.beanframework.common.utils;

import java.util.regex.Pattern;

public class DomainUtils {

  private static Pattern pDomainNameOnly;
  private static final String DOMAIN_NAME_PATTERN =
      "^((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}$";

  static {
    pDomainNameOnly = Pattern.compile(DOMAIN_NAME_PATTERN);
  }

  public static boolean isValidDomainName(String domainName) {
    return pDomainNameOnly.matcher(domainName).find();
  }

}
