package com.beanframework.core.interceptor.internationalization;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.internationalization.domain.Language;

public class LanguageLoadInterceptor extends AbstractLoadInterceptor<Language> {

  @Override
  public void onLoad(Language model, InterceptorContext context) throws InterceptorException {

  }

}
