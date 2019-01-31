package com.beanframework.console.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.admin.domain.Admin;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.converter.ModelAction;
import com.beanframework.common.data.AuditorDto;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.AdminDto;

public class DtoAdminConverter implements DtoConverter<Admin, AdminDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoAdminConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public AdminDto convert(Admin source, ModelAction action) throws ConverterException {
		return convert(source, new AdminDto(), action);
	}

	public List<AdminDto> convert(List<Admin> sources, ModelAction action) throws ConverterException {
		List<AdminDto> convertedList = new ArrayList<AdminDto>();
		try {
			for (Admin source : sources) {
				convertedList.add(convert(source, action));
			}
		} catch (ConverterException e) {
			throw new ConverterException(e.getMessage(), e);
		}
		return convertedList;
	}

	private AdminDto convert(Admin source, AdminDto prototype, ModelAction action) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setAccountNonExpired(source.getAccountNonExpired());
		prototype.setAccountNonLocked(source.getAccountNonLocked());
		prototype.setCredentialsNonExpired(source.getCredentialsNonExpired());
		prototype.setEnabled(source.getEnabled());
		prototype.setName(source.getName());
		
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
