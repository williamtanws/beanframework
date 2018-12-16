package com.beanframework.cronjob.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.common.service.ModelService;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.domain.CronjobSpecification;
import com.beanframework.cronjob.repository.CronjobRepository;

@Service
public class CronjobServiceImpl implements CronjobService {

	@Autowired
	private ModelService modelService;

	@Autowired
	private CronjobRepository cronjobRepository;

	@Transactional(readOnly = true)
	@Override
	public List<Cronjob> findStartupJobIsFalseWithQueueJob() {
		return cronjobRepository.findStartupJobIsFalseWithQueueJob();
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Cronjob> page(Cronjob cronjob, Pageable pageable) {
		Page<Cronjob> page = modelService.findPage(CronjobSpecification.findByCriteria(cronjob), pageable,
				Cronjob.class);
		return page;
	}
}
