package com.beanframework.core.converter.entity;

import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.DynamicFieldDto;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.enumuration.domain.Enumeration;
import com.beanframework.internationalization.domain.Language;

public class DynamicFieldEntityConverter implements EntityConverter<DynamicFieldDto, DynamicField> {

	@Autowired
	private ModelService modelService;

	@Override
	public DynamicField convert(DynamicFieldDto source) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				DynamicField prototype = modelService.findOneByUuid(source.getUuid(), DynamicField.class);

				if (prototype != null) {
					return convertToEntity(source, prototype);
				}
			}

			return convertToEntity(source, modelService.create(DynamicField.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private DynamicField convertToEntity(DynamicFieldDto source, DynamicField prototype) throws ConverterException {

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

			if (source.getRequired() == null) {
				if (prototype.getRequired() != null) {
					prototype.setRequired(null);
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			} else {
				if (prototype.getRequired() == null || prototype.getRequired().equals(source.getRequired()) == Boolean.FALSE) {
					prototype.setRequired(source.getRequired());
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getRule()), prototype.getRule()) == Boolean.FALSE) {
				prototype.setRule(StringUtils.stripToNull(source.getRule()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (prototype.getType() == source.getType() == Boolean.FALSE) {
				prototype.setType(source.getType());
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getLabel()), prototype.getLabel()) == Boolean.FALSE) {
				prototype.setLabel(StringUtils.stripToNull(source.getLabel()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getGrid()), prototype.getGrid()) == Boolean.FALSE) {
				prototype.setGrid(StringUtils.stripToNull(source.getGrid()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			// Language
			if (StringUtils.isBlank(source.getSelectedLanguageUuid())) {
				prototype.setLanguage(null);
				prototype.setLastModifiedDate(lastModifiedDate);
			} else {
				Language entityLanguage = modelService.findOneByUuid(UUID.fromString(source.getSelectedLanguageUuid()), Language.class);

				if (entityLanguage != null) {

					if (prototype.getLanguage() == null || prototype.getLanguage().equals(entityLanguage.getUuid()) == Boolean.FALSE) {
						prototype.setLanguage(entityLanguage.getUuid());
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				}
			}

			// Enumerations
			if (source.getSelectedEnumerationUuids() != null) {

				Iterator<UUID> enumerationsIterator = prototype.getEnumerations().iterator();
				while (enumerationsIterator.hasNext()) {
					UUID enumeration = enumerationsIterator.next();

					boolean remove = true;
					for (int i = 0; i < source.getSelectedEnumerationUuids().length; i++) {
						if (enumeration.equals(UUID.fromString(source.getSelectedEnumerationUuids()[i]))) {
							remove = false;
						}
					}
					if (remove) {
						enumerationsIterator.remove();
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				}

				for (int i = 0; i < source.getSelectedEnumerationUuids().length; i++) {

					boolean add = true;
					enumerationsIterator = prototype.getEnumerations().iterator();
					while (enumerationsIterator.hasNext()) {
						UUID enumeration = enumerationsIterator.next();

						if (enumeration.equals(UUID.fromString(source.getSelectedEnumerationUuids()[i]))) {
							add = false;
						}
					}

					if (add) {
						Enumeration enumeration = modelService.findOneByUuid(UUID.fromString(source.getSelectedEnumerationUuids()[i]), Enumeration.class);
						if (enumeration != null) {
							prototype.getEnumerations().add(enumeration.getUuid());
							prototype.setLastModifiedDate(lastModifiedDate);
						}
					}
				}
			}
			else if(prototype.getEnumerations() != null && prototype.getEnumerations().isEmpty() == false){
				for (final Iterator<UUID> itr = prototype.getEnumerations().iterator(); itr.hasNext();) {
					itr.next();
				    itr.remove(); 
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			}

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
