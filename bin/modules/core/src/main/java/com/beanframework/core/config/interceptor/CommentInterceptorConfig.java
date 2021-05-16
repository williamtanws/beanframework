package com.beanframework.core.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.beanframework.cms.domain.Comment;
import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.core.interceptor.comment.CommentInitialDefaultsInterceptor;
import com.beanframework.core.interceptor.comment.CommentPrepareInterceptor;

@Configuration
public class CommentInterceptorConfig {

  //////////////////////////////////
  // Initial Defaults Interceptor //
  //////////////////////////////////

  @Bean
  public CommentInitialDefaultsInterceptor commentInitialDefaultsInterceptor() {
    return new CommentInitialDefaultsInterceptor();
  }

  @Bean
  public InterceptorMapping commentInitialDefaultsInterceptorMapping() {
    InterceptorMapping mapping = new InterceptorMapping();
    mapping.setInterceptor(commentInitialDefaultsInterceptor());
    mapping.setTypeCode(Comment.class.getSimpleName());

    return mapping;
  }

  /////////////////////////
  // Prepare Interceptor //
  /////////////////////////

  @Bean
  public CommentPrepareInterceptor commentPrepareInterceptor() {
    return new CommentPrepareInterceptor();
  }

  @Bean
  public InterceptorMapping commentPrepareInterceptorMapping() {
    InterceptorMapping mapping = new InterceptorMapping();
    mapping.setInterceptor(commentPrepareInterceptor());
    mapping.setTypeCode(Comment.class.getSimpleName());

    return mapping;
  }
}
