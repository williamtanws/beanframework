package com.beanframework.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.csv.AddressCsvEntityConverter;
import com.beanframework.core.converter.entity.csv.CompanyCsvEntityConverter;
import com.beanframework.core.converter.entity.csv.ConfigurationCsvEntityConverter;
import com.beanframework.core.converter.entity.csv.CountryCsvEntityConverter;
import com.beanframework.core.converter.entity.csv.CronjobCsvEntityConverter;
import com.beanframework.core.converter.entity.csv.CurrencyCsvEntityConverter;
import com.beanframework.core.converter.entity.csv.CustomerCsvEntityConverter;
import com.beanframework.core.converter.entity.csv.DynamicFieldCsvEntityConverter;
import com.beanframework.core.converter.entity.csv.DynamicFieldSlotCsvEntityConverter;
import com.beanframework.core.converter.entity.csv.DynamicFieldTemplateCsvEntityConverter;
import com.beanframework.core.converter.entity.csv.EmployeeCsvEntityConverter;
import com.beanframework.core.converter.entity.csv.EnumerationCsvEntityConverter;
import com.beanframework.core.converter.entity.csv.ImexCsvEntityConverter;
import com.beanframework.core.converter.entity.csv.LanguageCsvEntityConverter;
import com.beanframework.core.converter.entity.csv.MediaCsvEntityConverter;
import com.beanframework.core.converter.entity.csv.MenuCsvEntityConverter;
import com.beanframework.core.converter.entity.csv.RegionCsvEntityConverter;
import com.beanframework.core.converter.entity.csv.SiteCsvEntityConverter;
import com.beanframework.core.converter.entity.csv.UserGroupCsvEntityConverter;
import com.beanframework.core.converter.entity.csv.UserPermissionCsvEntityConverter;
import com.beanframework.core.converter.entity.csv.UserRightCsvEntityConverter;
import com.beanframework.core.converter.entity.csv.VendorCsvEntityConverter;
import com.beanframework.core.csv.AddressCsv;
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

@Configuration
public class CoreEntityCsvConverterConfig {

	@Bean
	public ConfigurationCsvEntityConverter entityCsvConfigurationConverter() {
		return new ConfigurationCsvEntityConverter();
	}

