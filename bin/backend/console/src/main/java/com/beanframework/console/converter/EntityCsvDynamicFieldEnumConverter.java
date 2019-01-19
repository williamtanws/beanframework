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
import com.beanframework.console.csv.DynamicFieldEnumCsv;
import com.beanframework.dynamicfield.domain.DynamicFieldEnum;

@Component
public class EntityCsvDynamicFieldEnumConverter implements EntityConverter<DynamicFieldEnumCsv, DynamicFieldEnum> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityCsvDynamicFieldEnumConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public DynamicFieldEnum convert(DynamicFieldEnumCsv source) throws ConverterException {

		try {

			if (source.getId() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(DynamicFieldEnum.ID, source.getId());

				DynamicFieldEnum prototype = modelService.findOneEntityByProperties(properties, true,DynamicFieldEnum.class);

				if (prototype != null) {

					return convert(source, prototype);
				}
			}
			return convert(source, modelService.create(DynamicFieldEnum.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private DynamicFieldEnum convert(DynamicFieldEnumCsv source, DynamicFieldEnum prototype) throws ConverterException {

		try {
			prototype.setId(StringUtils.stripToNull(source.getId()));
			prototype.setEnumGroup(StringUtils.stripToNull(source.getGroup()));
			prototype.setName(StringUtils.stripToNull(source.getName()));
			prototype.setSort(source.getSort());

		} catch (Exception e) {
			e.printStackTrace();
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
