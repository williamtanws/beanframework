package com.beanframework.console.converter;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.EntityCsvConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.csv.DynamicFieldCsv;
import com.beanframework.console.registry.ImportListener;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.enumuration.domain.Enumeration;
import com.beanframework.language.domain.Language;

@Component
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

				DynamicField prototype = modelService.findOneEntityByProperties(properties, DynamicField.class);

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
				Language entityLanguage = modelService.findOneEntityByProperties(languageProperties, Language.class);

				if (entityLanguage == null) {
					LOGGER.error("Enum ID not exists: " + source.getLanguage());
				} else {
					prototype.setLanguage(entityLanguage);
				}
			}

			// Enum Values
			if (StringUtils.isNotBlank(source.getEnumIds())) {
				String[] values = source.getEnumIds().split(ImportListener.SPLITTER);
				for (int i = 0; i < values.length; i++) {

					boolean add = true;
					for (Enumeration prototypeValue : prototype.getEnumerations()) {
						if (prototypeValue.getId().equals(values[i])) {
							add = false;
						}
					}

					if (add) {
						Map<String, Object> enumProperties = new HashMap<String, Object>();
						enumProperties.put(Enumeration.ID, values[i]);
						Enumeration entityEnum = modelService.findOneEntityByProperties(enumProperties, Enumeration.class);

						if (entityEnum == null) {
							LOGGER.error("Enum ID not exists: " + values[i]);
						} else {
							prototype.getEnumerations().add(entityEnum);
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
