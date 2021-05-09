package com.beanframework.core.converter.entity;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.RegionDto;
import com.beanframework.internationalization.domain.Country;
import com.beanframework.internationalization.domain.Region;

@Component
public class RegionEntityConverter implements EntityConverter<RegionDto, Region> {

	@Autowired
	private ModelService modelService;

	@Override
	public Region convert(RegionDto source) throws ConverterException {

		try {
			if (source.getUuid() != null) {
				Region prototype = modelService.findOneByUuid(source.getUuid(), Region.class);

				if (prototype != null) {
					return convertToEntity(source, prototype);
				}
			}
			return convertToEntity(source, modelService.create(Region.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private Region convertToEntity(RegionDto source, Region prototype) throws ConverterException {

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

			// country
			if (StringUtils.isBlank(source.getSelectedCountryUuid())) {
				if (prototype.getCountry() != null) {
					prototype.setCountry(null);
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			} else {
				Country entity = modelService.findOneByUuid(UUID.fromString(source.getSelectedCountryUuid()), Country.class);

				if (entity != null) {

					if (prototype.getCountry() == null || prototype.getCountry().equals(entity.getUuid()) == false) {
						prototype.setCountry(entity.getUuid());
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				} else {
					throw new ConverterException("Country UUID not found: " + source.getSelectedCountryUuid());
				}
			}

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
