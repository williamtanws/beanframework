package com.beanframework.cronjob.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.beanframework.cronjob.domain.Cronjob;

public interface CronjobService {

	List<Cronjob> findStartupJobIsFalseWithQueueJob();

	Page<Cronjob> page(Cronjob cronjob, Pageable pageable);

}
