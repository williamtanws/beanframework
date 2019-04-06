package com.beanframework.console.integration.handle;

import java.io.File;
import java.io.Reader;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.CharSequenceReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.beanframework.console.csv.AdminCsv;
import com.beanframework.console.csv.ConfigurationCsv;
import com.beanframework.console.csv.CronjobCsv;
import com.beanframework.console.csv.CustomerCsv;
import com.beanframework.console.csv.DynamicFieldCsv;
import com.beanframework.console.csv.DynamicFieldSlotCsv;
import com.beanframework.console.csv.DynamicFieldTemplateCsv;
import com.beanframework.console.csv.EmployeeCsv;
import com.beanframework.console.csv.EnumerationCsv;
import com.beanframework.console.csv.LanguageCsv;
import com.beanframework.console.csv.MediaCsv;
import com.beanframework.console.csv.MenuCsv;
import com.beanframework.console.csv.SiteCsv;
import com.beanframework.console.csv.UserAuthorityCsv;
import com.beanframework.console.csv.UserGroupCsv;
import com.beanframework.console.csv.UserPermissionCsv;
import com.beanframework.console.csv.UserRightCsv;
import com.beanframework.console.csv.VendorCsv;
import com.beanframework.console.listener.AdminImportListener;
import com.beanframework.console.listener.ConfigurationImportListener;
import com.beanframework.console.listener.CronjobImportListener;
import com.beanframework.console.listener.CustomerImportListener;
import com.beanframework.console.listener.DynamicFieldImportListener;
import com.beanframework.console.listener.DynamicFieldSlotImportListener;
import com.beanframework.console.listener.DynamicFieldTemplateImportListener;
import com.beanframework.console.listener.EmployeeImportListener;
import com.beanframework.console.listener.EnumerationImportListener;
import com.beanframework.console.listener.LanguageImportListener;
import com.beanframework.console.listener.MediaImportListener;
import com.beanframework.console.listener.MenuImportListener;
import com.beanframework.console.listener.SiteImportListener;
import com.beanframework.console.listener.UserAuthorityImportListener;
import com.beanframework.console.listener.UserGroupImportListener;
import com.beanframework.console.listener.UserPermissionImportListener;
import com.beanframework.console.listener.UserRightImportListener;
import com.beanframework.console.listener.VendorImportListener;
import com.beanframework.console.registry.ImportListener;
import com.beanframework.console.registry.ImportListenerRegistry;
import com.beanframework.core.data.FileProcessor;

@Component
public class ImportFileProcessor implements FileProcessor {

	private static final String MSG = "%s received. Path: %s";

	@Autowired
	private LanguageImportListener languageImportListener;

	@Autowired
	private ConfigurationImportListener configurationImportListener;

	@Autowired
	private EnumerationImportListener enumerationImportListener;

	@Autowired
	private DynamicFieldImportListener dynamicFieldImportListener;

	@Autowired
	private DynamicFieldSlotImportListener dynamicFieldSlotImportListener;

	@Autowired
	private DynamicFieldTemplateImportListener dynamicFieldTemplateImportListener;

	@Autowired
	private UserRightImportListener userRightImportListener;

	@Autowired
	private UserPermissionImportListener userPermissionImportListener;

	@Autowired
	private UserGroupImportListener userGroupImportListener;

	@Autowired
	private UserAuthorityImportListener userAuthorityImportListener;

	@Autowired
	private MenuImportListener menuImportListener;

	@Autowired
	private CronjobImportListener cronjobImportListener;

	@Autowired
	private AdminImportListener adminImportListener;

	@Autowired
	private EmployeeImportListener employeeImportListener;

	@Autowired
	private CustomerImportListener customerImportListener;

	@Autowired
	private VendorImportListener vendorImportListener;

	@Autowired
	private SiteImportListener siteImportListener;

	@Autowired
	private MediaImportListener mediaImportListener;
	
	@Autowired
	private ImportListenerRegistry importerRegistry;

