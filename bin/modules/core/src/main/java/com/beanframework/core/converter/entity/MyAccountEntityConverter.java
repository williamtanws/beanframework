package com.beanframework.core.converter.entity;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.MyAccountDto;
import com.beanframework.user.domain.User;

@Component
public class MyAccountEntityConverter implements EntityConverter<MyAccountDto, User> {

  @Autowired
  private ModelService modelService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public User convert(MyAccountDto source) throws ConverterException {

    try {

      if (source.getUuid() != null) {
        User prototype = modelService.findOneByUuid(source.getUuid(), User.class);
        return convertToEntity(source, prototype);
      }

      return convertToEntity(source, modelService.create(User.class));

    } catch (Exception e) {
      throw new ConverterException(e.getMessage(), e);
    }

  }

  private User convertToEntity(MyAccountDto source, User prototype) throws ConverterException {

    try {

      if (StringUtils.equals(StringUtils.stripToNull(source.getId()),
          prototype.getId()) == Boolean.FALSE) {
        prototype.setId(StringUtils.stripToNull(source.getId()));
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getName()),
          prototype.getName()) == Boolean.FALSE) {
        prototype.setName(StringUtils.stripToNull(source.getName()));
      }

      if (StringUtils.isNotBlank(source.getPassword())) {
        prototype.setPassword(passwordEncoder.encode(source.getPassword()));
      }
    } catch (Exception e) {
      throw new ConverterException(e.getMessage(), e);
    }

    return prototype;
  }

}
