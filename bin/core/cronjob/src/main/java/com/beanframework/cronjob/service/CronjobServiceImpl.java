package com.beanframework.cronjob.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.repository.CronjobRepository;

@Service
public class CronjobServiceImpl implements CronjobService {

	@Autowired
	private CronjobRepository cronjobRepository;

	@Autowired
	private ModelService modelService;

	@Override
	public Cronjob create() throws Exception {
		return modelService.create(Cronjob.class);
	}

	@Override
	public Cronjob saveEntity(Cronjob model) throws BusinessException {
		return (Cronjob) modelService.saveEntity(model, Cronjob.class);
	}

	@Override
	public void deleteById(String id) throws BusinessException {

		try {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Cronjob.ID, id);
			Cronjob model = modelService.findOneEntityByProperties(properties, Cronjob.class);
			modelService.deleteByEntity(model, Cronjob.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<Cronjob> findEntityStartupJobIsFalseWithQueueJob() {
		return cronjobRepository.findStartupJobIsFalseWithQueueJob();
	}

}
