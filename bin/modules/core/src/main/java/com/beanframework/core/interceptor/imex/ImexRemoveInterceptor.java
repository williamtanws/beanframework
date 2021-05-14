package com.beanframework.core.interceptor.imex;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.imex.domain.Imex;

public class ImexRemoveInterceptor extends AbstractRemoveInterceptor<Imex> {

  @Override
  public void onRemove(Imex model, InterceptorContext context) throws InterceptorException {}

}
