package com.beanframework.configuration.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.beanframework.configuration.domain.Configuration;
import com.beanframework.configuration.validator.DeleteConfigurationValidator;
import com.beanframework.configuration.validator.SaveConfigurationValidator;

@Component
public class ConfigurationFacadeImpl implements ConfigurationFacade {

	@Autowired
	private ConfigurationService configurationService;

	@Autowired
	private SaveConfigurationValidator saveConfigurationValidator;
	
	@Autowired
	private DeleteConfigurationValidator deleteConfigurationValidator;

	@Override
	public Configuration create() {
		return configurationService.create();
	}

	@Override
	public Configuration initDefaults(Configuration configuration) {
		return configurationService.initDefaults(configuration);
	}

	@Override
	public Configuration save(Configuration configuration, Errors bindingResult) {
		saveConfigurationValidator.validate(configuration, bindingResult);

		if (bindingResult.hasErrors()) {
			return configuration;
		}

		return configurationService.save(configuration);
	}

	@Override
	public void delete(UUID uuid, Errors bindingResult) {
		deleteConfigurationValidator.validate(uuid, bindingResult);
		
		if (bindingResult.hasErrors() == false) {
			configurationService.delete(uuid);
		}
	}

	@Override
	public void deleteAll() {
		configurationService.deleteAll();
	}

	@Override
	public Configuration findByUuid(UUID uuid) {
		return configurationService.findByUuid(uuid);
	}

	@Override
	public Configuration findById(String id) {
		return configurationService.findById(id);
	}

	@Override
	public Page<Configuration> page(Configuration configuration, int page, int size, Direction direction,
			String... properties) {

		// Change page to index's page
		page = page <= 0 ? 0 : page - 1;
		size = size <= 0 ? 1 : size;

		PageRequest pageRequest = PageRequest.of(page, size, direction, properties);

		return configurationService.page(configuration, pageRequest);
	}

	public ConfigurationService getConfigurationService() {
		return configurationService;
	}

	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

	public SaveConfigurationValidator getSaveConfigurationValidator() {
		return saveConfigurationValidator;
	}

	public void setSaveConfigurationValidator(SaveConfigurationValidator saveConfigurationValidator) {
		this.saveConfigurationValidator = saveConfigurationValidator;
	}

}
