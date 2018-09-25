package com.beanframework.configuration.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.beanframework.configuration.domain.Configuration;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, UUID>, JpaSpecificationExecutor<Configuration> {

	Optional<Configuration> findByUuid(UUID uuid);

	Optional<Configuration> findById(String id);
}
