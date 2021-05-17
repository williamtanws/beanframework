package com.beanframework.core.converter.entity;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.beanframework.cms.domain.Comment;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.CommentDto;
import com.beanframework.user.domain.User;
import com.beanframework.user.service.UserService;

@Component
public class CommentEntityConverter implements EntityConverter<CommentDto, Comment> {

  @Autowired
  private ModelService modelService;

  @Autowired
  private UserService userService;

  @Override
  public Comment convert(CommentDto source) throws ConverterException {

    try {

      if (source.getUuid() != null) {
        Comment prototype = modelService.findOneByUuid(source.getUuid(), Comment.class);
        return convertToEntity(source, prototype);
      }

      return convertToEntity(source, modelService.create(Comment.class));

    } catch (Exception e) {
      throw new ConverterException(e.getMessage(), e);
    }
  }

  private Comment convertToEntity(CommentDto source, Comment prototype) throws ConverterException {

    try {

      if (StringUtils.equals(StringUtils.stripToNull(source.getId()),
          prototype.getId()) == Boolean.FALSE) {
        prototype.setId(StringUtils.stripToNull(source.getId()));
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getHtml()),
          prototype.getHtml()) == Boolean.FALSE) {
        prototype.setHtml(StringUtils.stripToNull(source.getHtml()));
      }

      if (prototype.getVisibled() == source.getVisibled() == Boolean.FALSE) {
        prototype.setVisibled(source.getVisibled());
      }

      if (prototype.getUser() == null && source.getUser() == null) {
        prototype.setUser(userService.getCurrentUserSession().getUuid());
      } else if (prototype.getUser().equals(source.getUser().getUuid()) == Boolean.FALSE) {
        User entityUser = modelService.findOneByUuid(source.getUser().getUuid(), User.class);

        if (entityUser != null) {
          prototype.setUser(entityUser.getUuid());
        } else {
          throw new ConverterException("User UUID not found: " + source.getUser().getUuid());
        }
      }

    } catch (Exception e) {
      throw new ConverterException(e.getMessage(), e);
    }

    return prototype;
  }

}
