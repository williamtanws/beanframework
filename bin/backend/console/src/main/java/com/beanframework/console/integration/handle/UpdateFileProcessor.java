package com.beanframework.console.integration.handle;

import java.io.File;
import java.io.Reader;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.CharSequenceReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.beanframework.console.update.CronjobUpdate;
import com.beanframework.console.update.CustomerUpdate;
import com.beanframework.console.update.EmployeeUpdate;
import com.beanframework.console.update.LanguageUpdate;
import com.beanframework.console.update.MenuUpdate;
import com.beanframework.console.update.UserAuthorityUpdate;
import com.beanframework.console.update.UserGroupUpdate;
import com.beanframework.console.update.UserPermissionUpdate;
import com.beanframework.console.update.UserRightUpdate;

@Component
public class UpdateFileProcessor {

    private static final String MSG = "%s received. Path: %s";
    
    @Autowired
	private CronjobUpdate cronjobUpdate;

	@Autowired
	private CustomerUpdate customerUpdate;

	@Autowired
	private EmployeeUpdate employeeUpdate;

	@Autowired
	private LanguageUpdate languageUpdate;

	@Autowired
	private MenuUpdate menuUpdate;

	@Autowired
	private UserAuthorityUpdate userAuthorityUpdate;

	@Autowired
	private UserGroupUpdate userGroupUpdate;

	@Autowired
	private UserPermissionUpdate userPermissionUpdate;

	@Autowired
	private UserRightUpdate userRightUpdate;
    
    public void process(Message<String> msg) throws Exception {
        String fileName = (String) msg.getHeaders().get(FileHeaders.FILENAME);
        File fileOriginalFile = (File) msg.getHeaders().get(FileHeaders.ORIGINAL_FILE);

        System.out.println(String.format(MSG, fileName, fileOriginalFile.getAbsolutePath()));
        
		byte[] buffer = FileUtils.readFileToByteArray(fileOriginalFile);
		Reader targetReader = new CharSequenceReader(new String(buffer));
        
        if (fileName.startsWith("cronjob_update")) {
			cronjobUpdate.save(cronjobUpdate.readCSVFile(targetReader));

		} else if (fileName.startsWith("customer_update")) {
			customerUpdate.save(customerUpdate.readCSVFile(targetReader));

		} else if (fileName.startsWith("employee_update")) {
			employeeUpdate.save(employeeUpdate.readCSVFile(targetReader));

		} else if (fileName.startsWith("language_update")) {
			languageUpdate.save(languageUpdate.readCSVFile(targetReader));

		} else if (fileName.startsWith("menu_update")) {
			menuUpdate.save(menuUpdate.readCSVFile(targetReader));

		} else if (fileName.startsWith("userauthority_update")) {
			userAuthorityUpdate.save(userAuthorityUpdate.readCSVFile(targetReader));

		} else if (fileName.startsWith("usergroup_update")) {
			userGroupUpdate.save(userGroupUpdate.readCSVFile(targetReader));

		} else if (fileName.startsWith("userpermission_update")) {
			userPermissionUpdate.save(userPermissionUpdate.readCSVFile(targetReader));

		} else if (fileName.startsWith("userright_update")) {
			userRightUpdate.save(userRightUpdate.readCSVFile(targetReader));

		}
    }
}
