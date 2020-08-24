package com.beanframework.core.converter.entity.csv;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityCsvConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.csv.DynamicFieldCsv;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.enumuration.domain.Enumeration;
import com.beanframework.imex.registry.ImportListener;
import com.beanframework.internationalization.domain.Language;

public class EntityCsvDynamicFieldConverter implements EntityCsvConverter<DynamicFieldCsv, DynamicField> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityCsvDynamicFieldConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public DynamicField convert(DynamicFieldCsv source) throws ConverterException {

		try {

			if (StringUtils.isNotBlank(source.getId())) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(DynamicField.ID, source.getId());

				DynamicField prototype = modelService.findOneByProperties(properties, DynamicField.class);

				if (prototype != null) {

					return convertToEntity(source, prototype);
				}
			}
			return convertToEntity(source, modelService.create(DynamicField.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private DynamicField convertToEntity(DynamicFieldCsv source, DynamicField prototype) throws ConverterException {

		try {
			if (StringUtils.isNotBlank(source.getId()))
				prototype.setId(source.getId());

			if (StringUtils.isNotBlank(source.getName()))
				prototype.setName(source.getName());

			if (source.getType() != null)
				prototype.setType(source.getType());

			if (source.getRequired() != null)
				prototype.setRequired(source.getRequired());

			if (StringUtils.isNotBlank(source.getRule()))
				prototype.setRule(source.getRule());

			if (StringUtils.isNotBlank(source.getLabel()))
				prototype.setLabel(source.getLabel());

			if (StringUtils.isNotBlank(source.getGrid()))
				prototype.setGrid(source.getGrid());

			// Language
			if (StringUtils.isNotBlank(source.getLanguage())) {
				Map<String, Object> languageProperties = new HashMap<String, Object>();
				languageProperties.put(Language.ID, source.getLanguage());
				Language entityLanguage = modelService.findOneByProperties(languageProperties, Language.class);

				if (entityLanguage == null) {
					LOGGER.error("Enum ID not exists: " + source.getLanguage());
				} else {
					prototype.setLanguage(entityLanguage.getUuid());
				}
			}

			// Enum Values
			if (StringUtils.isNotBlank(source.getEnumIds())) {
				String[] values = source.getEnumIds().split(ImportListener.SPLITTER);
				for (int i = 0; i < values.length; i++) {

					boolean add = true;
					for (UUID prototypeValue : prototype.getEnumerations()) {
						Enumeration enumeration = modelService.findOneByUuid(prototypeValue, Enumeration.class);
						if (enumeration.getId().equals(values[i])) {
							add = false;
						}
					}

					if (add) {
						Map<String, Object> enumProperties = new HashMap<String, Object>();
						enumProperties.put(Enumeration.ID, values[i]);
						Enumeration entityEnum = modelService.findOneByProperties(enumProperties, Enumeration.class);

						if (entityEnum == null) {
							LOGGER.error("Enum ID not exists: " + values[i]);
						} else {
							prototype.getEnumerations().add(entityEnum.getUuid());
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