	@Bean
	public ConverterMapping entityCsvConfigurationConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvConfigurationConverter());
		mapping.setTypeCode(ConfigurationCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public CronjobCsvEntityConverter entityCsvCronjobConverter() {
		return new CronjobCsvEntityConverter();
	}

	@Bean
	public ConverterMapping entityCsvCronjobConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvCronjobConverter());
		mapping.setTypeCode(CronjobCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public CustomerCsvEntityConverter entityCsvCustomerConverter() {
		return new CustomerCsvEntityConverter();
	}

	@Bean
	public ConverterMapping entityCsvCustomerConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvCustomerConverter());
		mapping.setTypeCode(CustomerCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public DynamicFieldCsvEntityConverter entityCsvDynamicFieldConverter() {
		return new DynamicFieldCsvEntityConverter();
	}

	@Bean
	public ConverterMapping entityCsvDynamicFieldConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvDynamicFieldConverter());
		mapping.setTypeCode(DynamicFieldCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public DynamicFieldSlotCsvEntityConverter entityCsvDynamicFieldSlotConverter() {
		return new DynamicFieldSlotCsvEntityConverter();
	}

	@Bean
	public ConverterMapping entityCsvDynamicFieldSlotConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvDynamicFieldSlotConverter());
		mapping.setTypeCode(DynamicFieldSlotCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public DynamicFieldTemplateCsvEntityConverter entityCsvDynamicFieldTemplateConverter() {
		return new DynamicFieldTemplateCsvEntityConverter();
	}

	@Bean
	public ConverterMapping entityCsvDynamicFieldTemplateConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvDynamicFieldTemplateConverter());
		mapping.setTypeCode(DynamicFieldTemplateCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public EmployeeCsvEntityConverter entityCsvEmployeeConverter() {
		return new EmployeeCsvEntityConverter();
	}

	@Bean
	public ConverterMapping entityCsvEmployeeConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvEmployeeConverter());
		mapping.setTypeCode(EmployeeCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public EnumerationCsvEntityConverter entityCsvEnumerationConverter() {
		return new EnumerationCsvEntityConverter();
	}

	@Bean
	public ConverterMapping entityCsvEnumerationConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvEnumerationConverter());
		mapping.setTypeCode(EnumerationCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public ImexCsvEntityConverter entityCsvImexConverter() {
		return new ImexCsvEntityConverter();
	}

	@Bean
	public ConverterMapping entityCsvImexConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvImexConverter());
		mapping.setTypeCode(ImexCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public LanguageCsvEntityConverter entityCsvLanguageConverter() {
		return new LanguageCsvEntityConverter();
	}

	@Bean
	public ConverterMapping entityCsvLanguageConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvLanguageConverter());
		mapping.setTypeCode(LanguageCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public MediaCsvEntityConverter entityCsvMediaConverter() {
		return new MediaCsvEntityConverter();
	}

	@Bean
	public ConverterMapping entityCsvMediaConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvMediaConverter());
		mapping.setTypeCode(MediaCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public MenuCsvEntityConverter entityCsvMenuConverter() {
		return new MenuCsvEntityConverter();
	}

	@Bean
	public ConverterMapping entityCsvMenuConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvMenuConverter());
		mapping.setTypeCode(MenuCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public SiteCsvEntityConverter entityCsvSiteConverter() {
		return new SiteCsvEntityConverter();
	}

	@Bean
	public ConverterMapping entityCsvSiteConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvSiteConverter());
		mapping.setTypeCode(SiteCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public UserGroupCsvEntityConverter entityCsvUserGroupConverter() {
		return new UserGroupCsvEntityConverter();
	}

	@Bean
	public ConverterMapping entityCsvUserGroupConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvUserGroupConverter());
		mapping.setTypeCode(UserGroupCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public UserPermissionCsvEntityConverter entityCsvUserPermissionConverter() {
		return new UserPermissionCsvEntityConverter();
	}

	@Bean
	public ConverterMapping entityCsvUserPermissionConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvUserPermissionConverter());
		mapping.setTypeCode(UserPermissionCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public UserRightCsvEntityConverter entityCsvUserRightConverter() {
		return new UserRightCsvEntityConverter();
	}

	@Bean
	public ConverterMapping entityCsvUserRightConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvUserRightConverter());
		mapping.setTypeCode(UserRightCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public VendorCsvEntityConverter entityCsvVendorConverter() {
		return new VendorCsvEntityConverter();
	}

	@Bean
	public ConverterMapping entityCsvVendorConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvVendorConverter());
		mapping.setTypeCode(VendorCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public CountryCsvEntityConverter entityCsvCountryConverter() {
		return new CountryCsvEntityConverter();
	}

	@Bean
	public ConverterMapping entityCsvCountryConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvCountryConverter());
		mapping.setTypeCode(CountryCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public AddressCsvEntityConverter entityCsvAddressConverter() {
		return new AddressCsvEntityConverter();
	}

	@Bean
	public ConverterMapping entityCsvAddressConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvAddressConverter());
		mapping.setTypeCode(AddressCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public RegionCsvEntityConverter entityCsvRegionConverter() {
		return new RegionCsvEntityConverter();
	}

	@Bean
	public ConverterMapping entityCsvRegionConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvRegionConverter());
		mapping.setTypeCode(RegionCsv.class.getSimpleName());

		return mapping;
	}

	@Bean
	public CurrencyCsvEntityConverter entityCsvCurrencyConverter() {
		return new CurrencyCsvEntityConverter();
	}

	@Bean
	public ConverterMapping entityCsvCurrencyConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvCurrencyConverter());
		mapping.setTypeCode(CurrencyCsv.class.getSimpleName());

		return mapping;
	}
	
	@Bean
	public CompanyCsvEntityConverter entityCsvCompanyConverter() {
		return new CompanyCsvEntityConverter();
	}

	@Bean
	public ConverterMapping entityCsvCompanyConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCsvCompanyConverter());
		mapping.setTypeCode(CompanyCsv.class.getSimpleName());

		return mapping;
	}
}
