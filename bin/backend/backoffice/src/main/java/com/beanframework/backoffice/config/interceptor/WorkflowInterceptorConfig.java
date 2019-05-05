package com.beanframework.backoffice.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.cms.domain.Workflow;
import com.beanframework.cms.interceptor.WorkflowInitialDefaultsInterceptor;
import com.beanframework.cms.interceptor.WorkflowLoadInterceptor;
import com.beanframework.cms.interceptor.WorkflowPrepareInterceptor;
import com.beanframework.cms.interceptor.WorkflowRemoveInterceptor;
import com.beanframework.cms.interceptor.WorkflowValidateInterceptor;
import com.beanframework.common.interceptor.InterceptorMapping;

@Configuration
public class WorkflowInterceptorConfig {

	//////////////////////////////////
	// Initial Defaults Interceptor //
	//////////////////////////////////

	@Bean
	public WorkflowInitialDefaultsInterceptor workflowInitialDefaultsInterceptor() {
		return new WorkflowInitialDefaultsInterceptor();
	}

	@Bean
	public InterceptorMapping workflowInitialDefaultsInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(workflowInitialDefaultsInterceptor());
		mapping.setTypeCode(Workflow.class.getSimpleName());

		return mapping;
	}

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public WorkflowLoadInterceptor workflowLoadInterceptor() {
		return new WorkflowLoadInterceptor();
	}

	@Bean
	public InterceptorMapping workflowLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(workflowLoadInterceptor());
		mapping.setTypeCode(Workflow.class.getSimpleName());

		return mapping;
	}

	/////////////////////////
	// Prepare Interceptor //
	/////////////////////////

	@Bean
	public WorkflowPrepareInterceptor workflowPrepareInterceptor() {
		return new WorkflowPrepareInterceptor();
	}

	@Bean
	public InterceptorMapping workflowPrepareInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(workflowPrepareInterceptor());
		mapping.setTypeCode(Workflow.class.getSimpleName());

		return mapping;
	}

	//////////////////////////
	// Validate Interceptor //
	//////////////////////////

	@Bean
	public WorkflowValidateInterceptor workflowValidateInterceptor() {
		return new WorkflowValidateInterceptor();
	}

	@Bean
	public InterceptorMapping workflowValidateInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(workflowValidateInterceptor());
		mapping.setTypeCode(Workflow.class.getSimpleName());

		return mapping;
	}

	////////////////////////
	// Remove Interceptor //
	////////////////////////

	@Bean
	public WorkflowRemoveInterceptor workflowRemoveInterceptor() {
		return new WorkflowRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping workflowRemoveInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(workflowRemoveInterceptor());
		mapping.setTypeCode(Workflow.class.getSimpleName());

		return mapping;
	}
}
