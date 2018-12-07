package com.beanframework.cronjob.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.beanframework.cronjob.domain.Cronjob;

public interface CronjobService {

	Cronjob create();

	Cronjob initDefaults(Cronjob cronjob);

	Cronjob save(Cronjob cronjob);

	void deleteByUuid(UUID uuid);

	void deleteCronJobData(UUID uuid);

	void deleteAll();

	boolean isGroupAndNameExists(String jobGroup, String jobName);

	Cronjob findByUuid(UUID uuid);

	Cronjob findById(String id);

	Cronjob findByJobGroupAndJobName(String jobGroup, String jobName);

	Optional<Cronjob> findEntityByUuid(UUID uuid);

	Optional<Cronjob> findEntityById(String id);

	List<Cronjob> findByStartupJobIsTrue();

	List<Cronjob> findStartupJobIsFalseWithQueueJob();

	Page<Cronjob> page(Cronjob cronjob, Pageable pageable);

}
