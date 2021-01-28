package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.common.utils.SizeUtils;
import com.beanframework.core.data.ImexDto;
import com.beanframework.core.data.MediaDto;
import com.beanframework.imex.domain.Imex;
import com.beanframework.media.domain.Media;


public class ImexPopulator extends AbstractPopulator<Imex, ImexDto> implements Populator<Imex, ImexDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(ImexPopulator.class);

	@Override
	public void populate(Imex source, ImexDto target) throws PopulatorException {
		try {
			populateCommon(source, target);
			target.setType(source.getType());
			target.setDirectory(source.getDirectory());
			target.setFileName(source.getFileName());
			target.setQuery(source.getQuery());
			target.setHeader(source.getHeader());
			target.setSeperator(source.getSeperator());
			for (Media media : source.getMedias()) {
				target.getMedias().add(populateMedia(media));
			}
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}
	
	public MediaDto populateMedia(Media source) throws PopulatorException {
		if (source == null)
			return null;

		try {
			MediaDto target = new MediaDto();
			target.setUuid(source.getUuid());
			target.setId(source.getId());
			target.setCreatedDate(source.getCreatedDate());
			target.setLastModifiedDate(source.getLastModifiedDate());
			target.setCreatedBy(populateAuditor(source.getCreatedBy()));
			target.setLastModifiedBy(populateAuditor(source.getLastModifiedBy()));

			target.setFileName(source.getFileName());
			target.setFileType(source.getFileType());
			target.setFileSize(source.getFileSize() == null ? null : SizeUtils.humanReadableByteCount(source.getFileSize(), true));
			target.setTitle(source.getTitle());
			target.setCaption(source.getCaption());
			target.setAltText(source.getAltText());
			target.setDescription(source.getDescription());
			target.setUrl(source.getUrl());
			target.setFolder(source.getFolder());

			return target;
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
