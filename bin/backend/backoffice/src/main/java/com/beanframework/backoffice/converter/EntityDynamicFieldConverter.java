package com.beanframework.backoffice.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.language.domain.Language;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.DynamicFieldDto;
import com.beanframework.core.data.DynamicFieldEnumDto;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.domain.DynamicFieldEnum;

public class EntityDynamicFieldConverter implements EntityConverter<DynamicFieldDto, DynamicField> {

	@Autowired
	private ModelService modelService;

	@Override
	public DynamicField convert(DynamicFieldDto source) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(DynamicField.UUID, source.getUuid());
				DynamicField prototype = modelService.findOneEntityByProperties(properties, true, DynamicField.class);

				if (prototype != null) {
					return convert(source, prototype);
				}
			}

			return convert(source, modelService.create(DynamicField.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private DynamicField convert(DynamicFieldDto source, DynamicField prototype) throws ConverterException {

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

			if (source.getSort() == null) {
				if (prototype.getSort() != null) {
					prototype.setSort(null);
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			} else {
				if (prototype.getSort() == null || prototype.getSort().equals(source.getSort()) == false) {
					prototype.setSort(source.getSort());
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			}

			if (source.getType() == source.getType() == false) {
				prototype.setType(source.getType());
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getLabel()), prototype.getLabel()) == false) {
				prototype.setLabel(StringUtils.stripToNull(source.getLabel()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			// Language
			if (StringUtils.isBlank(source.getLanguageUuid())) {
				if (prototype.getLanguage() != null) {
					prototype.setLanguage(null);
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			} else if (prototype.getLanguage() == null || prototype.getLanguage().getUuid().equals(UUID.fromString(source.getLanguageUuid())) == false) {

				Language entityLanguage = modelService.findOneEntityByUuid(UUID.fromString(source.getLanguageUuid()), true, Language.class);

				if (entityLanguage != null) {
					prototype.setLanguage(entityLanguage);
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			}

			// DynamicFieldEnum
			if (source.getEnums() == null || source.getEnums().isEmpty()) {
				if (prototype.getEnums().isEmpty()) {
					prototype.setEnums(new ArrayList<DynamicFieldEnum>());
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			}

			Iterator<DynamicFieldEnum> itr = prototype.getEnums().iterator();
			while (itr.hasNext()) {
				DynamicFieldEnum userGroup = itr.next();

				boolean remove = true;
				for (DynamicFieldEnumDto sourceDynamicFieldEnum : source.getEnums()) {
					if (userGroup.getUuid().equals(sourceDynamicFieldEnum.getUuid())) {
						remove = false;
					}
				}

				if (remove) {
					itr.remove();
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			}

			for (DynamicFieldEnumDto sourceDynamicFieldEnum : source.getEnums()) {

				boolean add = true;
				for (DynamicFieldEnum userGroup : prototype.getEnums()) {
					if (sourceDynamicFieldEnum.getUuid().equals(userGroup.getUuid()))
						add = false;
				}

				if (add) {
					DynamicFieldEnum entityDynamicFieldEnum = modelService.findOneEntityByUuid(sourceDynamicFieldEnum.getUuid(), true, DynamicFieldEnum.class);
					if (entityDynamicFieldEnum != null) {
						entityDynamicFieldEnum.setDynamicField(prototype);
						prototype.getEnums().add(entityDynamicFieldEnum);
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
