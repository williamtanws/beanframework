package com.beanframework.console.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.console.converter.EntityCsvAdminConverter;
import com.beanframework.console.converter.EntityCsvConfigurationConverter;
import com.beanframework.console.converter.EntityCsvCronjobConverter;
import com.beanframework.console.converter.EntityCsvCustomerConverter;
import com.beanframework.console.converter.EntityCsvDynamicFieldConverter;
import com.beanframework.console.converter.EntityCsvDynamicFieldSlotConverter;
import com.beanframework.console.converter.EntityCsvDynamicFieldTemplateConverter;
import com.beanframework.console.converter.EntityCsvEmployeeConverter;
import com.beanframework.console.converter.EntityCsvEnumerationConverter;
import com.beanframework.console.converter.EntityCsvImexConverter;
import com.beanframework.console.converter.EntityCsvLanguageConverter;
import com.beanframework.console.converter.EntityCsvMediaConverter;
import com.beanframework.console.converter.EntityCsvMenuConverter;
import com.beanframework.console.converter.EntityCsvSiteConverter;
import com.beanframework.console.converter.EntityCsvUserGroupConverter;
import com.beanframework.console.converter.EntityCsvUserPermissionConverter;
import com.beanframework.console.converter.EntityCsvUserRightConverter;
import com.beanframework.console.converter.EntityCsvVendorConverter;
import com.beanframework.console.converter.EntityCsvWorkflowConverter;
import com.beanframework.console.csv.AdminCsv;
import com.beanframework.console.csv.ConfigurationCsv;
import com.beanframework.console.csv.CronjobCsv;
import com.beanframework.console.csv.CustomerCsv;
import com.beanframework.console.csv.DynamicFieldCsv;
import com.beanframework.console.csv.DynamicFieldSlotCsv;
import com.beanframework.console.csv.DynamicFieldTemplateCsv;
import com.beanframework.console.csv.EmployeeCsv;
import com.beanframework.console.csv.EnumerationCsv;
import com.beanframework.console.csv.ImexCsv;
import com.beanframework.console.csv.LanguageCsv;
import com.beanframework.console.csv.MediaCsv;
import com.beanframework.console.csv.MenuCsv;
import com.beanframework.console.csv.SiteCsv;
import com.beanframework.console.csv.UserGroupCsv;
import com.beanframework.console.csv.UserPermissionCsv;
import com.beanframework.console.csv.UserRightCsv;
import com.beanframework.console.csv.VendorCsv;
import com.beanframework.console.csv.WorkflowCsv;

@Configuration
public class ConsoleEntityCsvConverterConfig {

	@Bean
	public EntityCsvAdminConverter entityCsvAdminConverter() {
		return new EntityCsvAdminConverter();
	}

