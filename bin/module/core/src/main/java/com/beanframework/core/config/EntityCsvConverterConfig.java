package com.beanframework.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.EntityCsvAdminConverter;
import com.beanframework.core.converter.EntityCsvConfigurationConverter;
import com.beanframework.core.converter.EntityCsvCronjobConverter;
import com.beanframework.core.converter.EntityCsvCustomerConverter;
import com.beanframework.core.converter.EntityCsvDynamicFieldConverter;
import com.beanframework.core.converter.EntityCsvDynamicFieldSlotConverter;
import com.beanframework.core.converter.EntityCsvDynamicFieldTemplateConverter;
import com.beanframework.core.converter.EntityCsvEmployeeConverter;
import com.beanframework.core.converter.EntityCsvEnumerationConverter;
import com.beanframework.core.converter.EntityCsvImexConverter;
import com.beanframework.core.converter.EntityCsvLanguageConverter;
import com.beanframework.core.converter.EntityCsvMediaConverter;
import com.beanframework.core.converter.EntityCsvMenuConverter;
import com.beanframework.core.converter.EntityCsvSiteConverter;
import com.beanframework.core.converter.EntityCsvUserGroupConverter;
import com.beanframework.core.converter.EntityCsvUserPermissionConverter;
import com.beanframework.core.converter.EntityCsvUserRightConverter;
import com.beanframework.core.converter.EntityCsvVendorConverter;
import com.beanframework.core.converter.EntityCsvWorkflowConverter;
import com.beanframework.core.csv.AdminCsv;
import com.beanframework.core.csv.ConfigurationCsv;
import com.beanframework.core.csv.CronjobCsv;
import com.beanframework.core.csv.CustomerCsv;
import com.beanframework.core.csv.DynamicFieldCsv;
import com.beanframework.core.csv.DynamicFieldSlotCsv;
import com.beanframework.core.csv.DynamicFieldTemplateCsv;
import com.beanframework.core.csv.EmployeeCsv;
import com.beanframework.core.csv.EnumerationCsv;
import com.beanframework.core.csv.ImexCsv;
import com.beanframework.core.csv.LanguageCsv;
import com.beanframework.core.csv.MediaCsv;
import com.beanframework.core.csv.MenuCsv;
import com.beanframework.core.csv.SiteCsv;
import com.beanframework.core.csv.UserGroupCsv;
import com.beanframework.core.csv.UserPermissionCsv;
import com.beanframework.core.csv.UserRightCsv;
import com.beanframework.core.csv.VendorCsv;
import com.beanframework.core.csv.WorkflowCsv;

@Configuration
public class EntityCsvConverterConfig {

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
