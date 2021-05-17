package com.beanframework.core.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.core.interceptor.user.UserInitialDefaultsInterceptor;
import com.beanframework.core.interceptor.user.UserLoadInterceptor;
import com.beanframework.core.interceptor.user.UserPrepareInterceptor;
import com.beanframework.core.interceptor.usergroup.UserGroupInitialDefaultsInterceptor;
import com.beanframework.core.interceptor.usergroup.UserGroupLoadInterceptor;
import com.beanframework.core.interceptor.usergroup.UserGroupPrepareInterceptor;
import com.beanframework.core.interceptor.usergroup.UserGroupRemoveInterceptor;
import com.beanframework.core.interceptor.usergroup.UserGroupValidateInterceptor;
import com.beanframework.core.interceptor.userpermission.UserPermissionInitialDefaultsInterceptor;
import com.beanframework.core.interceptor.userpermission.UserPermissionLoadInterceptor;
import com.beanframework.core.interceptor.userpermission.UserPermissionPrepareInterceptor;
import com.beanframework.core.interceptor.userright.UserRightInitialDefaultsInterceptor;
import com.beanframework.core.interceptor.userright.UserRightLoadInterceptor;
import com.beanframework.core.interceptor.userright.UserRightPrepareInterceptor;
import com.beanframework.user.domain.User;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserRight;

@Configuration
public class UserInterceptorConfig {

  /////////////////////////////////
  // Initial Defaults Interceptor //
  /////////////////////////////////

  @Bean
  public UserInitialDefaultsInterceptor userInitialDefaultsInterceptor() {
    return new UserInitialDefaultsInterceptor();
  }

  @Bean
  public InterceptorMapping userInitialDefaultsInterceptorMapping() {
    InterceptorMapping interceptorMapping = new InterceptorMapping();
    interceptorMapping.setInterceptor(userInitialDefaultsInterceptor());
    interceptorMapping.setTypeCode(User.class.getSimpleName());

    return interceptorMapping;
  }

  @Bean
  public UserGroupInitialDefaultsInterceptor userGroupInitialDefaultsInterceptor() {
    return new UserGroupInitialDefaultsInterceptor();
  }

  @Bean
  public InterceptorMapping userGroupInitialDefaultsInterceptorMapping() {
    InterceptorMapping interceptorMapping = new InterceptorMapping();
    interceptorMapping.setInterceptor(userGroupInitialDefaultsInterceptor());
    interceptorMapping.setTypeCode(UserGroup.class.getSimpleName());

    return interceptorMapping;
  }

  @Bean
  public UserPermissionInitialDefaultsInterceptor userPermissionInitialDefaultsInterceptor() {
    return new UserPermissionInitialDefaultsInterceptor();
  }

  @Bean
  public InterceptorMapping userPermissionInitialDefaultsInterceptorMapping() {
    InterceptorMapping interceptorMapping = new InterceptorMapping();
    interceptorMapping.setInterceptor(userPermissionInitialDefaultsInterceptor());
    interceptorMapping.setTypeCode(UserPermission.class.getSimpleName());

    return interceptorMapping;
  }

  @Bean
  public UserRightInitialDefaultsInterceptor userRightInitialDefaultsInterceptor() {
    return new UserRightInitialDefaultsInterceptor();
  }

  @Bean
  public InterceptorMapping userRightInitialDefaultsInterceptorMapping() {
    InterceptorMapping interceptorMapping = new InterceptorMapping();
    interceptorMapping.setInterceptor(userRightInitialDefaultsInterceptor());
    interceptorMapping.setTypeCode(UserRight.class.getSimpleName());

    return interceptorMapping;
  }

  //////////////////////
  // Load Interceptor //
  //////////////////////

  @Bean
  public UserLoadInterceptor userLoadInterceptor() {
    return new UserLoadInterceptor();
  }

  @Bean
  public InterceptorMapping userLoadInterceptorMapping() {
    InterceptorMapping mapping = new InterceptorMapping();
    mapping.setInterceptor(userLoadInterceptor());
    mapping.setTypeCode(User.class.getSimpleName());

    return mapping;
  }

  @Bean
  public UserGroupLoadInterceptor userGroupLoadInterceptor() {
    return new UserGroupLoadInterceptor();
  }