	@Bean
	public ConverterMapping entityCsvAdminConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvAdminConverter());
		mapping.setTypeCode(AdminCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public EntityCsvConfigurationConverter entityCsvConfigurationConverter() {
		return new EntityCsvConfigurationConverter();
	}

	@Bean
	public ConverterMapping entityCsvConfigurationConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvConfigurationConverter());
		mapping.setTypeCode(ConfigurationCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public EntityCsvCronjobConverter entityCsvCronjobConverter() {
		return new EntityCsvCronjobConverter();
	}

	@Bean
	public ConverterMapping entityCsvCronjobConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvCronjobConverter());
		mapping.setTypeCode(CronjobCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public EntityCsvCustomerConverter entityCsvCustomerConverter() {
		return new EntityCsvCustomerConverter();
	}

	@Bean
	public ConverterMapping entityCsvCustomerConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvCustomerConverter());
		mapping.setTypeCode(CustomerCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public EntityCsvDynamicFieldConverter entityCsvDynamicFieldConverter() {
		return new EntityCsvDynamicFieldConverter();
	}

	@Bean
	public ConverterMapping entityCsvDynamicFieldConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvDynamicFieldConverter());
		mapping.setTypeCode(DynamicFieldCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public EntityCsvDynamicFieldSlotConverter entityCsvDynamicFieldSlotConverter() {
		return new EntityCsvDynamicFieldSlotConverter();
	}

	@Bean
	public ConverterMapping entityCsvDynamicFieldSlotConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvDynamicFieldSlotConverter());
		mapping.setTypeCode(DynamicFieldSlotCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public EntityCsvDynamicFieldTemplateConverter entityCsvDynamicFieldTemplateConverter() {
		return new EntityCsvDynamicFieldTemplateConverter();
	}

	@Bean
	public ConverterMapping entityCsvDynamicFieldTemplateConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvDynamicFieldTemplateConverter());
		mapping.setTypeCode(DynamicFieldTemplateCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public EntityCsvEmployeeConverter entityCsvEmployeeConverter() {
		return new EntityCsvEmployeeConverter();
	}

	@Bean
	public ConverterMapping entityCsvEmployeeConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvEmployeeConverter());
		mapping.setTypeCode(EmployeeCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public EntityCsvEnumerationConverter entityCsvEnumerationConverter() {
		return new EntityCsvEnumerationConverter();
	}

	@Bean
	public ConverterMapping entityCsvEnumerationConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvEnumerationConverter());
		mapping.setTypeCode(EnumerationCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public EntityCsvImexConverter entityCsvImexConverter() {
		return new EntityCsvImexConverter();
	}

	@Bean
	public ConverterMapping entityCsvImexConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvImexConverter());
		mapping.setTypeCode(ImexCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public EntityCsvLanguageConverter entityCsvLanguageConverter() {
		return new EntityCsvLanguageConverter();
	}

	@Bean
	public ConverterMapping entityCsvLanguageConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvLanguageConverter());
		mapping.setTypeCode(LanguageCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public EntityCsvMediaConverter entityCsvMediaConverter() {
		return new EntityCsvMediaConverter();
	}

	@Bean
	public ConverterMapping entityCsvMediaConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvMediaConverter());
		mapping.setTypeCode(MediaCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public EntityCsvMenuConverter entityCsvMenuConverter() {
		return new EntityCsvMenuConverter();
	}

	@Bean
	public ConverterMapping entityCsvMenuConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvMenuConverter());
		mapping.setTypeCode(MenuCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public EntityCsvSiteConverter entityCsvSiteConverter() {
		return new EntityCsvSiteConverter();
	}

	@Bean
	public ConverterMapping entityCsvSiteConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvSiteConverter());
		mapping.setTypeCode(SiteCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public EntityCsvUserGroupConverter entityCsvUserGroupConverter() {
		return new EntityCsvUserGroupConverter();
	}

	@Bean
	public ConverterMapping entityCsvUserGroupConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvUserGroupConverter());
		mapping.setTypeCode(UserGroupCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public EntityCsvUserPermissionConverter entityCsvUserPermissionConverter() {
		return new EntityCsvUserPermissionConverter();
	}

	@Bean
	public ConverterMapping entityCsvUserPermissionConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvUserPermissionConverter());
		mapping.setTypeCode(UserPermissionCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public EntityCsvUserRightConverter entityCsvUserRightConverter() {
		return new EntityCsvUserRightConverter();
	}

	@Bean
	public ConverterMapping entityCsvUserRightConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvUserRightConverter());
		mapping.setTypeCode(UserRightCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public EntityCsvVendorConverter entityCsvVendorConverter() {
		return new EntityCsvVendorConverter();
	}

	@Bean
	public ConverterMapping entityCsvVendorConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvVendorConverter());
		mapping.setTypeCode(VendorCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public EntityCsvWorkflowConverter entityCsvWorkflowConverter() {
		return new EntityCsvWorkflowConverter();
	}

	@Bean
	public ConverterMapping entityCsvWorkflowConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvWorkflowConverter());
		mapping.setTypeCode(WorkflowCsv.class.getSimpleName());

		return mapping;
	}
}
