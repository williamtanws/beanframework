package com.beanframework.core.interceptor.cms.site;

import com.beanframework.cms.domain.Site;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractPrepareInterceptor;

public class SitePrepareInterceptor extends AbstractPrepareInterceptor<Site> {

  @Override
  public void onPrepare(Site model, InterceptorContext context) throws InterceptorException {
    super.onPrepare(model, context);

  }

}
