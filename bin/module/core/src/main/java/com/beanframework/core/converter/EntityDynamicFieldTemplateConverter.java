package com.beanframework.core.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.EntityConverterContext;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.common.utils.BooleanUtils;
import com.beanframework.core.data.DynamicFieldTemplateDto;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;
import com.beanframework.dynamicfield.service.DynamicFieldTemplateService;

public class EntityDynamicFieldTemplateConverter implements EntityConverter<DynamicFieldTemplateDto, DynamicFieldTemplate> {

	@Autowired
	private ModelService modelService;

	@Autowired
	private DynamicFieldTemplateService dynamicFieldTemplateService;

	@Override
	public DynamicFieldTemplate convert(DynamicFieldTemplateDto source, EntityConverterContext context) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(DynamicFieldTemplate.UUID, source.getUuid());
				DynamicFieldTemplate prototype = dynamicFieldTemplateService.findOneEntityByProperties(properties);

				if (prototype != null) {
					return convertToEntity(source, prototype);
				}
			}

			return convertToEntity(source, modelService.create(DynamicFieldTemplate.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private DynamicFieldTemplate convertToEntity(DynamicFieldTemplateDto source, DynamicFieldTemplate prototype) throws ConverterException {

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

			// DynamicFieldSlot
			if (source.getTableDynamicFieldSlots() != null) {

				for (int i = 0; i < source.getTableDynamicFieldSlots().length; i++) {

					boolean remove = true;
					if (source.getTableSelectedDynamicFieldSlots() != null && source.getTableSelectedDynamicFieldSlots().length > i
							&& BooleanUtils.parseBoolean(source.getTableSelectedDynamicFieldSlots()[i])) {
						remove = false;
					}

					if (remove) {
						for (Iterator<DynamicFieldSlot> dynamicFieldSlot = prototype.getDynamicFieldSlots().listIterator(); dynamicFieldSlot.hasNext();) {
							if (dynamicFieldSlot.next().getUuid().equals(UUID.fromString(source.getTableDynamicFieldSlots()[i]))) {
								dynamicFieldSlot.remove();
								prototype.setLastModifiedDate(lastModifiedDate);
							}
						}
					} else {
						boolean add = true;
						for (Iterator<DynamicFieldSlot> dynamicFieldSlot = prototype.getDynamicFieldSlots().listIterator(); dynamicFieldSlot.hasNext();) {
							if (dynamicFieldSlot.next().getUuid().equals(UUID.fromString(source.getTableDynamicFieldSlots()[i]))) {
								add = false;
							}
						}

						if (add) {
							DynamicFieldSlot entityDynamicFieldSlots = modelService.findOneEntityByUuid(UUID.fromString(source.getTableDynamicFieldSlots()[i]), DynamicFieldSlot.class);
							prototype.getDynamicFieldSlots().add(entityDynamicFieldSlots);
							prototype.setLastModifiedDate(lastModifiedDate);
						}
					}
				}
			}

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
