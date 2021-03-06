package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.beanframework.cms.domain.Comment;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.CommentDto;

public class CommentDtoConverter extends AbstractDtoConverter<Comment, CommentDto>
    implements DtoConverter<Comment, CommentDto> {

  protected static Logger LOGGER = LoggerFactory.getLogger(CommentDtoConverter.class);

  @Override
  public CommentDto convert(Comment source) throws ConverterException {
    return super.convert(source, new CommentDto());
  }

}
