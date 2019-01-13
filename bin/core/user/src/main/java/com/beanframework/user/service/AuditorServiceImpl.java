package com.beanframework.user.service;

import java.util.Date;

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
	public Auditor create() throws Exception {
		return modelService.create(Auditor.class);
	}

	@Override
	public Auditor saveDto(User user) throws BusinessException {
		try {
			Auditor auditor = modelService.findOneEntityByUuid(user.getUuid(), Auditor.class);

			if (auditor == null) {
				auditor = modelService.create(Auditor.class);
				auditor.setUuid(user.getUuid());
				auditor.setId(user.getId());
				auditor.setName(user.getName());
			} else {
				Date lastModifiedDate = new Date();
				if (StringUtils.isNotBlank(user.getId()) && StringUtils.equals(user.getId(), auditor.getId()) == false) {
					auditor.setId(user.getId());
					auditor.setLastModifiedDate(lastModifiedDate);
				}

				if (StringUtils.equals(user.getName(), auditor.getName()) == false) {
					auditor.setName(user.getName());
					auditor.setLastModifiedDate(lastModifiedDate);
				}
			}

			modelService.saveEntity(auditor, Auditor.class);

			return auditor;

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}


}
