package com.beanframework.core.interceptor.internationalization;

import org.springframework.beans.factory.annotation.Autowired;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.CoreConstants;
import com.beanframework.core.specification.DynamicFieldSpecification;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.internationalization.domain.Language;

public class LanguageRemoveInterceptor extends AbstractRemoveInterceptor<Language> {

  @Autowired
  private ModelService modelService;

  @Autowired
  private LocaleMessageService localeMessageService;

  @Override
  public void onRemove(Language model, InterceptorContext context) throws InterceptorException {
    try {
      int count = modelService.countBySpecification(
          DynamicFieldSpecification.getDynamicFieldByLanguageUuid(model.getUuid()),
          DynamicField.class);
      if (count > 0) {
        throw new InterceptorException(localeMessageService
            .getMessage(CoreConstants.Locale.ERROR_DELETE_NOT_ALLOWED_REFERENCE), this);
      }
    } catch (Exception e) {
      throw new InterceptorException(e.getMessage(), e);
    }
  }

}
