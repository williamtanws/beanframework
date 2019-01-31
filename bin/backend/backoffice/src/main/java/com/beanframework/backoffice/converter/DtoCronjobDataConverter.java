package com.beanframework.backoffice.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.converter.ModelAction;
import com.beanframework.common.data.AuditorDto;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.CronjobDataDto;
import com.beanframework.cronjob.domain.CronjobData;

public class DtoCronjobDataConverter implements DtoConverter<CronjobData, CronjobDataDto> {
	
	protected static Logger LOGGER = LoggerFactory.getLogger(DtoCronjobDataConverter.class);
	
	@Autowired
	private ModelService modelService;

	@Override
	public CronjobDataDto convert(CronjobData source, ModelAction action) throws ConverterException {
		return convert(source, new CronjobDataDto(), action);
	}

	public List<CronjobDataDto> convert(List<CronjobData> sources, ModelAction action) throws ConverterException {
		List<CronjobDataDto> convertedList = new ArrayList<CronjobDataDto>();
		for (CronjobData source : sources) {
			convertedList.add(convert(source, action));
		}
		return convertedList;
	}

	private CronjobDataDto convert(CronjobData source, CronjobDataDto prototype, ModelAction action) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setName(source.getName());
		prototype.setValue(source.getValue());
		
		try {
			ModelAction disableInitialCollectionAction = new ModelAction();
			disableInitialCollectionAction.setInitializeCollection(false);

			prototype.setCreatedBy(modelService.getDto(source.getCreatedBy(), disableInitialCollectionAction, AuditorDto.class));
			prototype.setLastModifiedBy(modelService.getDto(source.getLastModifiedBy(), disableInitialCollectionAction, AuditorDto.class));
		
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}


		return prototype;
	}

}
