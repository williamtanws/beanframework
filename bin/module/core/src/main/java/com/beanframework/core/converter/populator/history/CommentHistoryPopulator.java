package com.beanframework.core.converter.populator.history;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.comment.domain.Comment;
import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.converter.populator.AbstractPopulator;
import com.beanframework.core.converter.populator.UserBasicPopulator;
import com.beanframework.core.data.CommentDto;
import com.beanframework.core.data.UserDto;

@Component
public class CommentHistoryPopulator extends AbstractPopulator<Comment, CommentDto> implements Populator<Comment, CommentDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(CommentHistoryPopulator.class);

	@Autowired
	private UserBasicPopulator userBasicPopulator;

	@Override
	public void populate(Comment source, CommentDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
			target.setHtml(source.getHtml());
			target.setVisibled(source.getVisibled());
			target.setUser(modelService.getDto(source.getUser(), UserDto.class, new DtoConverterContext(userBasicPopulator)));
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
