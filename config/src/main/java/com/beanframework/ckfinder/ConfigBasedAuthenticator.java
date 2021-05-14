package com.beanframework.ckfinder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import com.cksource.ckfinder.authentication.Authenticator;

/**
 * WARNING: Your authenticator should never simply return true. By doing so, you are allowing
 * "anyone" to upload and list the files on your server. You should implement some kind of session
 * validation mechanism to make sure that only trusted users can upload or delete your files.
 */
@Component
public class ConfigBasedAuthenticator implements Authenticator {
  @Autowired
  private ApplicationContext applicationContext;

  @Override
  public boolean authenticate() {
    CustomConfig config = applicationContext.getBean(CustomConfig.class);

    return config.isEnabled();
  }
}
