package com.beanframework.core.listener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.beanframework.common.registry.AfterRemoveListenerRegistry;
import com.beanframework.common.registry.AfterSaveListenerRegistry;
import com.beanframework.common.registry.BeforeRemoveListenerRegistry;
import com.beanframework.common.registry.BeforeSaveListenerRegistry;
import com.beanframework.core.CoreImportListenerConstants;
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
import com.beanframework.imex.ImexConstants;
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

	@Value(ImexConstants.IMEX_IMPORT_LISTENER_TYPES)
	private List<String> importListenerTypesList;

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

		if (importListenerTypesList.contains(CoreImportListenerConstants.ConfigurationImport.TYPE))
			importListenerRegistry.addListener(configurationImportListener);

		if (importListenerTypesList.contains(CoreImportListenerConstants.LanguageImport.TYPE))
			importListenerRegistry.addListener(languageImportListener);

		if (importListenerTypesList.contains(CoreImportListenerConstants.CurrencyImport.TYPE))
			importListenerRegistry.addListener(currencyImportListener);

		if (importListenerTypesList.contains(CoreImportListenerConstants.CountryImport.TYPE))
			importListenerRegistry.addListener(countryImportListener);

		if (importListenerTypesList.contains(CoreImportListenerConstants.RegionImport.TYPE))
			importListenerRegistry.addListener(regionImportListener);

		if (importListenerTypesList.contains(CoreImportListenerConstants.CronjobImport.TYPE))
			importListenerRegistry.addListener(cronjobImportListener);

		if (importListenerTypesList.contains(CoreImportListenerConstants.CompanyImport.TYPE))
			importListenerRegistry.addListener(companyImportListener);

		if (importListenerTypesList.contains(CoreImportListenerConstants.AddressImport.TYPE))
			importListenerRegistry.addListener(addressImportListener);

		if (importListenerTypesList.contains(CoreImportListenerConstants.CustomerImport.TYPE))
			importListenerRegistry.addListener(customerImportListener);

		if (importListenerTypesList.contains(CoreImportListenerConstants.VendorImport.TYPE))
			importListenerRegistry.addListener(vendorImportListener);

		if (importListenerTypesList.contains(CoreImportListenerConstants.EnumerationImport.TYPE))
			importListenerRegistry.addListener(enumerationImportListener);

		if (importListenerTypesList.contains(CoreImportListenerConstants.DynamicFieldImport.TYPE))
			importListenerRegistry.addListener(dynamicFieldImportListener);

		if (importListenerTypesList.contains(CoreImportListenerConstants.DynamicFieldSlotImport.TYPE))
			importListenerRegistry.addListener(dynamicFieldSlotImportListener);

		if (importListenerTypesList.contains(CoreImportListenerConstants.DynamicFieldTemplateImport.TYPE))
			importListenerRegistry.addListener(dynamicFieldTemplateImportListener);

		if (importListenerTypesList.contains(CoreImportListenerConstants.EmployeeImport.TYPE))
			importListenerRegistry.addListener(employeeImportListener);

		if (importListenerTypesList.contains(CoreImportListenerConstants.MenuImport.TYPE))
			importListenerRegistry.addListener(menuImportListener);

		if (importListenerTypesList.contains(CoreImportListenerConstants.UserAuthorityImport.TYPE))
			importListenerRegistry.addListener(userAuthorityImportListener);

		if (importListenerTypesList.contains(CoreImportListenerConstants.UserGroupImport.TYPE))
			importListenerRegistry.addListener(userGroupImportListener);

		if (importListenerTypesList.contains(CoreImportListenerConstants.UserPermissionImport.TYPE))
			importListenerRegistry.addListener(userPermissionImportListener);

		if (importListenerTypesList.contains(CoreImportListenerConstants.UserRightImport.TYPE))
			importListenerRegistry.addListener(userRightImportListener);

		if (importListenerTypesList.contains(CoreImportListenerConstants.SiteImport.TYPE))
			importListenerRegistry.addListener(siteImportListener);

		if (importListenerTypesList.contains(CoreImportListenerConstants.MediaImport.TYPE))
			importListenerRegistry.addListener(mediaImportListener);

		if (importListenerTypesList.contains(CoreImportListenerConstants.ImexImport.TYPE))
			importListenerRegistry.addListener(imexImportListener);
	}

}
