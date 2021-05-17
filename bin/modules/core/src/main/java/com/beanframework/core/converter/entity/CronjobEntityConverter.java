package com.beanframework.core.converter.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.CronjobDto;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.user.domain.User;

@Component
public class CronjobEntityConverter implements EntityConverter<CronjobDto, Cronjob> {

  @Autowired
  private ModelService modelService;

  @Override
  public Cronjob convert(CronjobDto source) throws ConverterException {

    try {

      if (source.getUuid() != null) {
        Cronjob prototype = modelService.findOneByUuid(source.getUuid(), Cronjob.class);
        return convertToEntity(source, prototype);
      }

      return convertToEntity(source, modelService.create(Cronjob.class));

    } catch (Exception e) {
      throw new ConverterException(e.getMessage(), e);
    }

  }

  private Cronjob convertToEntity(CronjobDto source, Cronjob prototype) throws Exception {


    if (StringUtils.equals(StringUtils.stripToNull(source.getId()),
        prototype.getId()) == Boolean.FALSE) {
      prototype.setId(StringUtils.stripToNull(source.getId()));
    }

    if (StringUtils.equals(StringUtils.stripToNull(source.getJobClass()),
        prototype.getJobClass()) == Boolean.FALSE) {
      prototype.setJobClass(StringUtils.stripToNull(source.getJobClass()));
    }

    if (StringUtils.equals(StringUtils.stripToNull(source.getJobGroup()),
        prototype.getJobGroup()) == Boolean.FALSE) {
      prototype.setJobGroup(StringUtils.stripToNull(source.getJobGroup()));
    }

    if (StringUtils.equals(StringUtils.stripToNull(source.getName()),
        prototype.getName()) == Boolean.FALSE) {
      prototype.setName(StringUtils.stripToNull(source.getName()));
    }

    if (StringUtils.equals(StringUtils.stripToNull(source.getDescription()),
        prototype.getDescription()) == Boolean.FALSE) {
      prototype.setDescription(StringUtils.stripToNull(source.getDescription()));
    }

    if (StringUtils.equals(StringUtils.stripToNull(source.getCronExpression()),
        prototype.getCronExpression()) == Boolean.FALSE) {
      prototype.setCronExpression(StringUtils.stripToNull(source.getCronExpression()));
    }

    if (source.getStartup() == null) {
      if (prototype.getStartup() != null) {
        prototype.setStartup(null);
      }
    } else {
      if (prototype.getStartup() == null
          || prototype.getStartup().equals(source.getStartup()) == Boolean.FALSE) {
        prototype.setStartup(source.getStartup());
      }
    }

    if (source.getJobTrigger() != prototype.getJobTrigger()) {
      prototype.setJobTrigger(source.getJobTrigger());
    }

    if (source.getJobTrigger() != null) {
      prototype.setLastTriggeredDate(new Date());
    }

    if (source.getTriggerStartDate() != null) {
      prototype.setTriggerStartDate(source.getTriggerStartDate());
    }

    if (source.getLastFinishExecutedDate() != null) {
      prototype.setLastFinishExecutedDate(source.getLastFinishExecutedDate());

    }

    if (source.getLastStartExecutedDate() != null) {
      prototype.setLastStartExecutedDate(source.getLastStartExecutedDate());
    }

    if (source.getLastTriggeredDate() != null) {
      prototype.setLastTriggeredDate(source.getLastTriggeredDate());
    }

    if (source.getStatus() != null) {
      prototype.setStatus(source.getStatus());
    }

    // Parameters
    Map<String, String> prototypeParameters = new HashMap<String, String>();
    if (source.getSelectedParameterKeys() != null && source.getSelectedParameterValues() != null
        && source.getSelectedParameterKeys().length == source.getSelectedParameterValues().length) {
      for (int i = 0; i < source.getSelectedParameterKeys().length; i++) {
        prototypeParameters.put(source.getSelectedParameterKeys()[i],
            source.getSelectedParameterValues()[i]);
      }
    }

    if (prototypeParameters.isEmpty()) {
      prototype.getParameters().clear();
    }

    if (source.getParameters().equals(prototypeParameters) == false) {
      prototype.setParameters(prototypeParameters);
    }

    // User
    if (StringUtils.isBlank(source.getSelectedUserUuid())) {
      if (prototype.getUser() != null) {
        prototype.setUser(null);
      }
    } else {
      User entity =
          modelService.findOneByUuid(UUID.fromString(source.getSelectedUserUuid()), User.class);

      if (entity != null) {

        if (prototype.getUser() == null || prototype.getUser().equals(entity.getUuid()) == false) {
          prototype.setUser(entity.getUuid());
        }
      } else {
        throw new ConverterException("User UUID not found: " + source.getSelectedUserUuid());
      }
    }

    return prototype;
  }

}
