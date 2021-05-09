package com.beanframework.core.converter.populator;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.ImexDto;
import com.beanframework.imex.domain.Imex;

@Component
public class ImexPopulator extends AbstractPopulator<Imex, ImexDto> implements Populator<Imex, ImexDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(ImexPopulator.class);

	@Override
	public void populate(Imex source, ImexDto target) throws PopulatorException {
		try {
			populateGeneric(source, target);
			target.setType(source.getType());
			target.setDirectory(source.getDirectory());
			target.setFileName(source.getFileName());
			target.setQuery(source.getQuery());
			target.setHeader(source.getHeader());
			target.setSeperator(source.getSeperator());
			for (UUID uuid : source.getMedias()) {
				target.getMedias().add(populateMedia(uuid));
			}
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}
}
