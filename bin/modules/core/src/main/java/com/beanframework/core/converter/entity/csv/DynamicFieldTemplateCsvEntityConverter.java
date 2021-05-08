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
import com.beanframework.core.csv.DynamicFieldTemplateCsv;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;
import com.beanframework.imex.registry.ImportListener;

public class DynamicFieldTemplateCsvEntityConverter implements EntityCsvConverter<DynamicFieldTemplateCsv, DynamicFieldTemplate> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DynamicFieldTemplateCsvEntityConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public DynamicFieldTemplate convert(DynamicFieldTemplateCsv source) throws ConverterException {

		try {

			if (StringUtils.isNotBlank(source.getId())) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(DynamicFieldTemplate.ID, source.getId());

				DynamicFieldTemplate prototype = modelService.findOneByProperties(properties, DynamicFieldTemplate.class);

				if (prototype != null) {

					return convertToEntity(source, prototype);
				}
			}
			return convertToEntity(source, modelService.create(DynamicFieldTemplate.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private DynamicFieldTemplate convertToEntity(DynamicFieldTemplateCsv source, DynamicFieldTemplate prototype) throws ConverterException {

		try {
			if (StringUtils.isNotBlank(source.getId()))
				prototype.setId(source.getId());

			if (StringUtils.isNotBlank(source.getName()))
				prototype.setName(source.getName());

			// Dynamic Field Slot
			if (StringUtils.isNotBlank(source.getDynamicFieldSlotIds())) {
				String[] dynamicFieldSlotIds = source.getDynamicFieldSlotIds().split(ImportListener.SPLITTER);
				for (String dynamicFieldSlotId : dynamicFieldSlotIds) {

					boolean add = true;
					for (UUID dynamicFieldSlot : prototype.getDynamicFieldSlots()) {
						DynamicFieldSlot entity = modelService.findOneByUuid(dynamicFieldSlot, DynamicFieldSlot.class);
						if (StringUtils.equals(entity.getId(), dynamicFieldSlotId))
							add = false;
					}

					if (add) {
						Map<String, Object> dynamicFieldSlotProperties = new HashMap<String, Object>();
						dynamicFieldSlotProperties.put(DynamicFieldSlot.ID, dynamicFieldSlotId);
						DynamicFieldSlot entityDynamicFieldSlot = modelService.findOneByProperties(dynamicFieldSlotProperties, DynamicFieldSlot.class);

						if (entityDynamicFieldSlot == null) {
							LOGGER.error("DynamicFieldSlot ID not exists: " + dynamicFieldSlotId);
						} else {
							prototype.getDynamicFieldSlots().add(entityDynamicFieldSlot.getUuid());
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
