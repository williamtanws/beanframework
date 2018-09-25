package com.beanframework.cronjob.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.cronjob.CronjobConstants;
import com.beanframework.cronjob.converter.DtoCronjobConverter;
import com.beanframework.cronjob.converter.EntityCronjobConverter;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.domain.CronjobSpecification;
import com.beanframework.cronjob.repository.CronjobDataRepository;
import com.beanframework.cronjob.repository.CronjobRepository;

@Service
public class CronjobServiceImpl implements CronjobService {

	@Autowired
	private CronjobRepository cronjobRepository;

	@Autowired
	private CronjobDataRepository cronjobDataRepository;

	@Autowired
	private EntityCronjobConverter entityCronjobConverter;

	@Autowired
	private DtoCronjobConverter dtoCronjobConverter;

	@Override
	public Cronjob create() {
		return initDefaults(new Cronjob());
	}

	@Override
	public Cronjob initDefaults(Cronjob cronjob) {
		return cronjob;
	}

	@CacheEvict(value = { CronjobConstants.Cache.CRONJOB,  CronjobConstants.Cache.CRONJOBS}, allEntries = true)
	@Transactional(readOnly = false)
	@Override
	public Cronjob save(Cronjob cronjob) {
		cronjob = entityCronjobConverter.convert(cronjob);
		cronjob = cronjobRepository.save(cronjob);
		cronjob = dtoCronjobConverter.convert(cronjob);

		return cronjob;

	}

	@CacheEvict(value = { CronjobConstants.Cache.CRONJOB,  CronjobConstants.Cache.CRONJOBS}, allEntries = true)
	@Transactional(readOnly = false)
	@Override
	public void deleteByUuid(UUID uuid) {
		cronjobRepository.deleteById(uuid);
	}

	@CacheEvict(value = { CronjobConstants.Cache.CRONJOB,  CronjobConstants.Cache.CRONJOBS}, allEntries = true)
	@Transactional(readOnly = false)
	@Override
	public void deleteCronJobData(UUID uuid) {
		cronjobDataRepository.deleteById(uuid);
	}

	@CacheEvict(value = { CronjobConstants.Cache.CRONJOB,  CronjobConstants.Cache.CRONJOBS}, allEntries = true)
	@Transactional(readOnly = false)
	@Override
	public void deleteAll() {
		cronjobRepository.deleteAll();
	}

	@Transactional(readOnly = true)
	@Override
	public boolean isGroupAndNameExists(String jobGroup, String jobName) {
		return cronjobRepository.isGroupAndNameExists(jobGroup, jobName);
	}

	@Cacheable(value = CronjobConstants.Cache.CRONJOB, key="#p0")
	@Transactional(readOnly = true)
	@Override
	public Cronjob findByUuid(UUID uuid) {
		Optional<Cronjob> cronjob = cronjobRepository.findByUuid(uuid);
		if (cronjob.isPresent()) {
			return dtoCronjobConverter.convert(cronjob.get());
		} else {
			return null;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Cronjob findById(String id) {
		Optional<Cronjob> cronjob = cronjobRepository.findById(id);
		if (cronjob.isPresent()) {
			return dtoCronjobConverter.convert(cronjob.get());
		} else {
			return null;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Cronjob findByJobGroupAndJobName(String jobGroup, String jobName) {
		return cronjobRepository.findByJobGroupAndJobName(jobGroup, jobName);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Cronjob> findEntityByUuid(UUID uuid) {
		return cronjobRepository.findByUuid(uuid);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Optional<Cronjob> findEntityById(String id) {
		return cronjobRepository.findById(id);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Cronjob> findByStartupJobIsTrue() {
		return cronjobRepository.findByStartupJobIsTrue();
	}

	@Transactional(readOnly = true)
	@Override
	public List<Cronjob> findStartupJobIsFalseWithQueueJob() {
		return cronjobRepository.findStartupJobIsFalseWithQueueJob();
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Cronjob> page(Cronjob cronjob, Pageable pageable) {
		Page<Cronjob> page = cronjobRepository.findAll(CronjobSpecification.findByCriteria(cronjob), pageable);
		List<Cronjob> content = dtoCronjobConverter.convert(page.getContent());
		return new PageImpl<Cronjob>(content, page.getPageable(), page.getTotalElements());
	}
}
