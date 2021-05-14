package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.UserRightDto;
import com.beanframework.user.domain.UserRight;

public class UserRightDtoConverter extends AbstractDtoConverter<UserRight, UserRightDto>
    implements DtoConverter<UserRight, UserRightDto> {

  protected static Logger LOGGER = LoggerFactory.getLogger(UserRightDtoConverter.class);

  @Override
  public UserRightDto convert(UserRight source) throws ConverterException {
    return super.convert(source, new UserRightDto());
  }
}
