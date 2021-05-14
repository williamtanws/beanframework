package com.beanframework.core.interceptor.dynamicfieldtemplate;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;

public class DynamicFieldTemplateLoadInterceptor
    extends AbstractLoadInterceptor<DynamicFieldTemplate> {

  @Override
  public void onLoad(DynamicFieldTemplate model, InterceptorContext context)
      throws InterceptorException {}

}
