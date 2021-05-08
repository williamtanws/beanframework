package com.beanframework.core.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.beanframework.common.registry.AfterRemoveListenerRegistry;
import com.beanframework.common.registry.AfterSaveListenerRegistry;
import com.beanframework.common.registry.BeforeRemoveListenerRegistry;
import com.beanframework.common.registry.BeforeSaveListenerRegistry;
import com.beanframework.core.importlistener.AddressImportListener;
import com.beanframework.core.importlistener.CompanyImportListener;
import com.beanframework.core.importlistener.ConfigurationImportListener;
import com.beanframework.core.importlistener.CountryImportListener;
import com.beanframework.core.importlistener.CronjobImportListener;
import com.beanframework.core.importlistener.CurrencyImportListener;
import com.beanframework.core.importlistener.CustomerImportListener;
import com.beanframework.core.importlistener.DynamicFieldImportListener;
import com.beanframework.core.importlistener.DynamicFieldSlotImportListener;
import com.beanframework.core.importlistener.DynamicFieldTemplateImportListener;
import com.beanframework.core.importlistener.EmployeeImportListener;
import com.beanframework.core.importlistener.EnumerationImportListener;
import com.beanframework.core.importlistener.ImexImportListener;
import com.beanframework.core.importlistener.LanguageImportListener;
import com.beanframework.core.importlistener.MediaImportListener;
import com.beanframework.core.importlistener.MenuImportListener;
import com.beanframework.core.importlistener.RegionImportListener;
import com.beanframework.core.importlistener.SiteImportListener;
import com.beanframework.core.importlistener.UserAuthorityImportListener;
import com.beanframework.core.importlistener.UserGroupImportListener;
import com.beanframework.core.importlistener.UserPermissionImportListener;
import com.beanframework.core.importlistener.UserRightImportListener;
import com.beanframework.core.importlistener.VendorImportListener;
import com.beanframework.imex.registry.ImportListenerRegistry;

@Component
public class CoreListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private BeforeSaveListenerRegistry beforeSaveListenerRegistry;

	@Autowired
	private AfterSaveListenerRegistry afterSaveListenerRegistry;

	@Autowired
	private BeforeRemoveListenerRegistry beforeRemoveListenerRegistry;

	@Autowired
	private AfterRemoveListenerRegistry afterRemoveListenerRegistry;

	@Autowired
	private CoreBeforeSaveListener coreBeforeSaveListener;

	@Autowired
	private CoreAfterSaveListener coreAfterSaveListener;

	@Autowired
	private CoreBeforeRemoveListener coreBeforeRemoveListener;

	@Autowired
	private CoreAfterRemoveListener coreAfterRemoveListener;

	@Autowired
	private ImportListenerRegistry importListenerRegistry;

	@Autowired
	private ConfigurationImportListener configurationImportListener;

	@Autowired
	private LanguageImportListener languageImportListener;

	@Autowired
	private CurrencyImportListener currencyImportListener;

	@Autowired
	private CountryImportListener countryImportListener;

	@Autowired
	private RegionImportListener regionImportListener;

	@Autowired
	private CronjobImportListener cronjobImportListener;

	@Autowired
	private CompanyImportListener companyImportListener;

	@Autowired
	private AddressImportListener addressImportListener;

	@Autowired
	private CustomerImportListener customerImportListener;

	@Autowired
	private VendorImportListener vendorImportListener;

	@Autowired
	private EnumerationImportListener enumerationImportListener;

	@Autowired
	private DynamicFieldImportListener dynamicFieldImportListener;

	@Autowired
	private DynamicFieldSlotImportListener dynamicFieldSlotImportListener;

	@Autowired
	private DynamicFieldTemplateImportListener dynamicFieldTemplateImportListener;

	@Autowired
	private EmployeeImportListener employeeImportListener;

	@Autowired
	private MenuImportListener menuImportListener;

	@Autowired
	private UserAuthorityImportListener userAuthorityImportListener;

	@Autowired
	private UserGroupImportListener userGroupImportListener;

	@Autowired
	private UserPermissionImportListener userPermissionImportListener;

	@Autowired
	private UserRightImportListener userRightImportListener;

	@Autowired
	private SiteImportListener siteImportListener;

	@Autowired
	private MediaImportListener mediaImportListener;

	@Autowired
	private ImexImportListener imexImportListener;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		beforeSaveListenerRegistry.addListener(coreBeforeSaveListener);
		afterSaveListenerRegistry.addListener(coreAfterSaveListener);
		beforeRemoveListenerRegistry.addListener(coreBeforeRemoveListener);
		afterRemoveListenerRegistry.addListener(coreAfterRemoveListener);

		importListenerRegistry.addListener(configurationImportListener);
		importListenerRegistry.addListener(languageImportListener);
		importListenerRegistry.addListener(currencyImportListener);
		importListenerRegistry.addListener(countryImportListener);
		importListenerRegistry.addListener(regionImportListener);
		importListenerRegistry.addListener(cronjobImportListener);
		importListenerRegistry.addListener(companyImportListener);
		importListenerRegistry.addListener(addressImportListener);
		importListenerRegistry.addListener(customerImportListener);
		importListenerRegistry.addListener(vendorImportListener);
		importListenerRegistry.addListener(enumerationImportListener);
		importListenerRegistry.addListener(dynamicFieldImportListener);
		importListenerRegistry.addListener(dynamicFieldSlotImportListener);
		importListenerRegistry.addListener(dynamicFieldTemplateImportListener);
		importListenerRegistry.addListener(employeeImportListener);
		importListenerRegistry.addListener(menuImportListener);
		importListenerRegistry.addListener(userAuthorityImportListener);
		importListenerRegistry.addListener(userGroupImportListener);
		importListenerRegistry.addListener(userPermissionImportListener);
		importListenerRegistry.addListener(userRightImportListener);
		importListenerRegistry.addListener(siteImportListener);
		importListenerRegistry.addListener(mediaImportListener);
		importListenerRegistry.addListener(imexImportListener);
	}

}
