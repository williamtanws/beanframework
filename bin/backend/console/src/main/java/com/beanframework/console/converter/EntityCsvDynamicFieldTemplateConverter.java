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
import com.beanframework.console.csv.DynamicFieldTemplateCsv;
import com.beanframework.console.registry.ImportListener;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;

@Component
public class EntityCsvDynamicFieldTemplateConverter implements EntityConverter<DynamicFieldTemplateCsv, DynamicFieldTemplate> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityCsvDynamicFieldTemplateConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public DynamicFieldTemplate convert(DynamicFieldTemplateCsv source) throws ConverterException {

		try {

			if (source.getId() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(DynamicFieldTemplate.ID, source.getId());

				DynamicFieldTemplate prototype = modelService.findOneEntityByProperties(properties, true, DynamicFieldTemplate.class);

				if (prototype != null) {

					return convert(source, prototype);
				}
			}
			return convert(source, new DynamicFieldTemplate());

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private DynamicFieldTemplate convert(DynamicFieldTemplateCsv source, DynamicFieldTemplate prototype) throws ConverterException {

		try {
			prototype.setId(StringUtils.stripToNull(source.getId()));
			prototype.setName(StringUtils.stripToNull(source.getName()));

			// Dynamic Field
			if (StringUtils.isNotBlank(source.getDynamicFieldSlotIds())) {
				String[] dynamicFieldIds = source.getDynamicFieldSlotIds().split(ImportListener.SPLITTER);
				for (int i = 0; i < dynamicFieldIds.length; i++) {
					String dynamicFieldSlotId = dynamicFieldIds[i].split(ImportListener.EQUALS)[0];
					String dynamicFieldSlotSort = dynamicFieldIds[i].split(ImportListener.EQUALS)[1].split(ImportListener.COLON)[0];
					String dynamicFieldId = dynamicFieldIds[i].split(ImportListener.EQUALS)[1].split(ImportListener.COLON)[1];
					
					boolean add = true;
					for (DynamicFieldSlot dynamicFieldSlot : prototype.getDynamicFieldSlots()) {
						if (StringUtils.equals(dynamicFieldSlot.getId(), dynamicFieldSlotId))
							add = false;
					}

					if (add) {
						Map<String, Object> dynamicFieldProperties = new HashMap<String, Object>();
						dynamicFieldProperties.put(DynamicField.ID, dynamicFieldId);
						DynamicField dynamicField = modelService.findOneEntityByProperties(dynamicFieldProperties, true, DynamicField.class);

						if (dynamicField == null) {
							LOGGER.error("DynamicField not exists: " + dynamicFieldIds[i]);
						} else {
							DynamicFieldSlot dynamicFieldSlot = new DynamicFieldSlot();
							dynamicFieldSlot.setId(dynamicFieldSlotId);
							dynamicFieldSlot.setDynamicField(dynamicField);
							dynamicFieldSlot.setSort(Integer.valueOf(dynamicFieldSlotSort));
							dynamicFieldSlot.setDynamicFieldTemplate(prototype);
							prototype.getDynamicFieldSlots().add(dynamicFieldSlot);
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
