package com.beanframework.cronjob.service;

import java.util.List;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.cronjob.domain.Cronjob;

public interface CronjobService {

	List<Cronjob> findEntityStartupJobIsFalseWithQueueJob();

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;

}
