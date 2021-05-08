package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.data.AuditorDto;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.config.dto.RevisionsDto;
import com.beanframework.user.domain.RevisionsEntity;

public class RevisionsDtoConverter {

	protected static Logger LOGGER = LoggerFactory.getLogger(RevisionsDtoConverter.class);
	
	@Autowired
	private ModelService modelService;

	public RevisionsDto convert(RevisionsEntity source) throws ConverterException {
		try {
			RevisionsDto target = new RevisionsDto();
			populateRevisions(source, target);
			return target;
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}
	
	public void populateRevisions(RevisionsEntity source, RevisionsDto target) throws Exception {
		target.setId(source.getId());
		target.setLastModifiedBy(modelService.getDto(source.getLastModifiedBy(), AuditorDto.class));
		target.setTimestamp(source.getTimestamp());
	}
}
