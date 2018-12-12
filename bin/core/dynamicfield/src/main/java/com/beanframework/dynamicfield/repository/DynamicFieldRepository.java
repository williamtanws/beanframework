package com.beanframework.dynamicfield.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.beanframework.dynamicfield.domain.DynamicField;

@Repository
public interface DynamicFieldRepository extends JpaRepository<DynamicField, UUID>, JpaSpecificationExecutor<DynamicField> {

	Optional<DynamicField> findByUuid(UUID uuid);
}
