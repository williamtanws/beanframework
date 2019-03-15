package com.beanframework.core.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.EntityConverterContext;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.DynamicFieldSlotDto;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;

public class EntityDynamicFieldSlotConverter implements EntityConverter<DynamicFieldSlotDto, DynamicFieldSlot> {

	@Autowired
	private ModelService modelService;

	@Override
	public DynamicFieldSlot convert(DynamicFieldSlotDto source, EntityConverterContext context) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(DynamicFieldSlot.UUID, source.getUuid());
				DynamicFieldSlot prototype = modelService.findOneEntityByProperties(properties, true, DynamicFieldSlot.class);

				if (prototype != null) {
					return convertDto(source, prototype);
				}
			}

			return convertDto(source, modelService.create(DynamicFieldSlot.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private DynamicFieldSlot convertDto(DynamicFieldSlotDto source, DynamicFieldSlot prototype) throws ConverterException {

		try {

			Date lastModifiedDate = new Date();

			if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == false) {
				prototype.setId(StringUtils.stripToNull(source.getId()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getName()), prototype.getName()) == false) {
				prototype.setName(StringUtils.stripToNull(source.getName()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (source.getSort() == null) {
				if (prototype.getSort() != null) {
					prototype.setSort(null);
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			} else {
				if (prototype.getSort() == null || prototype.getSort() == source.getSort() == false) {
					prototype.setSort(source.getSort());
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			}

			// Dynamic Field
			if (StringUtils.isBlank(source.getTableSelectedDynamicField())) {
				prototype.setDynamicField(null);
				prototype.setLastModifiedDate(lastModifiedDate);
			} else {
				DynamicField entityDynamicField = modelService.findOneEntityByUuid(UUID.fromString(source.getTableSelectedDynamicField()), false, DynamicField.class);

				if (entityDynamicField != null) {

					if (prototype.getDynamicField() == null || prototype.getDynamicField().getUuid().equals(entityDynamicField.getUuid()) == false) {
						prototype.setDynamicField(entityDynamicField);
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
