package com.beanframework.console.update;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.beanframework.common.Updater;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.WebPlatformConstants;
import com.beanframework.console.domain.EmployeeCsv;
import com.beanframework.employee.domain.Employee;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.utils.PasswordUtils;

public class EmployeeUpdate extends Updater {
	private static Logger LOGGER = LoggerFactory.getLogger(EmployeeUpdate.class);

	@Autowired
	private ModelService modelService;

	@Value("${module.console.import.update.employee}")
	private String IMPORT_UPDATE_EMPLOYEE_PATH;

	@PostConstruct
	public void updater() {
		setKey(WebPlatformConstants.Update.Employee.KEY);
		setName(WebPlatformConstants.Update.Employee.NAME);
		setSort(WebPlatformConstants.Update.Employee.SORT);
		setDescription(WebPlatformConstants.Update.Employee.DESCRIPTION);
	}

	@Override
	public void update() throws Exception {
		PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
		Resource[] resources = loader.getResources(IMPORT_UPDATE_EMPLOYEE_PATH);
		for (Resource resource : resources) {
			InputStream in = resource.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtils.copy(in, baos);
			BufferedReader reader = new BufferedReader(new StringReader(new String(baos.toByteArray())));

			List<EmployeeCsv> employeeCsvList = readCSVFile(reader);
			save(employeeCsvList);
		}
	}

	public void save(List<EmployeeCsv> employeeCsvList) throws Exception {

		for (EmployeeCsv csv : employeeCsvList) {

			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Employee.ID, csv.getId());

			Employee employee = modelService.findOneEntityByProperties(properties, Employee.class);

			if (employee == null) {
				employee = modelService.create(Employee.class);
				employee.setId(csv.getId());
			} else {
				Hibernate.initialize(employee.getUserFields());
			}

			if (StringUtils.isNotEmpty(csv.getPassword())) {
				employee.setPassword(PasswordUtils.encode(csv.getPassword()));
			}
			employee.setAccountNonExpired(csv.isAccountNonExpired());
			employee.setAccountNonLocked(csv.isAccountNonLocked());
			employee.setCredentialsNonExpired(csv.isCredentialsNonExpired());
			employee.setEnabled(csv.isEnabled());

			modelService.saveEntity(employee, Employee.class);

			// User Field

			if (csv.getDynamicField() != null) {
				String[] dynamicFields = csv.getDynamicField().split(";");
				for (String dynamicField : dynamicFields) {
					String dynamicFieldId = dynamicField.split("=")[0];
					String value = dynamicField.split("=")[1];
					for (int i = 0; i < employee.getUserFields().size(); i++) {
						if (employee.getUserFields().get(i).getId().equals(employee.getId()+"_"+dynamicFieldId)) {
							employee.getUserFields().get(i).setValue(value);
						}
					}
				}
			}

			modelService.saveEntity(employee, Employee.class);

			// User Group

			String[] userGroupIds = csv.getUserGroupIds().split(SPLITTER);
			for (int i = 0; i < userGroupIds.length; i++) {
				Map<String, Object> userGroupProperties = new HashMap<String, Object>();
				userGroupProperties.put(UserGroup.ID, userGroupIds[i]);
				UserGroup userGroup = modelService.findOneEntityByProperties(userGroupProperties, UserGroup.class);

				if (userGroup == null) {
					LOGGER.error("UserGroup not exists: " + userGroupIds[i]);
				} else {
					employee.getUserGroups().add(userGroup);

					modelService.saveEntity(userGroup, UserGroup.class);
				}
			}
		}
	}

	public List<EmployeeCsv> readCSVFile(Reader reader) {
		ICsvBeanReader beanReader = null;

		List<EmployeeCsv> employeeCsvList = new ArrayList<EmployeeCsv>();

		try {
			beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);

			// the header elements are used to map the values to the bean (names
			// must match)
			final String[] header = beanReader.getHeader(true);
			final CellProcessor[] processors = getProcessors();

			EmployeeCsv employeeCsv;
			LOGGER.info("Start import Employee Csv.");
			while ((employeeCsv = beanReader.read(EmployeeCsv.class, header, processors)) != null) {
				LOGGER.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(), employeeCsv);
				employeeCsvList.add(employeeCsv);
			}
			LOGGER.info("Finished import Employee Csv.");
		} catch (FileNotFoundException ex) {
			LOGGER.error("Could not find the CSV file: " + ex);
		} catch (IOException ex) {
			LOGGER.error("Error reading the CSV file: " + ex);
		} finally {
			if (beanReader != null) {
				try {
					beanReader.close();
				} catch (IOException ex) {
					LOGGER.error("Error closing the reader: " + ex);
				}
			}
		}
		return employeeCsvList;
	}

	public CellProcessor[] getProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { new UniqueHashCode(), // id
				new NotNull(), // password
				new ParseBool(), // accountNonExpired
				new ParseBool(), // accountNonLocked
				new ParseBool(), // credentialsNonExpired
				new ParseBool(), // enabled
				new org.supercsv.cellprocessor.Optional(), // userGroupId
				new Optional() // dynamicField
		};

		return processors;
	}

}
