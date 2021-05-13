package com.beanframework.backoffice.ckfinder;

import com.cksource.ckfinder.config.Config;

public class CustomConfig extends Config {
  /**
   * 
   */
  private static final long serialVersionUID = -6798003813288292679L;

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  private boolean enabled = false;
}
