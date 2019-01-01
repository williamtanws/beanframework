package com.beanframework.console.importer;

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
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.beanframework.common.service.ModelService;
import com.beanframework.console.WebPlatformUpdateConstants;
import com.beanframework.console.csv.EmployeeCsv;
import com.beanframework.console.registry.Importer;
import com.beanframework.employee.domain.Employee;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.utils.PasswordUtils;

public class EmployeeImporter extends Importer {
	private static Logger LOGGER = LoggerFactory.getLogger(EmployeeImporter.class);

	@Autowired
	private ModelService modelService;

	@Value("${module.console.import.update.employee}")
	private String IMPORT_UPDATE;
	
	@Value("${module.console.import.remove.employee}")
	private String IMPORT_REMOVE;

	@PostConstruct
	public void importer() {
		setKey(WebPlatformUpdateConstants.Importer.Employee.KEY);
		setName(WebPlatformUpdateConstants.Importer.Employee.NAME);
		setSort(WebPlatformUpdateConstants.Importer.Employee.SORT);
		setDescription(WebPlatformUpdateConstants.Importer.Employee.DESCRIPTION);
	}

	@Override
	public void update() throws Exception {
		PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
		Resource[] resources = loader.getResources(IMPORT_UPDATE);
		for (Resource resource : resources) {
			InputStream in = resource.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtils.copy(in, baos);
			BufferedReader reader = new BufferedReader(new StringReader(new String(baos.toByteArray())));

			List<EmployeeCsv> employeeCsvList = readCSVFile(reader, EmployeeCsv.getUpdateProcessors());
			save(employeeCsvList);
		}
	}
	
	@Override
	public void remove() throws Exception {
		PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
		Resource[] resources = loader.getResources(IMPORT_REMOVE);
		for (Resource resource : resources) {
			InputStream in = resource.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtils.copy(in, baos);
			BufferedReader reader = new BufferedReader(new StringReader(new String(baos.toByteArray())));

			List<EmployeeCsv> employeeCsvList = readCSVFile(reader, EmployeeCsv.getRemoveProcessors());
			remove(employeeCsvList);
		}
	}
	
	public List<EmployeeCsv> readCSVFile(Reader reader, CellProcessor[] processors) {
		ICsvBeanReader beanReader = null;

		List<EmployeeCsv> csvList = new ArrayList<EmployeeCsv>();

		try {
			beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);

			// the header elements are used to map the values to the bean (names
			// must match)
			final String[] header = beanReader.getHeader(true);

			EmployeeCsv csv;
			LOGGER.info("Start import "+WebPlatformUpdateConstants.Importer.Employee.NAME);
			while ((csv = beanReader.read(EmployeeCsv.class, header, processors)) != null) {
				LOGGER.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(), csv);
				csvList.add(csv);
			}
			LOGGER.info("Finished import "+WebPlatformUpdateConstants.Importer.Employee.NAME);
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
		return csvList;
	}

	public void save(List<EmployeeCsv> csvList) throws Exception {

		for (EmployeeCsv csv : csvList) {

			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Employee.ID, csv.getId());

			Employee employee = modelService.findOneEntityByProperties(properties, Employee.class);

			if (employee == null) {
				employee = modelService.create(Employee.class);
				employee.setId(csv.getId());
			} else {
				Hibernate.initialize(employee.getFields());
			}

			if (StringUtils.isNotBlank(csv.getPassword())) {
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
					for (int i = 0; i < employee.getFields().size(); i++) {
						if (employee.getFields().get(i).getId().equals(employee.getId()+"_"+dynamicFieldId)) {
							employee.getFields().get(i).setValue(value);
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
	
	public void remove(List<EmployeeCsv> csvList) throws Exception {
		for (EmployeeCsv csv : csvList) {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Employee.ID, csv.getId());
			Employee model = modelService.findOneEntityByProperties(properties, Employee.class);
			modelService.deleteByEntity(model, Employee.class);
		}
	}

}
