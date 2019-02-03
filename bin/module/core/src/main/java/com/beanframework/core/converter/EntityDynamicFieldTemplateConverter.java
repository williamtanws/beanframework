package com.beanframework.core.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.common.utils.BooleanUtils;
import com.beanframework.core.data.DynamicFieldTemplateDto;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;

public class EntityDynamicFieldTemplateConverter implements EntityConverter<DynamicFieldTemplateDto, DynamicFieldTemplate> {

	@Autowired
	private ModelService modelService;

	@Override
	public DynamicFieldTemplate convert(DynamicFieldTemplateDto source) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(DynamicFieldTemplate.UUID, source.getUuid());
				DynamicFieldTemplate prototype = modelService.findOneEntityByProperties(properties, true, DynamicFieldTemplate.class);

				if (prototype != null) {
					return convert(source, prototype);
				}
			}

			return convert(source, modelService.create(DynamicFieldTemplate.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private DynamicFieldTemplate convert(DynamicFieldTemplateDto source, DynamicFieldTemplate prototype) throws ConverterException {

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

			// DynamicField
			if (source.getTableDynamicFields() != null) {

				for (int i = 0; i < source.getTableDynamicFields().length; i++) {

					boolean remove = true;
					if (source.getTableSelectedDynamicFields() != null && source.getTableSelectedDynamicFields().length > i && BooleanUtils.parseBoolean(source.getTableSelectedDynamicFields()[i])) {
						remove = false;
					}

					if (remove) {
						for (Iterator<DynamicField> employeesIterator = prototype.getDynamicFields().listIterator(); employeesIterator.hasNext();) {
							if (employeesIterator.next().getUuid().equals(UUID.fromString(source.getTableDynamicFields()[i]))) {
								employeesIterator.remove();
								prototype.setLastModifiedDate(lastModifiedDate);
							}
						}
					} else {
						boolean add = true;
						for (Iterator<DynamicField> employeesIterator = prototype.getDynamicFields().listIterator(); employeesIterator.hasNext();) {
							if (employeesIterator.next().getUuid().equals(UUID.fromString(source.getTableDynamicFields()[i]))) {
								add = false;
							}
						}

						if (add) {
							DynamicField entityDynamicFields = modelService.findOneEntityByUuid(UUID.fromString(source.getTableDynamicFields()[i]), false, DynamicField.class);
							prototype.getDynamicFields().add(entityDynamicFields);
							prototype.setLastModifiedDate(lastModifiedDate);
						}
					}
				}
			}

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
