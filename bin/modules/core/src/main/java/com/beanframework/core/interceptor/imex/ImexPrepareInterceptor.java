package com.beanframework.core.interceptor.imex;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractPrepareInterceptor;
import com.beanframework.imex.domain.Imex;

public class ImexPrepareInterceptor extends AbstractPrepareInterceptor<Imex> {

  @Override
  public void onPrepare(Imex model, InterceptorContext context) throws InterceptorException {}

}
