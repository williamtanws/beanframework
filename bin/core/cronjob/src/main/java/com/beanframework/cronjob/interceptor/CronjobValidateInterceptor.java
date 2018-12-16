package com.beanframework.cronjob.interceptor;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.ValidateInterceptor;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.service.ModelService;
import com.beanframework.cronjob.CronjobConstants;
import com.beanframework.cronjob.domain.Cronjob;

public class CronjobValidateInterceptor implements ValidateInterceptor<Cronjob> {
	
	@Autowired
	private ModelService modelService;

	@Autowired
	private LocaleMessageService localeMessageService;

	@Override
	public void onValidate(Cronjob model) throws InterceptorException {
		
		if (model.getUuid() == null) {
			// Save new
			if (StringUtils.isEmpty(model.getId())) {
				throw new InterceptorException(localeMessageService.getMessage(CronjobConstants.Locale.ID_REQUIRED), this);
			} else {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Cronjob.ID, model.getId());
				boolean exists = modelService.existsByProperties(properties, Cronjob.class);
				if (exists == false) {
					throw new InterceptorException(localeMessageService.getMessage(CronjobConstants.Locale.ID_EXISTS),
							this);
				}
			}
			
			if (StringUtils.isEmpty(model.getJobGroup())) {
				throw new InterceptorException(localeMessageService.getMessage(CronjobConstants.Locale.CRONJOB_GROUP_REQUIRED));
			}

			if (StringUtils.isEmpty(model.getJobName())) {
				throw new InterceptorException(localeMessageService.getMessage(CronjobConstants.Locale.CRONJOB_NAME_REQUIRED));
			}
			
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Cronjob.JOB_GROUP, model.getJobGroup());
			properties.put(Cronjob.JOB_NAME, model.getJobName());
			
			Cronjob cronjob = modelService.findOneEntityByProperties(properties, Cronjob.class);

			if (cronjob != null) {
				throw new InterceptorException(localeMessageService.getMessage(CronjobConstants.Locale.CRONJOB_NAME_GROUP_EXISTS));
			}

		} else {
			// Update exists
			if (StringUtils.isNotEmpty(model.getId())) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Cronjob.ID, model.getId());
				Cronjob cronjob = modelService.findOneEntityByProperties(properties, Cronjob.class);
				if (cronjob != null) {
					if (!model.getUuid().equals(cronjob.getUuid())) {
						throw new InterceptorException(localeMessageService.getMessage(CronjobConstants.Locale.ID_EXISTS),
								this);
					}
				}
			}
		}
	}

}
