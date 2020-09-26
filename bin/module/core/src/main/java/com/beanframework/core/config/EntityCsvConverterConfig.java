package com.beanframework.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.csv.EntityCsvAddressConverter;
import com.beanframework.core.converter.entity.csv.EntityCsvAdminConverter;
import com.beanframework.core.converter.entity.csv.EntityCsvCompanyConverter;
import com.beanframework.core.converter.entity.csv.EntityCsvConfigurationConverter;
import com.beanframework.core.converter.entity.csv.EntityCsvCountryConverter;
import com.beanframework.core.converter.entity.csv.EntityCsvCronjobConverter;
import com.beanframework.core.converter.entity.csv.EntityCsvCurrencyConverter;
import com.beanframework.core.converter.entity.csv.EntityCsvCustomerConverter;
import com.beanframework.core.converter.entity.csv.EntityCsvDynamicFieldConverter;
import com.beanframework.core.converter.entity.csv.EntityCsvDynamicFieldSlotConverter;
import com.beanframework.core.converter.entity.csv.EntityCsvDynamicFieldTemplateConverter;
import com.beanframework.core.converter.entity.csv.EntityCsvEmployeeConverter;
import com.beanframework.core.converter.entity.csv.EntityCsvEnumerationConverter;
import com.beanframework.core.converter.entity.csv.EntityCsvImexConverter;
import com.beanframework.core.converter.entity.csv.EntityCsvLanguageConverter;
import com.beanframework.core.converter.entity.csv.EntityCsvMediaConverter;
import com.beanframework.core.converter.entity.csv.EntityCsvMenuConverter;
import com.beanframework.core.converter.entity.csv.EntityCsvRegionConverter;
import com.beanframework.core.converter.entity.csv.EntityCsvSiteConverter;
import com.beanframework.core.converter.entity.csv.EntityCsvUserGroupConverter;
import com.beanframework.core.converter.entity.csv.EntityCsvUserPermissionConverter;
import com.beanframework.core.converter.entity.csv.EntityCsvUserRightConverter;
import com.beanframework.core.converter.entity.csv.EntityCsvVendorConverter;
import com.beanframework.core.converter.entity.csv.EntityCsvWorkflowConverter;
import com.beanframework.core.csv.AddressCsv;
import com.beanframework.core.csv.AdminCsv;
import com.beanframework.core.csv.CompanyCsv;
import com.beanframework.core.csv.ConfigurationCsv;
import com.beanframework.core.csv.CountryCsv;
import com.beanframework.core.csv.CronjobCsv;
import com.beanframework.core.csv.CurrencyCsv;
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
import com.beanframework.core.csv.RegionCsv;
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

	@Bean
	public EntityCsvCountryConverter entityCsvCountryConverter() {
		return new EntityCsvCountryConverter();
	}

	@Bean
	public ConverterMapping entityCsvCountryConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvCountryConverter());
		mapping.setTypeCode(CountryCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public EntityCsvAddressConverter entityCsvAddressConverter() {
		return new EntityCsvAddressConverter();
	}

	@Bean
	public ConverterMapping entityCsvAddressConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvAddressConverter());
		mapping.setTypeCode(AddressCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public EntityCsvRegionConverter entityCsvRegionConverter() {
		return new EntityCsvRegionConverter();
	}

	@Bean
	public ConverterMapping entityCsvRegionConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvRegionConverter());
		mapping.setTypeCode(RegionCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public EntityCsvCurrencyConverter entityCsvCurrencyConverter() {
		return new EntityCsvCurrencyConverter();
	}

	@Bean
	public ConverterMapping entityCsvCurrencyConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvCurrencyConverter());
		mapping.setTypeCode(CurrencyCsv.class.getSimpleName());

		return mapping;
	}
	
	@Bean
	public EntityCsvCompanyConverter entityCsvCompanyConverter() {
		return new EntityCsvCompanyConverter();
	}

	@Bean
	public ConverterMapping entityCsvCompanyConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvCompanyConverter());
		mapping.setTypeCode(CompanyCsv.class.getSimpleName());

		return mapping;
	}
}