	@Override
	public void process(Message<String> msg) throws Exception {
		String fileName = (String) msg.getHeaders().get(FileHeaders.FILENAME);
		File fileOriginalFile = (File) msg.getHeaders().get(FileHeaders.ORIGINAL_FILE);

		System.out.println(String.format(MSG, fileName, fileOriginalFile.getAbsolutePath()));

		byte[] buffer = FileUtils.readFileToByteArray(fileOriginalFile);
		Reader targetReader = new CharSequenceReader(new String(buffer));
		
		fileName = fileName.toLowerCase();
		
		// TODO: use generic importlistener
//		Set<Entry<String, ImportListener>> importListeners = importerRegistry.getListeners().entrySet();
//		List<Entry<String, ImportListener>> sortedImportListeners = new LinkedList<Entry<String, ImportListener>>(importListeners);
//		Collections.sort(sortedImportListeners, new Comparator<Entry<String, ImportListener>>() {
//			@Override
//			public int compare(Entry<String, ImportListener> ele1, Entry<String, ImportListener> ele2) {
//				Integer sort1 = ele1.getValue().getSort();
//				Integer sort2 = ele2.getValue().getSort();
//				return sort1.compareTo(sort2);
//			}
//		});
//
//		for (Entry<String, ImportListener> entry : sortedImportListeners) {
//			
//			entry.getValue().save(languageImportListener.readCSVFile(targetReader, LanguageCsv.getUpdateProcessors()));
//
//			
//			if(entry.getKey().startsWith(prefix))
//			
//			if (requestParams.get(entry.getKey()) != null) {
//				String keyValue = requestParams.get(entry.getKey()).toString();
//				if (parseBoolean(keyValue)) {
//					try {
//						entry.getValue().update();
//						entry.getValue().remove();
//						successMessages.append(entry.getValue().getName() + " is updated successfully. <br>");
//					} catch (Exception e) {
//						e.printStackTrace();
//						LOGGER.error(e.getMessage(), e);
//						errorMessages.append(entry.getValue().getName() + " is updated failed. Reason: " + e.getMessage() + " <br>");
//					}
//				}
//			}
//		}

		// Update

		if (fileName.startsWith("language_update"))
			languageImportListener.save(languageImportListener.readCSVFile(targetReader, LanguageCsv.getUpdateProcessors()));

		if (fileName.startsWith("configuration_update"))
			configurationImportListener.save(configurationImportListener.readCSVFile(targetReader, ConfigurationCsv.getUpdateProcessors()));

		if (fileName.startsWith("enumeration_update"))
			enumerationImportListener.save(enumerationImportListener.readCSVFile(targetReader, EnumerationCsv.getUpdateProcessors()));

		if (fileName.startsWith("dynamicfield_update"))
			dynamicFieldImportListener.save(dynamicFieldImportListener.readCSVFile(targetReader, DynamicFieldCsv.getUpdateProcessors()));

		if (fileName.startsWith("dynamicfieldslot_update"))
			dynamicFieldSlotImportListener.save(dynamicFieldSlotImportListener.readCSVFile(targetReader, DynamicFieldSlotCsv.getUpdateProcessors()));

		if (fileName.startsWith("dynamicfieldtemplate_update"))
			dynamicFieldTemplateImportListener.save(dynamicFieldTemplateImportListener.readCSVFile(targetReader, DynamicFieldTemplateCsv.getUpdateProcessors()));

		if (fileName.startsWith("userright_update"))
			userRightImportListener.save(userRightImportListener.readCSVFile(targetReader, UserRightCsv.getUpdateProcessors()));

		if (fileName.startsWith("userpermission_update"))
			userPermissionImportListener.save(userPermissionImportListener.readCSVFile(targetReader, UserPermissionCsv.getUpdateProcessors()));

		if (fileName.startsWith("usergroup_update"))
			userGroupImportListener.save(userGroupImportListener.readCSVFile(targetReader, UserGroupCsv.getUpdateProcessors()));

		if (fileName.startsWith("userauthority_update"))
			userAuthorityImportListener.save(userAuthorityImportListener.readCSVFile(targetReader, UserAuthorityCsv.getUpdateProcessors()));

		if (fileName.startsWith("menu_update"))
			menuImportListener.save(menuImportListener.readCSVFile(targetReader, MenuCsv.getUpdateProcessors()));

		if (fileName.startsWith("cronjob_update"))
			cronjobImportListener.save(cronjobImportListener.readCSVFile(targetReader, CronjobCsv.getUpdateProcessors()));

		if (fileName.startsWith("admin_update"))
			adminImportListener.save(adminImportListener.readCSVFile(targetReader, AdminCsv.getUpdateProcessors()));
		if (fileName.startsWith("employee_update"))
			employeeImportListener.save(employeeImportListener.readCSVFile(targetReader, EmployeeCsv.getUpdateProcessors()));

		if (fileName.startsWith("customer_update"))
			customerImportListener.save(customerImportListener.readCSVFile(targetReader, CustomerCsv.getUpdateProcessors()));

		if (fileName.startsWith("vendor_update"))
			vendorImportListener.save(vendorImportListener.readCSVFile(targetReader, VendorCsv.getUpdateProcessors()));

		if (fileName.startsWith("site_update"))
			siteImportListener.save(siteImportListener.readCSVFile(targetReader, SiteCsv.getUpdateProcessors()));

		if (fileName.startsWith("media_update"))
			mediaImportListener.save(mediaImportListener.readCSVFile(targetReader, MediaCsv.getUpdateProcessors()));

		// Remove

		if (fileName.startsWith("language_remove"))
			languageImportListener.remove(languageImportListener.readCSVFile(targetReader, LanguageCsv.getUpdateProcessors()));

		if (fileName.startsWith("configuration_remove"))
			configurationImportListener.remove(configurationImportListener.readCSVFile(targetReader, ConfigurationCsv.getUpdateProcessors()));

		if (fileName.startsWith("enumeration_remove"))
			enumerationImportListener.remove(enumerationImportListener.readCSVFile(targetReader, EnumerationCsv.getUpdateProcessors()));

		if (fileName.startsWith("dynamicfield_remove"))
			dynamicFieldImportListener.remove(dynamicFieldImportListener.readCSVFile(targetReader, DynamicFieldCsv.getUpdateProcessors()));

		if (fileName.startsWith("dynamicfieldslot_remove"))
			dynamicFieldSlotImportListener.remove(dynamicFieldSlotImportListener.readCSVFile(targetReader, DynamicFieldSlotCsv.getUpdateProcessors()));

		if (fileName.startsWith("dynamicfieldtemplate_remove"))
			dynamicFieldTemplateImportListener.remove(dynamicFieldTemplateImportListener.readCSVFile(targetReader, DynamicFieldTemplateCsv.getUpdateProcessors()));

		if (fileName.startsWith("userright_remove"))
			userRightImportListener.remove(userRightImportListener.readCSVFile(targetReader, UserRightCsv.getUpdateProcessors()));

		if (fileName.startsWith("userpermission_remove"))
			userPermissionImportListener.remove(userPermissionImportListener.readCSVFile(targetReader, UserPermissionCsv.getUpdateProcessors()));

		if (fileName.startsWith("usergroup_remove"))
			userGroupImportListener.remove(userGroupImportListener.readCSVFile(targetReader, UserGroupCsv.getUpdateProcessors()));

		if (fileName.startsWith("userauthority_remove"))
			userAuthorityImportListener.remove(userAuthorityImportListener.readCSVFile(targetReader, UserAuthorityCsv.getUpdateProcessors()));

		if (fileName.startsWith("menu_remove"))
			menuImportListener.remove(menuImportListener.readCSVFile(targetReader, MenuCsv.getUpdateProcessors()));

		if (fileName.startsWith("cronjob_remove"))
			cronjobImportListener.remove(cronjobImportListener.readCSVFile(targetReader, CronjobCsv.getUpdateProcessors()));

		if (fileName.startsWith("admin_remove"))
			adminImportListener.remove(adminImportListener.readCSVFile(targetReader, AdminCsv.getUpdateProcessors()));
		if (fileName.startsWith("employee_remove"))
			employeeImportListener.remove(employeeImportListener.readCSVFile(targetReader, EmployeeCsv.getUpdateProcessors()));

		if (fileName.startsWith("customer_remove"))
			customerImportListener.remove(customerImportListener.readCSVFile(targetReader, CustomerCsv.getUpdateProcessors()));

		if (fileName.startsWith("vendor_remove"))
			vendorImportListener.remove(vendorImportListener.readCSVFile(targetReader, VendorCsv.getUpdateProcessors()));

		if (fileName.startsWith("site_remove"))
			siteImportListener.remove(siteImportListener.readCSVFile(targetReader, SiteCsv.getUpdateProcessors()));

		if (fileName.startsWith("media_remove"))
			mediaImportListener.remove(mediaImportListener.readCSVFile(targetReader, MediaCsv.getUpdateProcessors()));
	}
}
