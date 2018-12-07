package com.beanframework.configuration.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.configuration.converter.DtoConfigurationConverter;
import com.beanframework.configuration.converter.EntityConfigurationConverter;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.configuration.domain.ConfigurationSpecification;
import com.beanframework.configuration.repository.ConfigurationRepository;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {

	@Autowired
	private ConfigurationRepository configurationRepository;

	@Autowired
	private EntityConfigurationConverter entityConfigurationConverter;

	@Autowired
	private DtoConfigurationConverter dtoConfigurationConverter;

	@Override
	public Configuration create() {
		return initDefaults(new Configuration());
	}

	@Override
	public Configuration initDefaults(Configuration configuration) {
		return configuration;
	}

	@Transactional(readOnly = false)
	@Override
	public Configuration save(Configuration configuration) {
		configuration = entityConfigurationConverter.convert(configuration);
		configuration = configurationRepository.save(configuration);
		configuration = dtoConfigurationConverter.convert(configuration);
		return configuration;
	}

	@Transactional(readOnly = false)
	@Override
	public void delete(UUID uuid) {
		configurationRepository.deleteById(uuid);
	}

	@Transactional(readOnly = false)
	@Override
	public void deleteAll() {
		configurationRepository.deleteAll();
	}
	
	@Transactional(readOnly = true)
	@Override
	public Optional<Configuration> findEntityByUuid(UUID uuid) {
		return configurationRepository.findByUuid(uuid);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Configuration> findEntityById(String id) {
		return configurationRepository.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Configuration findByUuid(UUID uuid) {
		Optional<Configuration> configuration = configurationRepository.findByUuid(uuid);

		if (configuration.isPresent()) {
			return dtoConfigurationConverter.convert(configuration.get());
		} else {
			return null;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Configuration findById(String id) {
		Optional<Configuration> configuration = configurationRepository.findById(id);

		if (configuration.isPresent()) {
			return dtoConfigurationConverter.convert(configuration.get());
		} else {
			return null;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Configuration> page(Configuration configuration, Pageable pageable) {
		Page<Configuration> page = configurationRepository
				.findAll(ConfigurationSpecification.findByCriteria(configuration), pageable);
		List<Configuration> content = dtoConfigurationConverter.convert(page.getContent());
		return new PageImpl<Configuration>(content, page.getPageable(), page.getTotalElements());
	}

	public ConfigurationRepository getConfigurationRepository() {
		return configurationRepository;
	}

	public void setConfigurationRepository(ConfigurationRepository configurationRepository) {
		this.configurationRepository = configurationRepository;
	}

	public EntityConfigurationConverter getEntityConfigurationConverter() {
		return entityConfigurationConverter;
	}

	public void setEntityConfigurationConverter(EntityConfigurationConverter entityConfigurationConverter) {
		this.entityConfigurationConverter = entityConfigurationConverter;
	}

	public DtoConfigurationConverter getDtoConfigurationConverter() {
		return dtoConfigurationConverter;
	}

	public void setDtoConfigurationConverter(DtoConfigurationConverter dtoConfigurationConverter) {
		this.dtoConfigurationConverter = dtoConfigurationConverter;
	}
}
