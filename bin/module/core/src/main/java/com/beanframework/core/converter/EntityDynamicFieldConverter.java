package com.beanframework.core.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.EntityConverterContext;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.common.utils.BooleanUtils;
import com.beanframework.core.data.DynamicFieldDto;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.enumuration.domain.Enumeration;
import com.beanframework.language.domain.Language;

public class EntityDynamicFieldConverter implements EntityConverter<DynamicFieldDto, DynamicField> {

	@Autowired
	private ModelService modelService;

	@Override
	public DynamicField convert(DynamicFieldDto source, EntityConverterContext context) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(DynamicField.UUID, source.getUuid());
				DynamicField prototype = modelService.findOneEntityByProperties(properties, DynamicField.class);

				if (prototype != null) {
					return convertDto(source, prototype);
				}
			}

			return convertDto(source, modelService.create(DynamicField.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private DynamicField convertDto(DynamicFieldDto source, DynamicField prototype) throws ConverterException {

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

			if (source.getRequired() == null) {
				if (prototype.getRequired() != null) {
					prototype.setRequired(null);
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			} else {
				if (prototype.getRequired() == null || prototype.getRequired().equals(source.getRequired()) == false) {
					prototype.setRequired(source.getRequired());
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getRule()), prototype.getRule()) == false) {
				prototype.setRule(StringUtils.stripToNull(source.getRule()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (source.getType() == source.getType() == false) {
				prototype.setType(source.getType());
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getLabel()), prototype.getLabel()) == false) {
				prototype.setLabel(StringUtils.stripToNull(source.getLabel()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}
			
			if (StringUtils.equals(StringUtils.stripToNull(source.getGrid()), prototype.getGrid()) == false) {
				prototype.setGrid(StringUtils.stripToNull(source.getGrid()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			// Language
			if (StringUtils.isBlank(source.getTableSelectedLanguage())) {
				prototype.setLanguage(null);
				prototype.setLastModifiedDate(lastModifiedDate);
			} else {
				Language entityLanguage = modelService.findOneEntityByUuid(UUID.fromString(source.getTableSelectedLanguage()), Language.class);

				if (entityLanguage != null) {

					if (prototype.getLanguage() == null || prototype.getLanguage().getUuid().equals(entityLanguage.getUuid()) == false) {
						prototype.setLanguage(entityLanguage);
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				}
			}

			// Enumeration
			if (source.getTableEnumerations() != null) {

				for (int i = 0; i < source.getTableEnumerations().length; i++) {

					boolean remove = true;
					if (source.getTableSelectedEnumerations() != null && source.getTableSelectedEnumerations().length > i && BooleanUtils.parseBoolean(source.getTableSelectedEnumerations()[i])) {
						remove = false;
					}

					if (remove) {
						for (Iterator<Enumeration> enumerationsIterator = prototype.getEnumerations().listIterator(); enumerationsIterator.hasNext();) {
							if (enumerationsIterator.next().getUuid().equals(UUID.fromString(source.getTableEnumerations()[i]))) {
								enumerationsIterator.remove();
								prototype.setLastModifiedDate(lastModifiedDate);
							}
						}
					} else {
						boolean add = true;
						for (Iterator<Enumeration> enumerationsIterator = prototype.getEnumerations().listIterator(); enumerationsIterator.hasNext();) {
							if (enumerationsIterator.next().getUuid().equals(UUID.fromString(source.getTableEnumerations()[i]))) {
								add = false;
							}
						}

						if (add) {
							Enumeration entityEnumerations = modelService.findOneEntityByUuid(UUID.fromString(source.getTableEnumerations()[i]), Enumeration.class);
							prototype.getEnumerations().add(entityEnumerations);
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
