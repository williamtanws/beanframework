package com.beanframework.console.integration.handle;

import java.io.File;
import java.io.Reader;

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
import com.beanframework.console.csv.EmployeeCsv;
import com.beanframework.console.csv.LanguageCsv;
import com.beanframework.console.csv.MenuCsv;
import com.beanframework.console.csv.UserAuthorityCsv;
import com.beanframework.console.csv.UserGroupCsv;
import com.beanframework.console.csv.UserPermissionCsv;
import com.beanframework.console.csv.UserRightCsv;
import com.beanframework.console.listener.AdminImportListener;
import com.beanframework.console.listener.ConfigurationImportListener;
import com.beanframework.console.listener.CronjobImportListener;
import com.beanframework.console.listener.CustomerImportListener;
import com.beanframework.console.listener.EmployeeImportListener;
import com.beanframework.console.listener.LanguageImportListener;
import com.beanframework.console.listener.MenuImportListener;
import com.beanframework.console.listener.UserAuthorityImportListener;
import com.beanframework.console.listener.UserGroupImportListener;
import com.beanframework.console.listener.UserPermissionImportListener;
import com.beanframework.console.listener.UserRightImportListener;
import com.beanframework.core.data.FileProcessor;

@Component
public class ImportFileProcessor implements FileProcessor {

	private static final String MSG = "%s received. Path: %s";

	@Autowired
	private AdminImportListener adminImportListener;

	@Autowired
	private ConfigurationImportListener configurationImportListener;

	@Autowired
	private CronjobImportListener cronjobImportListener;

	@Autowired
	private CustomerImportListener customerImportListener;

	@Autowired
	private EmployeeImportListener employeeImportListener;

	@Autowired
	private LanguageImportListener languageImportListener;

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

	@Override
	public void process(Message<String> msg) throws Exception {
		String fileName = (String) msg.getHeaders().get(FileHeaders.FILENAME);
		File fileOriginalFile = (File) msg.getHeaders().get(FileHeaders.ORIGINAL_FILE);

		System.out.println(String.format(MSG, fileName, fileOriginalFile.getAbsolutePath()));

		byte[] buffer = FileUtils.readFileToByteArray(fileOriginalFile);
		Reader targetReader = new CharSequenceReader(new String(buffer));

		// Update

		if (fileName.startsWith("configuration_update"))
			configurationImportListener.save(configurationImportListener.readCSVFile(targetReader, ConfigurationCsv.getUpdateProcessors()));

		if (fileName.startsWith("admin_update"))
			adminImportListener.save(adminImportListener.readCSVFile(targetReader, AdminCsv.getUpdateProcessors()));

		if (fileName.startsWith("cronjob_update"))
			cronjobImportListener.save(cronjobImportListener.readCSVFile(targetReader, CronjobCsv.getUpdateProcessors()));

		if (fileName.startsWith("customer_update"))
			customerImportListener.save(customerImportListener.readCSVFile(targetReader, CustomerCsv.getUpdateProcessors()));

		if (fileName.startsWith("employee_update"))
			employeeImportListener.save(employeeImportListener.readCSVFile(targetReader, EmployeeCsv.getUpdateProcessors()));

		if (fileName.startsWith("language_update"))
			languageImportListener.save(languageImportListener.readCSVFile(targetReader, LanguageCsv.getUpdateProcessors()));

		if (fileName.startsWith("menu_update"))
			menuImportListener.save(menuImportListener.readCSVFile(targetReader, MenuCsv.getUpdateProcessors()));

		if (fileName.startsWith("userauthority_update"))
			userAuthorityImportListener.save(userAuthorityImportListener.readCSVFile(targetReader, UserAuthorityCsv.getUpdateProcessors()));

		if (fileName.startsWith("usergroup_update"))
			userGroupImportListener.save(userGroupImportListener.readCSVFile(targetReader, UserGroupCsv.getUpdateProcessors()));

		if (fileName.startsWith("userpermission_update"))
			userPermissionImportListener.save(userPermissionImportListener.readCSVFile(targetReader, UserPermissionCsv.getUpdateProcessors()));

		if (fileName.startsWith("userright_update"))
			userRightImportListener.save(userRightImportListener.readCSVFile(targetReader, UserRightCsv.getUpdateProcessors()));

		// Remove

		if (fileName.startsWith("configuration_remove"))
			configurationImportListener.remove(configurationImportListener.readCSVFile(targetReader, ConfigurationCsv.getRemoveProcessors()));

		if (fileName.startsWith("admin_remove"))
			adminImportListener.remove(adminImportListener.readCSVFile(targetReader, AdminCsv.getRemoveProcessors()));

		if (fileName.startsWith("cronjob_remove"))
			cronjobImportListener.remove(cronjobImportListener.readCSVFile(targetReader, CronjobCsv.getRemoveProcessors()));

		if (fileName.startsWith("customer_remove"))
			customerImportListener.remove(customerImportListener.readCSVFile(targetReader, CustomerCsv.getRemoveProcessors()));

		if (fileName.startsWith("employee_remove"))
			employeeImportListener.remove(employeeImportListener.readCSVFile(targetReader, EmployeeCsv.getRemoveProcessors()));

		if (fileName.startsWith("language_remove"))
			languageImportListener.remove(languageImportListener.readCSVFile(targetReader, LanguageCsv.getRemoveProcessors()));

		if (fileName.startsWith("menu_remove"))
			menuImportListener.remove(menuImportListener.readCSVFile(targetReader, MenuCsv.getRemoveProcessors()));

		if (fileName.startsWith("userauthority_remove"))
			userAuthorityImportListener.remove(userAuthorityImportListener.readCSVFile(targetReader, UserAuthorityCsv.getRemoveProcessors()));

		if (fileName.startsWith("usergroup_remove"))
			userGroupImportListener.remove(userGroupImportListener.readCSVFile(targetReader, UserGroupCsv.getRemoveProcessors()));

		if (fileName.startsWith("userpermission_remove"))
			userPermissionImportListener.remove(userPermissionImportListener.readCSVFile(targetReader, UserPermissionCsv.getRemoveProcessors()));

		if (fileName.startsWith("userright_remove"))
			userRightImportListener.remove(userRightImportListener.readCSVFile(targetReader, UserRightCsv.getRemoveProcessors()));
	}
}
