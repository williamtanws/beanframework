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
import com.beanframework.console.csv.DynamicFieldSlotCsv;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;

@Component
public class EntityCsvDynamicFieldSlotConverter implements EntityConverter<DynamicFieldSlotCsv, DynamicFieldSlot> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityCsvDynamicFieldSlotConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public DynamicFieldSlot convert(DynamicFieldSlotCsv source, EntityConverterContext context) throws ConverterException {

		try {

			if (source.getId() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(DynamicFieldSlot.ID, source.getId());

				DynamicFieldSlot prototype = modelService.findOneEntityByProperties(properties, DynamicFieldSlot.class);

				if (prototype != null) {

					return convert(source, prototype);
				}
			}
			return convert(source, new DynamicFieldSlot());

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public DynamicFieldSlot convert(DynamicFieldSlotCsv source) throws ConverterException {
		return convert(source, new EntityConverterContext());
	}

	private DynamicFieldSlot convert(DynamicFieldSlotCsv source, DynamicFieldSlot prototype) throws ConverterException {

		try {
			prototype.setId(StringUtils.stripToNull(source.getId()));
			prototype.setName(StringUtils.stripToNull(source.getName()));
			prototype.setSort(source.getSort());

			// DynamicField
			if (StringUtils.isNotBlank(source.getDynamicFieldId())) {
				Map<String, Object> parentProperties = new HashMap<String, Object>();
				parentProperties.put(DynamicField.ID, source.getDynamicFieldId());
				DynamicField entityDynamicField = modelService.findOneEntityByProperties(parentProperties, DynamicField.class);

				if (entityDynamicField == null) {
					LOGGER.error("DynamicField ID not exists: " + source.getDynamicFieldId());
				} else {
					prototype.setDynamicField(entityDynamicField);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
