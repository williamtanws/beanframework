package com.beanframework.core.bean;

import org.springframework.beans.factory.annotation.Autowired;
import com.beanframework.common.service.LocaleMessageService;

public class LocaleMessageBeanImpl implements LocaleMessageBean {

  @Autowired
  private LocaleMessageService localeMessageService;

  @Override
  public String get(String code) throws Exception {
    return localeMessageService.getMessage(code);
  }

  @Override
  public String get(String code, Object[] args) throws Exception {
    return localeMessageService.getMessage(code, args);
  }

}
