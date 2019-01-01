package com.beanframework.cronjob.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.repository.CronjobRepository;

@Service
public class CronjobServiceImpl implements CronjobService {
	
	@Autowired
	private CronjobRepository cronjobRepository;

	@Transactional(readOnly = true)
	@Override
	public List<Cronjob> findEntityStartupJobIsFalseWithQueueJob() {
		return cronjobRepository.findStartupJobIsFalseWithQueueJob();
	}
}
