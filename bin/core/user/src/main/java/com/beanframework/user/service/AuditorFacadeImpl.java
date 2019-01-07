package com.beanframework.user.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.domain.Auditor;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.User;

@Component
public class AuditorFacadeImpl implements AuditorFacade {

	@Autowired
	private ModelService modelService;

	@Override
	public void save(User user) throws BusinessException {
		try {
			Auditor auditor = modelService.findOneEntityByUuid(user.getUuid(), Auditor.class);
			
			if(auditor == null) {
				auditor = new Auditor();
				auditor.setUuid(user.getUuid());
				auditor.setId(user.getId());
				auditor.setName(user.getName());

				modelService.saveEntity(auditor, Auditor.class);
			}
			else {
				if (StringUtils.isNotBlank(user.getId()) && StringUtils.equals(user.getId(), auditor.getId()) == false)
					auditor.setId(user.getId());

				if (StringUtils.equals(user.getName(), auditor.getName()) == false)
					auditor.setName(user.getName());

				modelService.saveEntity(auditor, Auditor.class);
			}			
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

}
