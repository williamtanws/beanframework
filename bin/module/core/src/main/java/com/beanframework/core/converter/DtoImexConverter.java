package com.beanframework.core.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.context.ConvertRelationType;
import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.core.data.ImexDto;
import com.beanframework.core.data.MediaDto;
import com.beanframework.imex.domain.Imex;

public class DtoImexConverter extends AbstractDtoConverter<Imex, ImexDto> implements DtoConverter<Imex, ImexDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoImexConverter.class);

	@Override
	public ImexDto convert(Imex source, DtoConverterContext context) throws ConverterException {
		return convert(source, new ImexDto(), context);
	}

	public List<ImexDto> convert(List<Imex> sources, DtoConverterContext context) throws ConverterException {
		List<ImexDto> convertedList = new ArrayList<ImexDto>();
		try {
			for (Imex source : sources) {
				convertedList.add(convert(source, context));
			}
		} catch (ConverterException e) {
			throw new ConverterException(e.getMessage(), e);
		}
		return convertedList;
	}

	private ImexDto convert(Imex source, ImexDto prototype, DtoConverterContext context) throws ConverterException {
		try {
			convertCommonProperties(source, prototype, context);

			prototype.setType(source.getType());
			prototype.setDirectory(source.getDirectory());
			prototype.setFileName(source.getFileName());
			prototype.setQuery(source.getQuery());
			prototype.setHeader(source.getHeader());
			prototype.setSeperator(source.getSeperator());

			if (ConvertRelationType.ALL == context.getConverModelType()) {
				convertAll(source, prototype, context);
			} else if (ConvertRelationType.BASIC == context.getConverModelType()) {
				convertRelation(source, prototype, context);
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

	private void convertAll(Imex source, ImexDto prototype, DtoConverterContext context) throws Exception {
		prototype.setMedias(modelService.getDto(source.getMedias(), MediaDto.class));
	}

	private void convertRelation(Imex source, ImexDto prototype, DtoConverterContext context) throws Exception {
	}

}
