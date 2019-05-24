
package com.beanframework.console.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.console.listener.AdminImportListener;
import com.beanframework.console.listener.ConfigurationImportListener;
import com.beanframework.console.listener.CronjobImportListener;
import com.beanframework.console.listener.CustomerImportListener;
import com.beanframework.console.listener.DynamicFieldImportListener;
import com.beanframework.console.listener.DynamicFieldSlotImportListener;
import com.beanframework.console.listener.DynamicFieldTemplateImportListener;
import com.beanframework.console.listener.EmployeeImportListener;
import com.beanframework.console.listener.EnumerationImportListener;
import com.beanframework.console.listener.ImexImportListener;
import com.beanframework.console.listener.LanguageImportListener;
import com.beanframework.console.listener.MediaImportListener;
import com.beanframework.console.listener.MenuImportListener;
import com.beanframework.console.listener.SiteImportListener;
import com.beanframework.console.listener.UserAuthorityImportListener;
import com.beanframework.console.listener.UserGroupImportListener;
import com.beanframework.console.listener.UserPermissionImportListener;
import com.beanframework.console.listener.UserRightImportListener;
import com.beanframework.console.listener.VendorImportListener;
import com.beanframework.console.listener.WorkflowImportListener;
import com.beanframework.imex.registry.ImportListenerRegistry;

@Configuration
public class ConsoleImportListenerConfig implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private ImportListenerRegistry importListenerRegistry;

	@Value("#{'${module.console.import.listener.types}'.split(',')}")
	private List<String> importListenerTypesList;

	@Bean
	public AdminImportListener adminImportListener() {
		return new AdminImportListener();
	}

	@Bean
	public ConfigurationImportListener configurationImportListener() {
		return new ConfigurationImportListener();
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
	public EmployeeImportListener employeeImportListener() {
		return new EmployeeImportListener();
	}

	@Bean
	public LanguageImportListener languageImportListener() {
		return new LanguageImportListener();
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

		if (importListenerTypesList.contains(ConsoleImportListenerConstants.AdminImport.TYPE))
			importListenerRegistry.addListener(adminImportListener());

		if (importListenerTypesList.contains(ConsoleImportListenerConstants.ConfigurationImport.TYPE))
			importListenerRegistry.addListener(configurationImportListener());

		if (importListenerTypesList.contains(ConsoleImportListenerConstants.CronjobImport.TYPE))
			importListenerRegistry.addListener(cronjobImportListener());

		if (importListenerTypesList.contains(ConsoleImportListenerConstants.CustomerImport.TYPE))
			importListenerRegistry.addListener(customerImportListener());

		if (importListenerTypesList.contains(ConsoleImportListenerConstants.VendorImport.TYPE))
			importListenerRegistry.addListener(vendorImportListener());

		if (importListenerTypesList.contains(ConsoleImportListenerConstants.EnumerationImport.TYPE))
			importListenerRegistry.addListener(enumerationImportListener());

		if (importListenerTypesList.contains(ConsoleImportListenerConstants.DynamicFieldImport.TYPE))
			importListenerRegistry.addListener(dynamicFieldImportListener());

		if (importListenerTypesList.contains(ConsoleImportListenerConstants.DynamicFieldSlotImport.TYPE))
			importListenerRegistry.addListener(dynamicFieldSlotImportListener());

		if (importListenerTypesList.contains(ConsoleImportListenerConstants.DynamicFieldTemplateImport.TYPE))
			importListenerRegistry.addListener(dynamicFieldTemplateImportListener());

		if (importListenerTypesList.contains(ConsoleImportListenerConstants.EmployeeImport.TYPE))
			importListenerRegistry.addListener(employeeImportListener());

		if (importListenerTypesList.contains(ConsoleImportListenerConstants.LanguageImport.TYPE))
			importListenerRegistry.addListener(languageImportListener());

		if (importListenerTypesList.contains(ConsoleImportListenerConstants.MenuImport.TYPE))
			importListenerRegistry.addListener(menuImportListener());

		if (importListenerTypesList.contains(ConsoleImportListenerConstants.UserAuthorityImport.TYPE))
			importListenerRegistry.addListener(userAuthorityImportListener());

		if (importListenerTypesList.contains(ConsoleImportListenerConstants.UserGroupImport.TYPE))
			importListenerRegistry.addListener(userGroupImportListener());

		if (importListenerTypesList.contains(ConsoleImportListenerConstants.UserPermissionImport.TYPE))
			importListenerRegistry.addListener(userPermissionImportListener());

		if (importListenerTypesList.contains(ConsoleImportListenerConstants.UserRightImport.TYPE))
			importListenerRegistry.addListener(userRightImportListener());

		if (importListenerTypesList.contains(ConsoleImportListenerConstants.SiteImport.TYPE))
			importListenerRegistry.addListener(siteImportListener());

		if (importListenerTypesList.contains(ConsoleImportListenerConstants.WorkflowImport.TYPE))
			importListenerRegistry.addListener(workflowImportListener());

		if (importListenerTypesList.contains(ConsoleImportListenerConstants.MediaImport.TYPE))
			importListenerRegistry.addListener(mediaImportListener());

		if (importListenerTypesList.contains(ConsoleImportListenerConstants.ImexImport.TYPE))
			importListenerRegistry.addListener(imexImportListener());
	}
}