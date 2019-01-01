package com.beanframework.cronjob.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.beanframework.cronjob.domain.Cronjob;

@Repository
public interface CronjobRepository extends JpaRepository<Cronjob, UUID>, JpaSpecificationExecutor<Cronjob> {

	@Query("select c from Cronjob c where c.startup = 0 and (c.status = 'Continued' or c.status = 'RUNNING' or c.status = 'PAUSED')")
	List<Cronjob> findStartupJobIsFalseWithQueueJob();
}