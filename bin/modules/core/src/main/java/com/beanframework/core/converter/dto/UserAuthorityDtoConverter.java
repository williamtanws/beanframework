package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.UserAuthorityDto;
import com.beanframework.user.domain.UserAuthority;

public class UserAuthorityDtoConverter extends AbstractDtoConverter<UserAuthority, UserAuthorityDto>
    implements DtoConverter<UserAuthority, UserAuthorityDto> {

  protected static Logger LOGGER = LoggerFactory.getLogger(UserAuthorityDtoConverter.class);

  @Override
  public UserAuthorityDto convert(UserAuthority source) throws ConverterException {
    return super.convert(source, new UserAuthorityDto());
  }

}
