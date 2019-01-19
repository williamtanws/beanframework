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
import com.beanframework.dynamicfield.domain.DynamicFieldEnum;
import com.beanframework.dynamicfield.domain.DynamicFieldType;
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

				DynamicField prototype = modelService.findOneEntityByProperties(properties, true,DynamicField.class);

				if (prototype != null) {

					return convert(source, prototype);
				}
			}
			return convert(source, modelService.create(DynamicField.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private DynamicField convert(DynamicFieldCsv source, DynamicField prototype) throws ConverterException {

		try {
			prototype.setId(StringUtils.stripToNull(source.getId()));
			prototype.setName(StringUtils.stripToNull(source.getName()));
			prototype.setType(DynamicFieldType.valueOf(source.getType()));
			prototype.setSort(source.getSort());
			prototype.setRequired(source.isRequired());
			prototype.setRule(StringUtils.stripToNull(StringUtils.stripToNull(source.getRule())));
			prototype.setLabel(StringUtils.stripToNull(source.getLabel()));

			if (StringUtils.isNotBlank(source.getLanguage())) {
				Map<String, Object> languageProperties = new HashMap<String, Object>();
				languageProperties.put(Language.ID, source.getLanguage());
				Language entityLanguage = modelService.findOneEntityByProperties(languageProperties, true, Language.class);
				
				if(entityLanguage != null) {
					prototype.setLanguage(entityLanguage);
				}
			}

			// Enum Values
			if (StringUtils.isNotBlank(source.getEnumIds())) {
				String[] values = source.getEnumIds().split(ImportListener.SPLITTER);
				for (int i = 0; i < values.length; i++) {

					boolean add = true;
					for (DynamicFieldEnum prototypeValue : prototype.getEnums()) {
						if (prototypeValue.getId().equals(values[i])) {
							add = false;
						}
					}

					if (add) {
						Map<String, Object> enumProperties = new HashMap<String, Object>();
						enumProperties.put(DynamicFieldEnum.ID, values[i]);
						DynamicFieldEnum entityEnum = modelService.findOneEntityByProperties(enumProperties, true, DynamicFieldEnum.class);
						
						if(entityEnum != null) {
							entityEnum.setDynamicField(prototype);
							prototype.getEnums().add(entityEnum);
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
