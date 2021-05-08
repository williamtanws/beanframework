package com.beanframework.core.converter.entity;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.DynamicFieldSlotDto;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;

public class DynamicFieldSlotEntityConverter implements EntityConverter<DynamicFieldSlotDto, DynamicFieldSlot> {

	@Autowired
	private ModelService modelService;

	@Override
	public DynamicFieldSlot convert(DynamicFieldSlotDto source) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				DynamicFieldSlot prototype = modelService.findOneByUuid(source.getUuid(), DynamicFieldSlot.class);

				if (prototype != null) {
					return convertToEntity(source, prototype);
				}
			}

			return convertToEntity(source, modelService.create(DynamicFieldSlot.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private DynamicFieldSlot convertToEntity(DynamicFieldSlotDto source, DynamicFieldSlot prototype) throws ConverterException {

		try {

			Date lastModifiedDate = new Date();

			if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == Boolean.FALSE) {
				prototype.setId(StringUtils.stripToNull(source.getId()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getName()), prototype.getName()) == Boolean.FALSE) {
				prototype.setName(StringUtils.stripToNull(source.getName()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (source.getSort() == null) {
				if (prototype.getSort() != null) {
					prototype.setSort(null);
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			} else {
				if (prototype.getSort() == null || prototype.getSort() == source.getSort() == Boolean.FALSE) {
					prototype.setSort(source.getSort());
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			}

			// Dynamic Field
			if (StringUtils.isBlank(source.getSelectedDynamicFieldUuid())) {
				prototype.setDynamicField(null);
				prototype.setLastModifiedDate(lastModifiedDate);
			} else {
				DynamicField entityDynamicField = modelService.findOneByUuid(UUID.fromString(source.getSelectedDynamicFieldUuid()), DynamicField.class);

				if (entityDynamicField != null) {

					if (prototype.getDynamicField() == null || prototype.getDynamicField().equals(entityDynamicField.getUuid()) == Boolean.FALSE) {
						prototype.setDynamicField(entityDynamicField.getUuid());
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				}
			}

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
