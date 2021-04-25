package com.beanframework.core.listener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.beanframework.common.registry.AfterRemoveListenerRegistry;
import com.beanframework.common.registry.AfterSaveListenerRegistry;
import com.beanframework.common.registry.BeforeRemoveListenerRegistry;
import com.beanframework.common.registry.BeforeSaveListenerRegistry;
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
import com.beanframework.imex.ImexConstants;
import com.beanframework.imex.registry.ImportListenerRegistry;

@Component
public class CoreListener implements ApplicationListener<ApplicationReadyEvent> {

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
	public void onApplicationEvent(ApplicationReadyEvent event) {

		beforeSaveListenerRegistry.addListener(coreBeforeSaveListener);
		afterSaveListenerRegistry.addListener(coreAfterSaveListener);
		beforeRemoveListenerRegistry.addListener(coreBeforeRemoveListener);
		afterRemoveListenerRegistry.addListener(coreAfterRemoveListener);

		if (importListenerTypesList.contains(ImportListenerConstants.ConfigurationImport.TYPE))
			importListenerRegistry.addListener(configurationImportListener);

		if (importListenerTypesList.contains(ImportListenerConstants.LanguageImport.TYPE))
			importListenerRegistry.addListener(languageImportListener);

		if (importListenerTypesList.contains(ImportListenerConstants.CurrencyImport.TYPE))
			importListenerRegistry.addListener(currencyImportListener);

		if (importListenerTypesList.contains(ImportListenerConstants.CountryImport.TYPE))
			importListenerRegistry.addListener(countryImportListener);

		if (importListenerTypesList.contains(ImportListenerConstants.RegionImport.TYPE))
			importListenerRegistry.addListener(regionImportListener);

		if (importListenerTypesList.contains(ImportListenerConstants.CronjobImport.TYPE))
			importListenerRegistry.addListener(cronjobImportListener);

		if (importListenerTypesList.contains(ImportListenerConstants.CompanyImport.TYPE))
			importListenerRegistry.addListener(companyImportListener);

		if (importListenerTypesList.contains(ImportListenerConstants.AddressImport.TYPE))
			importListenerRegistry.addListener(addressImportListener);

		if (importListenerTypesList.contains(ImportListenerConstants.CustomerImport.TYPE))
			importListenerRegistry.addListener(customerImportListener);

		if (importListenerTypesList.contains(ImportListenerConstants.VendorImport.TYPE))
			importListenerRegistry.addListener(vendorImportListener);

		if (importListenerTypesList.contains(ImportListenerConstants.EnumerationImport.TYPE))
			importListenerRegistry.addListener(enumerationImportListener);

		if (importListenerTypesList.contains(ImportListenerConstants.DynamicFieldImport.TYPE))
			importListenerRegistry.addListener(dynamicFieldImportListener);

		if (importListenerTypesList.contains(ImportListenerConstants.DynamicFieldSlotImport.TYPE))
			importListenerRegistry.addListener(dynamicFieldSlotImportListener);

		if (importListenerTypesList.contains(ImportListenerConstants.DynamicFieldTemplateImport.TYPE))
			importListenerRegistry.addListener(dynamicFieldTemplateImportListener);

		if (importListenerTypesList.contains(ImportListenerConstants.EmployeeImport.TYPE))
			importListenerRegistry.addListener(employeeImportListener);

		if (importListenerTypesList.contains(ImportListenerConstants.MenuImport.TYPE))
			importListenerRegistry.addListener(menuImportListener);

		if (importListenerTypesList.contains(ImportListenerConstants.UserAuthorityImport.TYPE))
			importListenerRegistry.addListener(userAuthorityImportListener);

		if (importListenerTypesList.contains(ImportListenerConstants.UserGroupImport.TYPE))
			importListenerRegistry.addListener(userGroupImportListener);

		if (importListenerTypesList.contains(ImportListenerConstants.UserPermissionImport.TYPE))
			importListenerRegistry.addListener(userPermissionImportListener);

		if (importListenerTypesList.contains(ImportListenerConstants.UserRightImport.TYPE))
			importListenerRegistry.addListener(userRightImportListener);

		if (importListenerTypesList.contains(ImportListenerConstants.SiteImport.TYPE))
			importListenerRegistry.addListener(siteImportListener);

		if (importListenerTypesList.contains(ImportListenerConstants.MediaImport.TYPE))
			importListenerRegistry.addListener(mediaImportListener);

		if (importListenerTypesList.contains(ImportListenerConstants.ImexImport.TYPE))
			importListenerRegistry.addListener(imexImportListener);
	}

}
