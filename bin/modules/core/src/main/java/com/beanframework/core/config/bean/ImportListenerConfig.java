
package com.beanframework.core.config.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.core.listenerimport.AddressImportListener;
import com.beanframework.core.listenerimport.CompanyImportListener;
import com.beanframework.core.listenerimport.ConfigurationImportListener;
import com.beanframework.core.listenerimport.CountryImportListener;
import com.beanframework.core.listenerimport.CronjobImportListener;
import com.beanframework.core.listenerimport.CurrencyImportListener;
import com.beanframework.core.listenerimport.CustomerImportListener;
import com.beanframework.core.listenerimport.DynamicFieldImportListener;
import com.beanframework.core.listenerimport.DynamicFieldSlotImportListener;
import com.beanframework.core.listenerimport.DynamicFieldTemplateImportListener;
import com.beanframework.core.listenerimport.EmployeeImportListener;
import com.beanframework.core.listenerimport.EnumerationImportListener;
import com.beanframework.core.listenerimport.ImexImportListener;
import com.beanframework.core.listenerimport.LanguageImportListener;
import com.beanframework.core.listenerimport.MediaImportListener;
import com.beanframework.core.listenerimport.MenuImportListener;
import com.beanframework.core.listenerimport.RegionImportListener;
import com.beanframework.core.listenerimport.SiteImportListener;
import com.beanframework.core.listenerimport.UserAuthorityImportListener;
import com.beanframework.core.listenerimport.UserGroupImportListener;
import com.beanframework.core.listenerimport.UserPermissionImportListener;
import com.beanframework.core.listenerimport.UserRightImportListener;
import com.beanframework.core.listenerimport.VendorImportListener;

@Configuration
public class ImportListenerConfig {

	@Bean
	public ConfigurationImportListener configurationImportListener() {
		return new ConfigurationImportListener();
	}
	
	@Bean
	public LanguageImportListener languageImportListener() {
		return new LanguageImportListener();
	}
	
	@Bean
	public CurrencyImportListener currencyImportListener() {
		return new CurrencyImportListener();
	}
	
	@Bean
	public CountryImportListener countryImportListener() {
		return new CountryImportListener();
	}
	
	@Bean
	public RegionImportListener regionImportListener() {
		return new RegionImportListener();
	}

	@Bean
	public EnumerationImportListener enumerationImportListener() {
		return new EnumerationImportListener();
	}

	@Bean
	public DynamicFieldImportListener dynamicFieldImportListener() {
		return new DynamicFieldImportListener();
	}

	@Bean
	public DynamicFieldSlotImportListener dynamicFieldSlotImportListener() {
		return new DynamicFieldSlotImportListener();
	}

	@Bean
	public DynamicFieldTemplateImportListener dynamicFieldTemplateImportListener() {
		return new DynamicFieldTemplateImportListener();
	}
	
	@Bean
	public CompanyImportListener companyImportListener() {
		return new CompanyImportListener();
	}
	
	@Bean
	public AddressImportListener addressImportListener() {
		return new AddressImportListener();
	}

	@Bean
	public EmployeeImportListener employeeImportListener() {
		return new EmployeeImportListener();
	}

	@Bean
	public MenuImportListener menuImportListener() {
		return new MenuImportListener();
	}

	@Bean
	public UserPermissionImportListener userPermissionImportListener() {
		return new UserPermissionImportListener();
	}

	@Bean
	public UserGroupImportListener userGroupImportListener() {
		return new UserGroupImportListener();
	}

	@Bean
	public UserRightImportListener userRightImportListener() {
		return new UserRightImportListener();
	}

	@Bean
	public UserAuthorityImportListener userAuthorityImportListener() {
		return new UserAuthorityImportListener();
	}

	@Bean
	public CustomerImportListener customerImportListener() {
		return new CustomerImportListener();
	}
	
	@Bean
	public VendorImportListener vendorImportListener() {
		return new VendorImportListener();
	}

	@Bean
	public CronjobImportListener cronjobImportListener() {
		return new CronjobImportListener();
	}

	@Bean
	public SiteImportListener siteImportListener() {
		return new SiteImportListener();
	}

	@Bean
	public MediaImportListener mediaImportListener() {
		return new MediaImportListener();
	}
	
	@Bean
	public ImexImportListener imexImportListener() {
		return new ImexImportListener();
	}
}