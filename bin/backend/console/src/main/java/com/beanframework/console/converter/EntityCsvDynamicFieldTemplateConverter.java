package com.beanframework.console.converter;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.EntityConverterContext;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.csv.DynamicFieldTemplateCsv;
import com.beanframework.console.registry.ImportListener;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;

@Component
public class EntityCsvDynamicFieldTemplateConverter implements EntityConverter<DynamicFieldTemplateCsv, DynamicFieldTemplate> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityCsvDynamicFieldTemplateConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public DynamicFieldTemplate convert(DynamicFieldTemplateCsv source, EntityConverterContext context) throws ConverterException {

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

	public DynamicFieldTemplate convert(DynamicFieldTemplateCsv source) throws ConverterException {
		return convert(source, new EntityConverterContext());
	}

	private DynamicFieldTemplate convert(DynamicFieldTemplateCsv source, DynamicFieldTemplate prototype) throws ConverterException {

		try {
			prototype.setId(StringUtils.stripToNull(source.getId()));
			prototype.setName(StringUtils.stripToNull(source.getName()));

			// Dynamic Field Slot
			if (StringUtils.isNotBlank(source.getDynamicFieldSlotIds())) {
				String[] dynamicFieldSlotIds = source.getDynamicFieldSlotIds().split(ImportListener.SPLITTER);
				for (String dynamicFieldSlotId : dynamicFieldSlotIds) {

					boolean add = true;
					for (DynamicFieldSlot dynamicFieldSlot : prototype.getDynamicFieldSlots()) {
						if (StringUtils.equals(dynamicFieldSlot.getId(), dynamicFieldSlotId))
							add = false;
					}

					if (add) {
						Map<String, Object> dynamicFieldSlotProperties = new HashMap<String, Object>();
						dynamicFieldSlotProperties.put(DynamicFieldSlot.ID, dynamicFieldSlotId);
						DynamicFieldSlot entityDynamicFieldSlot = modelService.findOneEntityByProperties(dynamicFieldSlotProperties, true, DynamicFieldSlot.class);

						if (entityDynamicFieldSlot == null) {
							LOGGER.error("DynamicFieldSlot ID not exists: " + dynamicFieldSlotId);
						} else {
							prototype.getDynamicFieldSlots().add(entityDynamicFieldSlot);
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
