package com.beanframework.cronjob.service;

import java.util.List;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.cronjob.domain.Cronjob;

public interface CronjobService {

	Cronjob create() throws Exception;

	Cronjob saveEntity(Cronjob model) throws BusinessException;

	void deleteById(String id) throws BusinessException;
	
	List<Cronjob> findEntityStartupJobIsFalseWithQueueJob();
	
}
