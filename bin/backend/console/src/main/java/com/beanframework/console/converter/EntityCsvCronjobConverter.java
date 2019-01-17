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
import com.beanframework.console.csv.CronjobCsv;
import com.beanframework.console.registry.ImportListener;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.domain.CronjobData;

@Component
public class EntityCsvCronjobConverter implements EntityConverter<CronjobCsv, Cronjob> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityCsvCronjobConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public Cronjob convert(CronjobCsv source) throws ConverterException {

		try {

			if (source.getId() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Cronjob.ID, source.getId());

				Cronjob prototype = modelService.findOneEntityByProperties(properties, Cronjob.class);

				if (prototype != null) {
					
					return convert(source, prototype);
				}
			}
			return convert(source, modelService.create(Cronjob.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), this);
		}
	}

	private Cronjob convert(CronjobCsv source, Cronjob prototype) throws ConverterException {

		try {
			prototype.setId(StringUtils.stripToNull(source.getId()));
			prototype.setJobClass(StringUtils.stripToNull(source.getJobClass()));
			prototype.setJobGroup(StringUtils.stripToNull(source.getJobGroup()));
			prototype.setJobName(StringUtils.stripToNull(source.getJobName()));
			prototype.setDescription(StringUtils.stripToNull(source.getDescription()));
			prototype.setCronExpression(StringUtils.stripToNull(source.getCronExpression()));
			prototype.setStartup(source.isStartup());
			
			// Cronjob Data
			for (int i = 0; i < prototype.getCronjobDatas().size(); i++) {
				if (source.getCronjobData() != null) {
					String[] cronjobDataList = source.getCronjobData().split(ImportListener.SPLITTER);

					for (String cronjobDataString : cronjobDataList) {
						String name = cronjobDataString.split(ImportListener.EQUALS)[0];
						String value = cronjobDataString.split(ImportListener.EQUALS)[1];

						if (prototype.getCronjobDatas().get(i).getName().equals(name)) {
							prototype.getCronjobDatas().get(i).setValue(value);
						}
					}
				}
			}
			
			if (source.getCronjobData() != null) {

				String[] cronjobDataList = source.getCronjobData().split(ImportListener.SPLITTER);

				for (String cronjobDataString : cronjobDataList) {
					String name = cronjobDataString.split(ImportListener.EQUALS)[0];
					String value = cronjobDataString.split(ImportListener.EQUALS)[1];
					
					boolean add = true;
					for (CronjobData cronjobData : prototype.getCronjobDatas()) {
						if (cronjobData.getName().equals(name)) {
							add = false;
						}
					}
					
					if(add) {
						CronjobData cronjobData = new CronjobData();
						cronjobData.setName(name);
						cronjobData.setValue(value);
						cronjobData.setCronjob(prototype);
						prototype.getCronjobDatas().add(cronjobData);
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
