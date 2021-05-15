package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.beanframework.cms.domain.Comment;
import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.CommentDto;

@Component
public class CommentPopulator extends AbstractPopulator<Comment, CommentDto>
    implements Populator<Comment, CommentDto> {

  protected static Logger LOGGER = LoggerFactory.getLogger(CommentPopulator.class);

  @Override
  public void populate(Comment source, CommentDto target) throws PopulatorException {
    try {
      populateGeneric(source, target);
      target.setHtml(source.getHtml());
      target.setVisibled(source.getVisibled());
      target.setUser(populateUser(source.getUser()));
    } catch (Exception e) {
      throw new PopulatorException(e.getMessage(), e);
    }
  }

}
