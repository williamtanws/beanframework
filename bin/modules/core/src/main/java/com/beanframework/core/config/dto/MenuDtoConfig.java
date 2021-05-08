package com.beanframework.core.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.MenuBreadcrumbsDtoConverter;
import com.beanframework.core.converter.dto.MenuDtoConverter;
import com.beanframework.core.converter.dto.MenuTreeByCurrentUserDtoConverter;
import com.beanframework.core.converter.dto.MenuTreeDtoConverter;
import com.beanframework.core.converter.populator.MenuBreadcrumbsPopulator;
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
	public MenuDtoConverter dtoMenuConverter() {
		MenuDtoConverter converter = new MenuDtoConverter();
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
	public MenuTreeDtoConverter dtoMenuTreeConverter() {
		MenuTreeDtoConverter converter = new MenuTreeDtoConverter();
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
	public MenuTreeByCurrentUserDtoConverter dtoMenuTreeByCurrentUserConverter() {
		MenuTreeByCurrentUserDtoConverter converter = new MenuTreeByCurrentUserDtoConverter();
		converter.addPopulator(menuTreeByCurrentUserPopulator());
		return converter;
	}

	// Menu Breadcrumbs
	@Bean
	public MenuBreadcrumbsPopulator menuBreadcrumbsPopulator() {
		return new MenuBreadcrumbsPopulator();
	}

	@Bean
	public MenuBreadcrumbsDtoConverter dtoMenuBreadcrumbsConverter() {
		MenuBreadcrumbsDtoConverter converter = new MenuBreadcrumbsDtoConverter();
		converter.addPopulator(menuBreadcrumbsPopulator());
		return converter;
	}
}
