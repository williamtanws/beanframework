package com.beanframework.core.converter.entity;

import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.DynamicFieldTemplateDto;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;

@Component
public class DynamicFieldTemplateEntityConverter implements EntityConverter<DynamicFieldTemplateDto, DynamicFieldTemplate> {

	@Autowired
	private ModelService modelService;

	@Override
	public DynamicFieldTemplate convert(DynamicFieldTemplateDto source) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				DynamicFieldTemplate prototype = modelService.findOneByUuid(source.getUuid(), DynamicFieldTemplate.class);
				
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

			if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == Boolean.FALSE) {
				prototype.setId(StringUtils.stripToNull(source.getId()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getName()), prototype.getName()) == Boolean.FALSE) {
				prototype.setName(StringUtils.stripToNull(source.getName()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			// DynamicFieldSlots
			if (source.getSelectedDynamicFieldSlotUuids() != null) {

				Iterator<UUID> itr = prototype.getDynamicFieldSlots().iterator();
				while (itr.hasNext()) {
					UUID enitity = itr.next();

					boolean remove = true;
					for (int i = 0; i < source.getSelectedDynamicFieldSlotUuids().length; i++) {
						if (enitity.equals(UUID.fromString(source.getSelectedDynamicFieldSlotUuids()[i]))) {
							remove = false;
						}
					}
					if (remove) {
						itr.remove();
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				}

				for (int i = 0; i < source.getSelectedDynamicFieldSlotUuids().length; i++) {

					boolean add = true;
					itr = prototype.getDynamicFieldSlots().iterator();
					while (itr.hasNext()) {
						UUID entity = itr.next();

						if (entity.equals(UUID.fromString(source.getSelectedDynamicFieldSlotUuids()[i]))) {
							add = false;
						}
					}

					if (add) {
						DynamicFieldSlot DynamicFieldSlot = modelService.findOneByUuid(UUID.fromString(source.getSelectedDynamicFieldSlotUuids()[i]), DynamicFieldSlot.class);
						if (DynamicFieldSlot != null) {
							prototype.getDynamicFieldSlots().add(DynamicFieldSlot.getUuid());
							prototype.setLastModifiedDate(lastModifiedDate);
						}
					}
				}
			}
			else if(prototype.getDynamicFieldSlots() != null && prototype.getDynamicFieldSlots().isEmpty() == false){
				for (final Iterator<UUID> itr = prototype.getDynamicFieldSlots().iterator(); itr.hasNext();) {
					itr.next();
				    itr.remove(); 
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			}

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
