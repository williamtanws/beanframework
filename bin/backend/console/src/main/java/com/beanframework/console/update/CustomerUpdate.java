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
import com.beanframework.console.domain.CustomerCsv;
import com.beanframework.customer.domain.Customer;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.domain.DynamicFieldTypeEnum;
import com.beanframework.user.domain.UserField;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.utils.PasswordUtils;

public class CustomerUpdate extends Updater {
	protected final Logger logger = LoggerFactory.getLogger(CustomerUpdate.class);

	@Autowired
	private ModelService modelService;

	@Value("${module.console.import.update.customer}")
	private String IMPORT_UPDATE_CUSTOMER_PATH;

	@PostConstruct
	public void updater() {
		setKey(WebPlatformConstants.Update.Customer.KEY);
		setName(WebPlatformConstants.Update.Customer.NAME);
		setSort(WebPlatformConstants.Update.Customer.SORT);
		setDescription(WebPlatformConstants.Update.Customer.DESCRIPTION);
	}

	@Override
	public void update() {
		PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
		Resource[] resources = null;
		try {
			resources = loader.getResources(IMPORT_UPDATE_CUSTOMER_PATH);
			for (Resource resource : resources) {
				try {
					InputStream in = resource.getInputStream();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					IOUtils.copy(in, baos);
					BufferedReader reader = new BufferedReader(new StringReader(new String(baos.toByteArray())));

					List<CustomerCsv> customerCsvList = readCSVFile(reader);
					save(customerCsvList);

				} catch (Exception ex) {
					logger.error("Error reading the resource file: " + ex);
				}
			}
		} catch (IOException ex) {
			logger.error("Error reading the resource file: " + ex);
		}
	}

	public void save(List<CustomerCsv> customerCsvList) throws Exception {

		// Dynamic Field

		Map<String, Object> nameDynamicFieldProperties = new HashMap<String, Object>();
		nameDynamicFieldProperties.put(DynamicField.ID, "customer_name");
		DynamicField nameDynamicField = modelService.findOneEntityByProperties(nameDynamicFieldProperties,
				DynamicField.class);

		if (nameDynamicField == null) {
			nameDynamicField = modelService.create(DynamicField.class);
			nameDynamicField.setId("customer_name");
		}
		nameDynamicField.setRequired(true);
		nameDynamicField.setRule(null);
		nameDynamicField.setSort(0);
		nameDynamicField.setType(DynamicFieldTypeEnum.TEXT);
		modelService.saveEntity(nameDynamicField);

		for (CustomerCsv csv : customerCsvList) {

			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Customer.ID, csv.getId());

			Customer customer = modelService.findOneEntityByProperties(properties, Customer.class);

			if (customer == null) {
				customer = modelService.create(Customer.class);
				customer.setId(csv.getId());
			} else {
				Hibernate.initialize(customer.getUserFields());
			}
			
			if (StringUtils.isNotEmpty(csv.getPassword())) {
				customer.setPassword(PasswordUtils.encode(csv.getPassword()));
			}
			customer.setAccountNonExpired(csv.isAccountNonExpired());
			customer.setAccountNonLocked(csv.isAccountNonLocked());
			customer.setCredentialsNonExpired(csv.isCredentialsNonExpired());
			customer.setEnabled(csv.isEnabled());
			
			boolean createName = true;

			for (int i = 0; i < customer.getUserFields().size(); i++) {
				if (customer.getUserFields().get(i).getId().equals(csv.getId() + "_name")) {
					customer.getUserFields().get(i).setLabel("Name");
					customer.getUserFields().get(i).setValue(csv.getName());
					createName = false;
				}
			}

			modelService.saveEntity(customer);

			// User Group

			String[] userGroupIds = csv.getUserGroupIds().split(SPLITTER);
			for (int i = 0; i < userGroupIds.length; i++) {
				Map<String, Object> userGroupProperties = new HashMap<String, Object>();
				userGroupProperties.put(UserGroup.ID, userGroupIds[i]);
				UserGroup userGroup = modelService.findOneEntityByProperties(userGroupProperties, UserGroup.class);

				if (userGroup == null) {
					logger.error("UserGroup not exists: " + userGroupIds[i]);
				} else {
					customer.getUserGroups().add(userGroup);

					modelService.saveEntity(userGroup);
				}
			}

			// Customer Field

			if (createName) {
				UserField customerField = modelService.create(UserField.class);
				customerField.setId(csv.getId() + "_name");
				customerField.setDynamicField(nameDynamicField);
				customerField.setLabel("Name");
				customerField.setValue(csv.getName());
				customerField.setUser(customer);
				customer.getUserFields().add(customerField);

				modelService.saveEntity(customerField);
			}
		}
		modelService.saveAll();
	}

	public List<CustomerCsv> readCSVFile(Reader reader) {
		ICsvBeanReader beanReader = null;

		List<CustomerCsv> customerCsvList = new ArrayList<CustomerCsv>();

		try {
			beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);

			// the header elements are used to map the values to the bean (names
			// must match)
			final String[] header = beanReader.getHeader(true);
			final CellProcessor[] processors = getProcessors();

			CustomerCsv customerCsv;
			logger.info("Start import Customer Csv.");
			while ((customerCsv = beanReader.read(CustomerCsv.class, header, processors)) != null) {
				logger.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(),
						customerCsv);
				customerCsvList.add(customerCsv);
			}
			logger.info("Finished import Customer Csv.");
		} catch (FileNotFoundException ex) {
			logger.error("Could not find the CSV file: " + ex);
		} catch (IOException ex) {
			logger.error("Error reading the CSV file: " + ex);
		} finally {
			if (beanReader != null) {
				try {
					beanReader.close();
				} catch (IOException ex) {
					logger.error("Error closing the reader: " + ex);
				}
			}
		}
		return customerCsvList;
	}

	public CellProcessor[] getProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { new UniqueHashCode(), // id
				new NotNull(), // name
				new NotNull(), // password
				new ParseBool(), // accountNonExpired
				new ParseBool(), // accountNonLocked
				new ParseBool(), // credentialsNonExpired
				new ParseBool(), // enabled
				new org.supercsv.cellprocessor.Optional() // userGroupId
		};

		return processors;
	}

}
