package com.beanframework.cronjob.service;

import java.util.List;

import com.beanframework.cronjob.domain.Cronjob;

public interface CronjobService {

	List<Cronjob> findEntityStartupJobIsFalseWithQueueJob();

}
