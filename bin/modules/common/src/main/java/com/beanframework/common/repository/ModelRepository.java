package com.beanframework.common.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.beanframework.common.domain.GenericEntity;

@Repository
public interface ModelRepository<T extends GenericEntity> extends JpaRepository<T, UUID>, JpaSpecificationExecutor<T> {

}
