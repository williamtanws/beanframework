package com.beanframework.common.interceptor;

import org.springframework.core.Ordered;

public class InterceptorMapping implements Ordered {
  private String typeCode;
  private Interceptor interceptor;
  private int order = Integer.MAX_VALUE;

  public InterceptorMapping() {}

  public InterceptorMapping(String typeCode, Interceptor interceptor) {
    this.typeCode = typeCode;
    this.interceptor = interceptor;
  }

  public Interceptor getInterceptor() {
    return this.interceptor;
  }

  public void setInterceptor(Interceptor interceptor) {
    this.interceptor = interceptor;
  }

  public String getTypeCode() {
    return this.typeCode;
  }

  public void setTypeCode(String typeCode) {
    this.typeCode = typeCode;
  }

  public final void setOrder(int order) {
    this.order = order;
  }

  public final int getOrder() {
    return this.order;
  }
}