  @Bean
  public InterceptorMapping userGroupLoadInterceptorMapping() {
    InterceptorMapping interceptorMapping = new InterceptorMapping();
    interceptorMapping.setInterceptor(userGroupLoadInterceptor());
    interceptorMapping.setTypeCode(UserGroup.class.getSimpleName());

    return interceptorMapping;
  }

  @Bean
  public UserPermissionLoadInterceptor userPermissionLoadInterceptor() {
    return new UserPermissionLoadInterceptor();
  }

  @Bean
  public InterceptorMapping userPermissionLoadInterceptorMapping() {
    InterceptorMapping interceptorMapping = new InterceptorMapping();
    interceptorMapping.setInterceptor(userPermissionLoadInterceptor());
    interceptorMapping.setTypeCode(UserPermission.class.getSimpleName());

    return interceptorMapping;
  }

  @Bean
  public UserRightLoadInterceptor userRightLoadInterceptor() {
    return new UserRightLoadInterceptor();
  }

  @Bean
  public InterceptorMapping userRightLoadInterceptorMapping() {
    InterceptorMapping interceptorMapping = new InterceptorMapping();
    interceptorMapping.setInterceptor(userRightLoadInterceptor());
    interceptorMapping.setTypeCode(UserRight.class.getSimpleName());

    return interceptorMapping;
  }

  /////////////////////////
  // Prepare Interceptor //
  /////////////////////////

  @Bean
  public UserPrepareInterceptor userPrepareInterceptor() {
    return new UserPrepareInterceptor();
  }

  @Bean
  public InterceptorMapping userPrepareInterceptorMapping() {
    InterceptorMapping interceptorMapping = new InterceptorMapping();
    interceptorMapping.setInterceptor(userPrepareInterceptor());
    interceptorMapping.setTypeCode(User.class.getSimpleName());

    return interceptorMapping;
  }

  @Bean
  public UserGroupPrepareInterceptor userGroupPrepareInterceptor() {
    return new UserGroupPrepareInterceptor();
  }

  @Bean
  public InterceptorMapping userGroupPrepareInterceptorMapping() {
    InterceptorMapping interceptorMapping = new InterceptorMapping();
    interceptorMapping.setInterceptor(userGroupPrepareInterceptor());
    interceptorMapping.setTypeCode(UserGroup.class.getSimpleName());

    return interceptorMapping;
  }

  @Bean
  public UserPermissionPrepareInterceptor userPermissionPrepareInterceptor() {
    return new UserPermissionPrepareInterceptor();
  }

  @Bean
  public InterceptorMapping userPermissionPrepareInterceptorMapping() {
    InterceptorMapping interceptorMapping = new InterceptorMapping();
    interceptorMapping.setInterceptor(userPermissionPrepareInterceptor());
    interceptorMapping.setTypeCode(UserPermission.class.getSimpleName());

    return interceptorMapping;
  }

  @Bean
  public UserRightPrepareInterceptor userRightPrepareInterceptor() {
    return new UserRightPrepareInterceptor();
  }

  @Bean
  public InterceptorMapping userRightPrepareInterceptorMapping() {
    InterceptorMapping interceptorMapping = new InterceptorMapping();
    interceptorMapping.setInterceptor(userRightPrepareInterceptor());
    interceptorMapping.setTypeCode(UserRight.class.getSimpleName());

    return interceptorMapping;
  }

  //////////////////////////
  // Validate Interceptor //
  //////////////////////////

  @Bean
  public UserGroupValidateInterceptor userGroupValidateInterceptor() {
    return new UserGroupValidateInterceptor();
  }

  @Bean
  public InterceptorMapping userGroupValidateInterceptorMapping() {
    InterceptorMapping interceptorMapping = new InterceptorMapping();
    interceptorMapping.setInterceptor(userGroupValidateInterceptor());
    interceptorMapping.setTypeCode(UserGroup.class.getSimpleName());

    return interceptorMapping;
  }

  ////////////////////////
  // Remove Interceptor //
  ////////////////////////

  @Bean
  public UserGroupRemoveInterceptor userGroupRemoveInterceptor() {
    return new UserGroupRemoveInterceptor();
  }

  @Bean
  public InterceptorMapping userGroupRemoveInterceptorMapping() {
    InterceptorMapping interceptorMapping = new InterceptorMapping();
    interceptorMapping.setInterceptor(userGroupRemoveInterceptor());
    interceptorMapping.setTypeCode(UserGroup.class.getSimpleName());

    return interceptorMapping;
  }
}
