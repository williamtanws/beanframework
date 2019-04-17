package com.beanframework.cronjob.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.cronjob.domain.Cronjob;

public class CronjobListLoadInterceptor extends AbstractLoadInterceptor<Cronjob> {

	@Override
	public Cronjob onLoad(Cronjob model, InterceptorContext context) throws InterceptorException {
		Cronjob prototype = new Cronjob();
		loadCommonProperties(model, prototype, context);
		prototype.setJobClass(model.getJobClass());
		prototype.setJobGroup(model.getJobGroup());
		prototype.setName(model.getName());
		prototype.setDescription(model.getDescription());
		prototype.setCronExpression(model.getCronExpression());
		prototype.setStartup(model.getStartup());
		prototype.setStatus(model.getStatus());
		prototype.setResult(model.getResult());
		prototype.setMessage(model.getMessage());
		prototype.setJobTrigger(model.getJobTrigger());
		prototype.setTriggerStartDate(model.getTriggerStartDate());
		prototype.setLastTriggeredDate(model.getLastTriggeredDate());
		prototype.setLastStartExecutedDate(model.getLastStartExecutedDate());
		prototype.setLastFinishExecutedDate(model.getLastFinishExecutedDate());
		
		return prototype;
	}

}
