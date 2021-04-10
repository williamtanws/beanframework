
package com.beanframework.core.config.bean;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.core.ImportListenerConstants;
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
import com.beanframework.core.listenerimport.WorkflowImportListener;
import com.beanframework.imex.ImexConstants;
import com.beanframework.imex.registry.ImportListenerRegistry;

@Configuration
public class ImportListenerConfig implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private ImportListenerRegistry importListenerRegistry;

	@Value(ImexConstants.IMEX_IMPORT_LISTENER_TYPES)
	private List<String> importListenerTypesList;

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
	public WorkflowImportListener workflowImportListener() {
		return new WorkflowImportListener();
	}

	@Bean
	public MediaImportListener mediaImportListener() {
		return new MediaImportListener();
	}
	
	@Bean
	public ImexImportListener imexImportListener() {
		return new ImexImportListener();
	}

	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {

		if (importListenerTypesList.contains(ImportListenerConstants.ConfigurationImport.TYPE))
			importListenerRegistry.addListener(configurationImportListener());
		
		if (importListenerTypesList.contains(ImportListenerConstants.LanguageImport.TYPE))
			importListenerRegistry.addListener(languageImportListener());

		if (importListenerTypesList.contains(ImportListenerConstants.CurrencyImport.TYPE))
			importListenerRegistry.addListener(currencyImportListener());

		if (importListenerTypesList.contains(ImportListenerConstants.CountryImport.TYPE))
			importListenerRegistry.addListener(countryImportListener());

		if (importListenerTypesList.contains(ImportListenerConstants.RegionImport.TYPE))
			importListenerRegistry.addListener(regionImportListener());

		if (importListenerTypesList.contains(ImportListenerConstants.CronjobImport.TYPE))
			importListenerRegistry.addListener(cronjobImportListener());

		if (importListenerTypesList.contains(ImportListenerConstants.CompanyImport.TYPE))
			importListenerRegistry.addListener(companyImportListener());
		
		if (importListenerTypesList.contains(ImportListenerConstants.AddressImport.TYPE))
			importListenerRegistry.addListener(addressImportListener());

		if (importListenerTypesList.contains(ImportListenerConstants.CustomerImport.TYPE))
			importListenerRegistry.addListener(customerImportListener());

		if (importListenerTypesList.contains(ImportListenerConstants.VendorImport.TYPE))
			importListenerRegistry.addListener(vendorImportListener());

		if (importListenerTypesList.contains(ImportListenerConstants.EnumerationImport.TYPE))
			importListenerRegistry.addListener(enumerationImportListener());

		if (importListenerTypesList.contains(ImportListenerConstants.DynamicFieldImport.TYPE))
			importListenerRegistry.addListener(dynamicFieldImportListener());

		if (importListenerTypesList.contains(ImportListenerConstants.DynamicFieldSlotImport.TYPE))
			importListenerRegistry.addListener(dynamicFieldSlotImportListener());

		if (importListenerTypesList.contains(ImportListenerConstants.DynamicFieldTemplateImport.TYPE))
			importListenerRegistry.addListener(dynamicFieldTemplateImportListener());

		if (importListenerTypesList.contains(ImportListenerConstants.EmployeeImport.TYPE))
			importListenerRegistry.addListener(employeeImportListener());

		if (importListenerTypesList.contains(ImportListenerConstants.MenuImport.TYPE))
			importListenerRegistry.addListener(menuImportListener());

		if (importListenerTypesList.contains(ImportListenerConstants.UserAuthorityImport.TYPE))
			importListenerRegistry.addListener(userAuthorityImportListener());

		if (importListenerTypesList.contains(ImportListenerConstants.UserGroupImport.TYPE))
			importListenerRegistry.addListener(userGroupImportListener());

		if (importListenerTypesList.contains(ImportListenerConstants.UserPermissionImport.TYPE))
			importListenerRegistry.addListener(userPermissionImportListener());

		if (importListenerTypesList.contains(ImportListenerConstants.UserRightImport.TYPE))
			importListenerRegistry.addListener(userRightImportListener());

		if (importListenerTypesList.contains(ImportListenerConstants.SiteImport.TYPE))
			importListenerRegistry.addListener(siteImportListener());

		if (importListenerTypesList.contains(ImportListenerConstants.WorkflowImport.TYPE))
			importListenerRegistry.addListener(workflowImportListener());

		if (importListenerTypesList.contains(ImportListenerConstants.MediaImport.TYPE))
			importListenerRegistry.addListener(mediaImportListener());

		if (importListenerTypesList.contains(ImportListenerConstants.ImexImport.TYPE))
			importListenerRegistry.addListener(imexImportListener());
	}
}