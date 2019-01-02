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
import com.beanframework.console.data.FileProcessor;
import com.beanframework.console.importer.AdminImporter;
import com.beanframework.console.importer.ConfigurationImporter;
import com.beanframework.console.importer.CronjobImporter;
import com.beanframework.console.importer.CustomerImporter;
import com.beanframework.console.importer.EmployeeImporter;
import com.beanframework.console.importer.LanguageImporter;
import com.beanframework.console.importer.MenuImporter;
import com.beanframework.console.importer.UserAuthorityImporter;
import com.beanframework.console.importer.UserGroupImporter;
import com.beanframework.console.importer.UserPermissionImporter;
import com.beanframework.console.importer.UserRightImporter;

@Component
public class ImporterFileProcessor implements FileProcessor{

	private static final String MSG = "%s received. Path: %s";

	@Autowired
	private AdminImporter adminImporter;

	@Autowired
	private ConfigurationImporter configurationImporter;

	@Autowired
	private CronjobImporter cronjobImporter;

	@Autowired
	private CustomerImporter customerImporter;

	@Autowired
	private EmployeeImporter employeeImporter;

	@Autowired
	private LanguageImporter languageImporter;

	@Autowired
	private MenuImporter menuImporter;

	@Autowired
	private UserAuthorityImporter userAuthorityImporter;

	@Autowired
	private UserGroupImporter userGroupImporter;

	@Autowired
	private UserPermissionImporter userPermissionImporter;

	@Autowired
	private UserRightImporter userRightImporter;

	@Override
	public void process(Message<String> msg) throws Exception {
		String fileName = (String) msg.getHeaders().get(FileHeaders.FILENAME);
		File fileOriginalFile = (File) msg.getHeaders().get(FileHeaders.ORIGINAL_FILE);

		System.out.println(String.format(MSG, fileName, fileOriginalFile.getAbsolutePath()));

		byte[] buffer = FileUtils.readFileToByteArray(fileOriginalFile);
		Reader targetReader = new CharSequenceReader(new String(buffer));

		// Update

		if (fileName.startsWith("configuration_update"))
			configurationImporter.save(configurationImporter.readCSVFile(targetReader, ConfigurationCsv.getUpdateProcessors()));

		if (fileName.startsWith("admin_update"))
			adminImporter.save(adminImporter.readCSVFile(targetReader, AdminCsv.getUpdateProcessors()));

		if (fileName.startsWith("cronjob_update"))
			cronjobImporter.save(cronjobImporter.readCSVFile(targetReader, CronjobCsv.getUpdateProcessors()));

		if (fileName.startsWith("customer_update"))
			customerImporter.save(customerImporter.readCSVFile(targetReader, CustomerCsv.getUpdateProcessors()));

		if (fileName.startsWith("employee_update"))
			employeeImporter.save(employeeImporter.readCSVFile(targetReader, EmployeeCsv.getUpdateProcessors()));

		if (fileName.startsWith("language_update"))
			languageImporter.save(languageImporter.readCSVFile(targetReader, LanguageCsv.getUpdateProcessors()));

		if (fileName.startsWith("menu_update"))
			menuImporter.save(menuImporter.readCSVFile(targetReader, MenuCsv.getUpdateProcessors()));

		if (fileName.startsWith("userauthority_update"))
			userAuthorityImporter.save(userAuthorityImporter.readCSVFile(targetReader, UserAuthorityCsv.getUpdateProcessors()));

		if (fileName.startsWith("usergroup_update"))
			userGroupImporter.save(userGroupImporter.readCSVFile(targetReader, UserGroupCsv.getUpdateProcessors()));

		if (fileName.startsWith("userpermission_update"))
			userPermissionImporter.save(userPermissionImporter.readCSVFile(targetReader, UserPermissionCsv.getUpdateProcessors()));

		if (fileName.startsWith("userright_update"))
			userRightImporter.save(userRightImporter.readCSVFile(targetReader, UserRightCsv.getUpdateProcessors()));

		// Remove

		if (fileName.startsWith("configuration_remove"))
			configurationImporter.remove(configurationImporter.readCSVFile(targetReader, ConfigurationCsv.getRemoveProcessors()));

		if (fileName.startsWith("admin_remove"))
			adminImporter.remove(adminImporter.readCSVFile(targetReader, AdminCsv.getRemoveProcessors()));

		if (fileName.startsWith("cronjob_remove"))
			cronjobImporter.remove(cronjobImporter.readCSVFile(targetReader, CronjobCsv.getRemoveProcessors()));

		if (fileName.startsWith("customer_remove"))
			customerImporter.remove(customerImporter.readCSVFile(targetReader, CustomerCsv.getRemoveProcessors()));

		if (fileName.startsWith("employee_remove"))
			employeeImporter.remove(employeeImporter.readCSVFile(targetReader, EmployeeCsv.getRemoveProcessors()));

		if (fileName.startsWith("language_remove"))
			languageImporter.remove(languageImporter.readCSVFile(targetReader, LanguageCsv.getRemoveProcessors()));

		if (fileName.startsWith("menu_remove"))
			menuImporter.remove(menuImporter.readCSVFile(targetReader, MenuCsv.getRemoveProcessors()));

		if (fileName.startsWith("userauthority_remove"))
			userAuthorityImporter.remove(userAuthorityImporter.readCSVFile(targetReader, UserAuthorityCsv.getRemoveProcessors()));

		if (fileName.startsWith("usergroup_remove"))
			userGroupImporter.remove(userGroupImporter.readCSVFile(targetReader, UserGroupCsv.getRemoveProcessors()));

		if (fileName.startsWith("userpermission_remove"))
			userPermissionImporter.remove(userPermissionImporter.readCSVFile(targetReader, UserPermissionCsv.getRemoveProcessors()));

		if (fileName.startsWith("userright_remove"))
			userRightImporter.remove(userRightImporter.readCSVFile(targetReader, UserRightCsv.getRemoveProcessors()));
	}
}
