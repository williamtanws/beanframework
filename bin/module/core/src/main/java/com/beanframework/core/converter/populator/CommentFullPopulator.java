package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.comment.domain.Comment;
import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.CommentDto;
import com.beanframework.core.data.UserDto;
import com.beanframework.user.domain.User;

@Component
public class CommentFullPopulator extends AbstractPopulator<Comment, CommentDto> implements Populator<Comment, CommentDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(CommentFullPopulator.class);

	@Autowired
	private UserBasicPopulator userBasicPopulator;

	@Override
	public void populate(Comment source, CommentDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
			target.setHtml(source.getHtml());
			target.setVisibled(source.getVisibled());
			if (source.getUser() != null) {
				User entity = modelService.findOneByUuid(source.getUser(), User.class);
				target.setUser(modelService.getDto(entity, UserDto.class, new DtoConverterContext(userBasicPopulator)));
			}
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
