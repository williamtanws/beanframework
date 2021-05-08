package com.beanframework.trainingcore.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.training.domain.Training;
import com.beanframework.trainingcore.interceptor.TrainingInitialDefaultsInterceptor;
import com.beanframework.trainingcore.interceptor.TrainingLoadInterceptor;
import com.beanframework.trainingcore.interceptor.TrainingPrepareInterceptor;
import com.beanframework.trainingcore.interceptor.TrainingRemoveInterceptor;
import com.beanframework.trainingcore.interceptor.TrainingValidateInterceptor;

@Configuration
public class TrainingInterceptorConfig {

	//////////////////////////////////
	// Initial Defaults Interceptor //
	//////////////////////////////////

	@Bean
	public TrainingInitialDefaultsInterceptor trainingInitialDefaultsInterceptor() {
		return new TrainingInitialDefaultsInterceptor();
	}

	@Bean
	public InterceptorMapping trainingInitialDefaultsInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(trainingInitialDefaultsInterceptor());
		mapping.setTypeCode(Training.class.getSimpleName());

		return mapping;
	}

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public TrainingLoadInterceptor trainingLoadInterceptor() {
		return new TrainingLoadInterceptor();
	}

	@Bean
	public InterceptorMapping trainingLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(trainingLoadInterceptor());
		mapping.setTypeCode(Training.class.getSimpleName());

		return mapping;
	}

	/////////////////////////
	// Prepare Interceptor //
	/////////////////////////

	@Bean
	public TrainingPrepareInterceptor trainingPrepareInterceptor() {
		return new TrainingPrepareInterceptor();
	}

	@Bean
	public InterceptorMapping trainingPrepareInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(trainingPrepareInterceptor());
		mapping.setTypeCode(Training.class.getSimpleName());

		return mapping;
	}

	//////////////////////////
	// Validate Interceptor //
	//////////////////////////

	@Bean
	public TrainingValidateInterceptor trainingValidateInterceptor() {
		return new TrainingValidateInterceptor();
	}

	@Bean
	public InterceptorMapping trainingValidateInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(trainingValidateInterceptor());
		mapping.setTypeCode(Training.class.getSimpleName());

		return mapping;
	}

	////////////////////////
	// Remove Interceptor //
	////////////////////////

	@Bean
	public TrainingRemoveInterceptor trainingRemoveInterceptor() {
		return new TrainingRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping trainingRemoveInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(trainingRemoveInterceptor());
		mapping.setTypeCode(Training.class.getSimpleName());

		return mapping;
	}
}
