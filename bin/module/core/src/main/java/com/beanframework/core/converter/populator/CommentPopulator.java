package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.comment.domain.Comment;
import com.beanframework.common.context.ConvertRelationType;
import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.AbstractPopulator;
import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.CommentDto;
import com.beanframework.core.data.UserDto;

@Component
public class CommentPopulator extends AbstractPopulator<Comment, CommentDto> implements Populator<Comment, CommentDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(CommentPopulator.class);

	@Override
	public void populate(Comment source, CommentDto target) throws PopulatorException {
		convertCommonProperties(source, target);
		prototype.setHtml(source.getHtml());
		prototype.setVisibled(source.getVisibled());
		prototype.setUser(modelService.getDto(source.getUser(), UserDto.class, new DtoConverterContext(ConvertRelationType.BASIC)));
	}

}
