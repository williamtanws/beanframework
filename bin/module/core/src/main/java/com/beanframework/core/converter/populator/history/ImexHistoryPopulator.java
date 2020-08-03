package com.beanframework.core.converter.populator.history;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.converter.populator.AbstractPopulator;
import com.beanframework.core.data.ImexDto;
import com.beanframework.core.data.MediaDto;
import com.beanframework.imex.domain.Imex;

@Component
public class ImexHistoryPopulator extends AbstractPopulator<Imex, ImexDto> implements Populator<Imex, ImexDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(ImexHistoryPopulator.class);

	@Autowired
	private MediaHistoryPopulator mediaHistoryPopulator;

	@Override
	public void populate(Imex source, ImexDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
			target.setType(source.getType());
			target.setDirectory(source.getDirectory());
			target.setFileName(source.getFileName());
			target.setQuery(source.getQuery());
			target.setHeader(source.getHeader());
			target.setSeperator(source.getSeperator());
			target.setMedias(modelService.getDto(source.getMedias(), MediaDto.class, new DtoConverterContext(mediaHistoryPopulator)));
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
