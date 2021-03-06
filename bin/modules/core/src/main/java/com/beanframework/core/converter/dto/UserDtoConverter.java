package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.UserDto;
import com.beanframework.user.domain.User;

public class UserDtoConverter extends AbstractDtoConverter<User, UserDto>
    implements DtoConverter<User, UserDto> {

  protected static Logger LOGGER = LoggerFactory.getLogger(UserDtoConverter.class);

  @Override
  public UserDto convert(User source) throws ConverterException {
    return super.convert(source, new UserDto());
  }

}
