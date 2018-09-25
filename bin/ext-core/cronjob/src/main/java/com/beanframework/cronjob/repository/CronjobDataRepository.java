package com.beanframework.cronjob.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.beanframework.cronjob.domain.CronjobData;

@Repository
public interface CronjobDataRepository extends JpaRepository<CronjobData, UUID>, JpaSpecificationExecutor<CronjobData> {

}