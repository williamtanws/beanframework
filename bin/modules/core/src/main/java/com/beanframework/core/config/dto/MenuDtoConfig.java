package com.beanframework.core.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.DtoMenuConverter;
import com.beanframework.core.converter.dto.DtoMenuTreeByCurrentUserConverter;
import com.beanframework.core.converter.dto.DtoMenuTreeConverter;
import com.beanframework.core.converter.populator.MenuPopulator;
import com.beanframework.core.converter.populator.MenuTreeByCurrentUserPopulator;
import com.beanframework.core.converter.populator.MenuTreePopulator;
import com.beanframework.core.data.MenuDto;

@Configuration
public class MenuDtoConfig {

	// Menu
	@Bean
	public MenuPopulator menuPopulator() {
		return new MenuPopulator();
	}

	@Bean
	public DtoMenuConverter dtoMenuConverter() {
		DtoMenuConverter converter = new DtoMenuConverter();
		converter.addPopulator(menuPopulator());
		return converter;
	}

	@Bean
	public ConverterMapping dtoMenuConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoMenuConverter());
		mapping.setTypeCode(MenuDto.class.getSimpleName());

		return mapping;
	}
	
	// Menu Tree
	@Bean
	public MenuTreePopulator menuTreePopulator() {
		return new MenuTreePopulator();
	}

	@Bean
	public DtoMenuTreeConverter dtoMenuTreeConverter() {
		DtoMenuTreeConverter converter = new DtoMenuTreeConverter();
		converter.addPopulator(menuTreePopulator());
		return converter;
	}

	@Bean
	public ConverterMapping dtoMenuTreeConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoMenuTreeConverter());
		mapping.setTypeCode(MenuDto.class.getSimpleName());

		return mapping;
	}
	
	// Menu Tree By Current User
	@Bean
	public MenuTreeByCurrentUserPopulator menuTreeByCurrentUserPopulator() {
		return new MenuTreeByCurrentUserPopulator();
	}

	@Bean
	public DtoMenuTreeByCurrentUserConverter dtoMenuTreeByCurrentUserConverter() {
		DtoMenuTreeByCurrentUserConverter converter = new DtoMenuTreeByCurrentUserConverter();
		converter.addPopulator(menuTreeByCurrentUserPopulator());
		return converter;
	}
}
