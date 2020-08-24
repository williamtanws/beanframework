package com.beanframework.core.converter.entity;

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
import com.beanframework.core.data.CountryDto;
import com.beanframework.internationalization.domain.Country;
import com.beanframework.internationalization.domain.Region;

public class EntityCountryConverter implements EntityConverter<CountryDto, Country> {

	@Autowired
	private ModelService modelService;

	@Override
	public Country convert(CountryDto source, EntityConverterContext context) throws ConverterException {

		try {
			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Country.UUID, source.getUuid());
				Country prototype = modelService.findOneByProperties(properties, Country.class);

				if (prototype != null) {
					return convertToEntity(source, prototype);
				}
			}
			return convertToEntity(source, modelService.create(Country.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private Country convertToEntity(CountryDto source, Country prototype) throws ConverterException {

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

			if (prototype.getActive() == source.getActive() == Boolean.FALSE) {
				prototype.setActive(source.getActive());
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			// Regions
			if (source.getSelectedRegions() != null) {

				Iterator<UUID> it = prototype.getRegions().iterator();
				while (it.hasNext()) {
					UUID o = it.next();

					boolean remove = true;
					for (int i = 0; i < source.getSelectedRegions().length; i++) {
						if (o.equals(UUID.fromString(source.getSelectedRegions()[i]))) {
							remove = false;
						}
					}
					if (remove) {
						Region entity = modelService.findOneByUuid(o, Region.class);
						entity.setCountry(null);
						modelService.saveEntity(entity, Region.class);
						it.remove();
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				}

				for (int i = 0; i < source.getSelectedRegions().length; i++) {

					boolean add = true;
					it = prototype.getRegions().iterator();
					while (it.hasNext()) {
						UUID entity = it.next();

						if (entity.equals(UUID.fromString(source.getSelectedRegions()[i]))) {
							add = false;
						}
					}

					if (add) {
						Region entity = modelService.findOneByUuid(UUID.fromString(source.getSelectedRegions()[i]), Region.class);
						if (entity != null) {
							entity.setCountry(prototype.getUuid());
							prototype.getRegions().add(entity.getUuid());
							prototype.setLastModifiedDate(lastModifiedDate);
						}
					}
				}
			}
			else if(prototype.getRegions() != null && prototype.getRegions().isEmpty() == false){
				for (final Iterator<UUID> itr = prototype.getRegions().iterator(); itr.hasNext();) {
					Region entity = modelService.findOneByUuid(itr.next(), Region.class);
					entity.setCountry(null);
					modelService.saveEntity(entity, Region.class);
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
