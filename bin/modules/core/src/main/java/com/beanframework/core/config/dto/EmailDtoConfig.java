package com.beanframework.core.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.DtoEmailConverter;
import com.beanframework.core.converter.populator.EmailPopulator;
import com.beanframework.core.data.EmailDto;

@Configuration
public class EmailDtoConfig {

	@Bean
	public EmailPopulator emailPopulator() {
		return new EmailPopulator();
	}

	@Bean
	public DtoEmailConverter dtoEmailConverter() {
		DtoEmailConverter converter = new DtoEmailConverter();
		converter.addPopulator(emailPopulator());
		return converter;
	}

	@Bean
	public ConverterMapping dtoEmailConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoEmailConverter());
		mapping.setTypeCode(EmailDto.class.getSimpleName());

		return mapping;
	}
}
