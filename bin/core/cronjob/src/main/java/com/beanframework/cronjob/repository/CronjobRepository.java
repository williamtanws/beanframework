package com.beanframework.cronjob.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.beanframework.cronjob.domain.Cronjob;

@Repository
public interface CronjobRepository extends JpaRepository<Cronjob, UUID>, JpaSpecificationExecutor<Cronjob> {

	Optional<Cronjob> findByUuid(UUID uuid);

	Optional<Cronjob> findById(String id);

	@Query("select count(c) > 0 from Cronjob c where c.jobGroup = :jobGroup and c.jobName = :jobName")
	boolean isGroupAndNameExists(@Param("jobGroup") String jobGroup, @Param("jobName") String jobName);

	@Query("select c from Cronjob c where c.jobGroup = :jobGroup and c.jobName = :jobName")
	Cronjob findByJobGroupAndJobName(@Param("jobGroup") String jobGroup, @Param("jobName") String jobName);

	@Query("select c from Cronjob c where c.startup = 1")
	List<Cronjob> findByStartupJobIsTrue();
	
	@Query("select c from Cronjob c where c.startup = 0 and (c.status = 'Continued' or c.status = 'RUNNING' or c.status = 'PAUSED')")
	List<Cronjob> findStartupJobIsFalseWithQueueJob();
}