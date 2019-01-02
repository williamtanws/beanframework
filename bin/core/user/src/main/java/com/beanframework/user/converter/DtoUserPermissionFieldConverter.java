package com.beanframework.user.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.user.domain.UserPermissionField;

public class DtoUserPermissionFieldConverter implements DtoConverter<UserPermissionField, UserPermissionField> {
	
	protected static Logger LOGGER = LoggerFactory.getLogger(DtoUserPermissionFieldConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public UserPermissionField convert(UserPermissionField source) throws ConverterException {
		return convert(source, new UserPermissionField());
	}

	public List<UserPermissionField> convert(List<UserPermissionField> sources) throws ConverterException {
		List<UserPermissionField> convertedList = new ArrayList<UserPermissionField>();
		for (UserPermissionField source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	public UserPermissionField convert(UserPermissionField source, UserPermissionField prototype)
			throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setValue(source.getValue());
		try {			
			prototype.setDynamicField(modelService.getDto(source.getDynamicField(), DynamicField.class));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
