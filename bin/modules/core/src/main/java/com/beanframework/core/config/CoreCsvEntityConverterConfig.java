package com.beanframework.core.config;

import org.springframework.beans.factory.annotation.Autowired;
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
public class CoreCsvEntityConverterConfig {

  @Autowired
  public ConfigurationCsvEntityConverter configurationCsvEntityConverter;

  @Autowired
  public CronjobCsvEntityConverter cronjobCsvEntityConverter;

  @Autowired
  public CustomerCsvEntityConverter customerCsvEntityConverter;

  @Autowired
  public DynamicFieldCsvEntityConverter dynamicFieldCsvEntityConverter;

  @Autowired
  public DynamicFieldSlotCsvEntityConverter dynamicFieldSlotCsvEntityConverter;

  @Autowired
  public DynamicFieldTemplateCsvEntityConverter dynamicFieldTemplateCsvEntityConverter;

  @Autowired
  public EmployeeCsvEntityConverter employeeCsvEntityConverter;

  @Autowired
  public EnumerationCsvEntityConverter enumerationCsvEntityConverter;

  @Autowired
  public ImexCsvEntityConverter imexCsvEntityConverter;

  @Autowired
  public LanguageCsvEntityConverter languageCsvEntityConverter;

  @Autowired
  public MediaCsvEntityConverter mediaCsvEntityConverter;

  @Autowired
  public MenuCsvEntityConverter menuCsvEntityConverter;

  @Autowired
  public SiteCsvEntityConverter siteCsvEntityConverter;

  @Autowired
  public UserGroupCsvEntityConverter userGroupCsvEntityConverter;

  @Autowired
  public UserPermissionCsvEntityConverter userPermissionCsvEntityConverter;

  @Autowired
  public UserRightCsvEntityConverter userRightCsvEntityConverter;

  @Autowired
  public VendorCsvEntityConverter vendorCsvEntityConverter;

  @Autowired
  public CountryCsvEntityConverter countryCsvEntityConverter;

  @Autowired
  public AddressCsvEntityConverter addressCsvEntityConverter;

  @Autowired
  public RegionCsvEntityConverter regionCsvEntityConverter;

  @Autowired
  public CurrencyCsvEntityConverter currencyCsvEntityConverter;

  @Autowired
  public CompanyCsvEntityConverter companyCsvEntityConverter;

  @Bean
  public ConverterMapping configurationCsvEntityConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(configurationCsvEntityConverter);
    mapping.setTypeCode(ConfigurationCsv.class.getSimpleName());

    return mapping;
  }

  @Bean
  public ConverterMapping cronjobCsvEntityConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(cronjobCsvEntityConverter);
    mapping.setTypeCode(CronjobCsv.class.getSimpleName());

    return mapping;
  }

  @Bean
  public ConverterMapping customerCsvEntityConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(customerCsvEntityConverter);
    mapping.setTypeCode(CustomerCsv.class.getSimpleName());

    return mapping;
  }

  @Bean
  public ConverterMapping dynamicFieldCsvEntityConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(dynamicFieldCsvEntityConverter);
    mapping.setTypeCode(DynamicFieldCsv.class.getSimpleName());

    return mapping;
  }

  @Bean
  public ConverterMapping dynamicFieldSlotCsvEntityConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(dynamicFieldSlotCsvEntityConverter);
    mapping.setTypeCode(DynamicFieldSlotCsv.class.getSimpleName());

    return mapping;
  }

  @Bean
  public ConverterMapping dynamicFieldTemplateCsvEntityConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(dynamicFieldTemplateCsvEntityConverter);
    mapping.setTypeCode(DynamicFieldTemplateCsv.class.getSimpleName());

    return mapping;
  }

  @Bean
  public ConverterMapping employeeCsvEntityConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(employeeCsvEntityConverter);
    mapping.setTypeCode(EmployeeCsv.class.getSimpleName());

    return mapping;
  }

  @Bean
  public ConverterMapping enumerationCsvEntityConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(enumerationCsvEntityConverter);
    mapping.setTypeCode(EnumerationCsv.class.getSimpleName());

    return mapping;
  }

  @Bean
  public ConverterMapping imexCsvEntityConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(imexCsvEntityConverter);
    mapping.setTypeCode(ImexCsv.class.getSimpleName());

    return mapping;
  }

  @Bean
  public ConverterMapping languageCsvEntityConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(languageCsvEntityConverter);
    mapping.setTypeCode(LanguageCsv.class.getSimpleName());

    return mapping;
  }

  @Bean
  public ConverterMapping mediaCsvEntityConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(mediaCsvEntityConverter);
    mapping.setTypeCode(MediaCsv.class.getSimpleName());

    return mapping;
  }

  @Bean
  public ConverterMapping menuCsvEntityConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(menuCsvEntityConverter);
    mapping.setTypeCode(MenuCsv.class.getSimpleName());

    return mapping;
  }

  @Bean
  public ConverterMapping siteCsvEntityConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(siteCsvEntityConverter);
    mapping.setTypeCode(SiteCsv.class.getSimpleName());

    return mapping;
  }

  @Bean
  public ConverterMapping userGroupCsvEntityConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(userGroupCsvEntityConverter);
    mapping.setTypeCode(UserGroupCsv.class.getSimpleName());

    return mapping;
  }

  @Bean
  public ConverterMapping userPermissionCsvEntityConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(userPermissionCsvEntityConverter);
    mapping.setTypeCode(UserPermissionCsv.class.getSimpleName());

    return mapping;
  }

  @Bean
  public ConverterMapping userRightCsvEntityConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(userRightCsvEntityConverter);
    mapping.setTypeCode(UserRightCsv.class.getSimpleName());

    return mapping;
  }

  @Bean
  public ConverterMapping vendorCsvEntityConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(vendorCsvEntityConverter);
    mapping.setTypeCode(VendorCsv.class.getSimpleName());

    return mapping;
  }

  @Bean
  public ConverterMapping countryCsvEntityConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(countryCsvEntityConverter);
    mapping.setTypeCode(CountryCsv.class.getSimpleName());

    return mapping;
  }

  @Bean
  public ConverterMapping addressCsvEntityConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(addressCsvEntityConverter);
    mapping.setTypeCode(AddressCsv.class.getSimpleName());

    return mapping;
  }

  @Bean
  public ConverterMapping regionCsvEntityConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(regionCsvEntityConverter);
    mapping.setTypeCode(RegionCsv.class.getSimpleName());

    return mapping;
  }

  @Bean
  public ConverterMapping currencyCsvEntityConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(currencyCsvEntityConverter);
    mapping.setTypeCode(CurrencyCsv.class.getSimpleName());

    return mapping;
  }

  @Bean
  public ConverterMapping companyCsvEntityConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(companyCsvEntityConverter);
    mapping.setTypeCode(CompanyCsv.class.getSimpleName());

    return mapping;
  }
}
