package com.beanframework.common.exception;

import com.beanframework.common.converter.Populator;

public class PopulatorException extends BusinessException {

  /**
   * 
   */
  private static final long serialVersionUID = 3284956082085211152L;
  private Populator<?, ?> populator;

  public PopulatorException(String message) {
    this(message, (Throwable) null, (Populator<?, ?>) null);
  }

  public PopulatorException(String message, Throwable cause) {
    this(message, cause, (Populator<?, ?>) null);
  }

  public PopulatorException(String message, Populator<?, ?> populator) {
    this(message, (Throwable) null, populator);
  }

  public PopulatorException(String message, Throwable cause, Populator<?, ?> populator) {
    super(message, cause);
    this.setPopulator(populator);
  }

  public Populator<?, ?> getPopulator() {
    return populator;
  }

  public void setPopulator(Populator<?, ?> populator) {
    this.populator = populator;
  }

  public String getMessage() {
    return "[" + this.populator + "]:" + super.getMessage();
  }
}
