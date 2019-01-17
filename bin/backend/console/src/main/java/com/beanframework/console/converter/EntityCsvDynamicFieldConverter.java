package com.beanframework.console.converter;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.csv.DynamicFieldCsv;
import com.beanframework.console.registry.ImportListener;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.domain.DynamicFieldEnumValue;
import com.beanframework.dynamicfield.domain.DynamicFieldTypeEnum;
import com.beanframework.language.domain.Language;

@Component
public class EntityCsvDynamicFieldConverter implements EntityConverter<DynamicFieldCsv, DynamicField> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityCsvDynamicFieldConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public DynamicField convert(DynamicFieldCsv source) throws ConverterException {

		try {

			if (source.getId() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(DynamicField.ID, source.getId());

				DynamicField prototype = modelService.findOneEntityByProperties(properties, DynamicField.class);

				if (prototype != null) {

					return convert(source, prototype);
				}
			}
			return convert(source, modelService.create(DynamicField.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), this);
		}
	}

	private DynamicField convert(DynamicFieldCsv source, DynamicField prototype) throws ConverterException {

		try {
			prototype.setId(StringUtils.stripToNull(source.getId()));
			prototype.setName(StringUtils.stripToNull(source.getName()));
			prototype.setFieldType(DynamicFieldTypeEnum.valueOf(source.getType()));
			prototype.setSort(source.getSort());
			prototype.setRequired(source.isRequired());
			prototype.setRule(StringUtils.stripToNull(StringUtils.stripToNull(source.getRule())));
			prototype.setFieldGroup(StringUtils.stripToNull(source.getGroup()));
			prototype.setLabel(StringUtils.stripToNull(source.getLabel()));

			if (StringUtils.isNotBlank(source.getLanguage())) {
				Map<String, Object> languageProperties = new HashMap<String, Object>();
				languageProperties.put(Language.ID, source.getLanguage());
				Language modelLanguage = modelService.findOneEntityByProperties(languageProperties, Language.class);
				prototype.setLanguage(modelLanguage);
			}

			// Enum Values
			if (StringUtils.isNotBlank(source.getEnumValues())) {
				String[] values = source.getEnumValues().split(ImportListener.SPLITTER);
				for (int i = 0; i < values.length; i++) {

					boolean add = true;
					for (DynamicFieldEnumValue prototypeValue : prototype.getValues()) {
						if (values[i].equals(prototypeValue.getValue())) {
							add = false;
						}
					}

					if (add) {
						DynamicFieldEnumValue dynamicFieldEnumValue = new DynamicFieldEnumValue();
						dynamicFieldEnumValue.setValue(StringUtils.upperCase(values[i]));
						dynamicFieldEnumValue.setSort(i);

						dynamicFieldEnumValue.setDynamicField(prototype);
						prototype.getValues().add(dynamicFieldEnumValue);
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
