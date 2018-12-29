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
import com.beanframework.console.data.CustomerCsv;
import com.beanframework.customer.domain.Customer;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.utils.PasswordUtils;

public class CustomerUpdate extends Updater {
	private static Logger LOGGER = LoggerFactory.getLogger(CustomerUpdate.class);

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
	public void update() throws Exception {
		PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
		Resource[] resources = loader.getResources(IMPORT_UPDATE_CUSTOMER_PATH);
		for (Resource resource : resources) {
			InputStream in = resource.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtils.copy(in, baos);
			BufferedReader reader = new BufferedReader(new StringReader(new String(baos.toByteArray())));

			List<CustomerCsv> customerCsvList = readCSVFile(reader);
			save(customerCsvList);
		}
	}

	public void save(List<CustomerCsv> customerCsvList) throws Exception {

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

			if (StringUtils.isNotBlank(csv.getPassword())) {
				customer.setPassword(PasswordUtils.encode(csv.getPassword()));
			}
			customer.setAccountNonExpired(csv.isAccountNonExpired());
			customer.setAccountNonLocked(csv.isAccountNonLocked());
			customer.setCredentialsNonExpired(csv.isCredentialsNonExpired());
			customer.setEnabled(csv.isEnabled());

			modelService.saveEntity(customer, Customer.class);
			
			// Employee Field

			if (csv.getDynamicField() != null) {
				String dynamicFieldId = csv.getDynamicField().split(";")[0];
				String value = csv.getDynamicField().split(";")[1];
				for (int i = 0; i < customer.getUserFields().size(); i++) {
					if (customer.getUserFields().get(i).getId().equals(dynamicFieldId)) {
						customer.getUserFields().get(i).setValue(value);
					}
				}
			}

			modelService.saveEntity(customer, Customer.class);
			
			// User Field

			if (csv.getDynamicField() != null) {
				String[] dynamicFields = csv.getDynamicField().split(";");
				for (String dynamicField : dynamicFields) {
					String dynamicFieldId = dynamicField.split("=")[0];
					String value = dynamicField.split("=")[1];
					for (int i = 0; i < customer.getUserFields().size(); i++) {
						if (customer.getUserFields().get(i).getId().equals(customer.getId()+"_"+dynamicFieldId)) {
							customer.getUserFields().get(i).setValue(value);
						}
					}
				}
			}
			
			modelService.saveEntity(customer, Customer.class);

			// User Group

			String[] userGroupIds = csv.getUserGroupIds().split(SPLITTER);
			for (int i = 0; i < userGroupIds.length; i++) {
				Map<String, Object> userGroupProperties = new HashMap<String, Object>();
				userGroupProperties.put(UserGroup.ID, userGroupIds[i]);
				UserGroup userGroup = modelService.findOneEntityByProperties(userGroupProperties, UserGroup.class);

				if (userGroup == null) {
					LOGGER.error("UserGroup not exists: " + userGroupIds[i]);
				} else {
					customer.getUserGroups().add(userGroup);

					modelService.saveEntity(userGroup, UserGroup.class);
				}
			}
		}
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
			LOGGER.info("Start import Customer Csv.");
			while ((customerCsv = beanReader.read(CustomerCsv.class, header, processors)) != null) {
				LOGGER.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(), customerCsv);
				customerCsvList.add(customerCsv);
			}
			LOGGER.info("Finished import Customer Csv.");
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
		return customerCsvList;
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
