package com.beanframework.user.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beanframework.common.domain.Auditor;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.User;

@Service
public class AuditorServiceImpl implements AuditorService {

	@Autowired
	private ModelService modelService;

	@Override
	public Auditor saveEntityByUser(User model) throws BusinessException {
		try {
			Auditor auditor = modelService.findOneByUuid(model.getUuid(), Auditor.class);
			if (auditor == null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Auditor.ID, model.getId());
				auditor = modelService.findOneByProperties(properties, Auditor.class);
			}

			if (auditor == null) {
				auditor = modelService.create(Auditor.class);
				auditor.setUuid(model.getUuid());
				auditor.setId(model.getId());
				auditor.setName(model.getName());
			} else {
				Date lastModifiedDate = new Date();
				if (StringUtils.equals(model.getId(), auditor.getId()) == Boolean.FALSE) {
					auditor.setId(model.getId());
					auditor.setLastModifiedDate(lastModifiedDate);
				}

				if (StringUtils.equals(model.getName(), auditor.getName()) == Boolean.FALSE) {
					auditor.setName(model.getName());
					auditor.setLastModifiedDate(lastModifiedDate);
				}
			}

			modelService.saveEntity(auditor);

			return auditor;
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
}
