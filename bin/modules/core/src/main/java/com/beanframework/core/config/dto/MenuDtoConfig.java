package com.beanframework.core.config.dto;

import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	public MenuPopulator menuPopulator;
	@Autowired
	public MenuTreePopulator menuTreePopulator;
	@Autowired
	public MenuTreeByCurrentUserPopulator menuTreeByCurrentUserPopulator;
	@Autowired
	public MenuBreadcrumbsPopulator menuBreadcrumbsPopulator;

	@Bean
	public MenuDtoConverter menuDtoConverter() {
		MenuDtoConverter converter = new MenuDtoConverter();
		converter.addPopulator(menuPopulator);
		return converter;
	}

	@Bean
	public ConverterMapping menuDtoConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(menuDtoConverter());
		mapping.setTypeCode(MenuDto.class.getSimpleName());

		return mapping;
	}

	// Menu Tree
	@Bean
	public MenuTreeDtoConverter menuTreeDtoConverter() {
		MenuTreeDtoConverter converter = new MenuTreeDtoConverter();
		converter.addPopulator(menuTreePopulator);
		return converter;
	}

	// Menu Tree By Current User
	@Bean
	public MenuTreeByCurrentUserDtoConverter menuTreeByCurrentUserDtoConverter() {
		MenuTreeByCurrentUserDtoConverter converter = new MenuTreeByCurrentUserDtoConverter();
		converter.addPopulator(menuTreeByCurrentUserPopulator);
		return converter;
	}

	// Menu Breadcrumbs
	@Bean
	public MenuBreadcrumbsDtoConverter menuBreadcrumbsDtoConverter() {
		MenuBreadcrumbsDtoConverter converter = new MenuBreadcrumbsDtoConverter();
		converter.addPopulator(menuBreadcrumbsPopulator);
		return converter;
	}
}
