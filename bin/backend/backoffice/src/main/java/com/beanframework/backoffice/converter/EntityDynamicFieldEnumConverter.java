package com.beanframework.backoffice.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.backoffice.data.DynamicFieldEnumDto;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicFieldEnum;

public class EntityDynamicFieldEnumConverter implements EntityConverter<DynamicFieldEnumDto, DynamicFieldEnum> {

	@Autowired
	private ModelService modelService;

	@Override
	public DynamicFieldEnum convert(DynamicFieldEnumDto source) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(DynamicFieldEnum.UUID, source.getUuid());
				DynamicFieldEnum prototype = modelService.findOneEntityByProperties(properties, DynamicFieldEnum.class);

				if (prototype != null) {
					return convert(source, prototype);
				}
			}

			return convert(source, modelService.create(DynamicFieldEnum.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private DynamicFieldEnum convert(DynamicFieldEnumDto source, DynamicFieldEnum prototype) {

		Date lastModifiedDate = new Date();

		if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == false) {
			prototype.setId(StringUtils.stripToNull(source.getId()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getEnumGroup()), prototype.getEnumGroup()) == false) {
			prototype.setEnumGroup(StringUtils.stripToNull(source.getEnumGroup()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}
		
		if (StringUtils.equals(StringUtils.stripToNull(source.getEnumGroup()), prototype.getName()) == false) {
			prototype.setName(StringUtils.stripToNull(source.getName()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (source.getSort() == null) {
			if (prototype.getSort() != null) {
				prototype.setSort(null);
				prototype.setLastModifiedDate(lastModifiedDate);
			}
		} else {
			if (prototype.getSort() == null || prototype.getSort().equals(source.getSort()) == false) {
				prototype.setSort(source.getSort());
				prototype.setLastModifiedDate(lastModifiedDate);
			}
		}

		return prototype;
	}

}