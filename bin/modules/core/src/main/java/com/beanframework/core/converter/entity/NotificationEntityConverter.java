package com.beanframework.core.converter.entity;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.NotificationDto;
import com.beanframework.notification.domain.Notification;

@Component
public class NotificationEntityConverter implements EntityConverter<NotificationDto, Notification> {

  @Autowired
  private ModelService modelService;

  @Override
  public Notification convert(NotificationDto source) throws ConverterException {

    try {

      if (source.getUuid() != null) {
        Notification prototype = modelService.findOneByUuid(source.getUuid(), Notification.class);
        return convertToEntity(source, prototype);
      }

      return convertToEntity(source, modelService.create(Notification.class));

    } catch (Exception e) {
      throw new ConverterException(e.getMessage(), e);
    }
  }

  private Notification convertToEntity(NotificationDto source, Notification prototype) {

    if (StringUtils.equals(StringUtils.stripToNull(source.getType()),
        prototype.getType()) == Boolean.FALSE) {
      prototype.setType(StringUtils.stripToNull(source.getType()));
    }

    if (StringUtils.equals(StringUtils.stripToNull(source.getMessage()),
        prototype.getMessage()) == Boolean.FALSE) {
      prototype.setMessage(StringUtils.stripToNull(source.getMessage()));
    }

    if (source.getParameters().equals(prototype.getParameters()) == false) {
      prototype.setParameters(prototype.getParameters());
    }

    return prototype;
  }

}
