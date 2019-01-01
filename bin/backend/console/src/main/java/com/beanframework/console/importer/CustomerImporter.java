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
import com.beanframework.console.csv.CustomerCsv;
import com.beanframework.console.registry.Importer;
import com.beanframework.customer.domain.Customer;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.utils.PasswordUtils;

public class CustomerImporter extends Importer {
	private static Logger LOGGER = LoggerFactory.getLogger(CustomerImporter.class);

	@Autowired
	private ModelService modelService;

	@Value("${module.console.import.update.customer}")
	private String IMPORT_UPDATE;
	
	@Value("${module.console.import.remove.customer}")
	private String IMPORT_REMOVE;

	@PostConstruct
	public void importer() {
		setKey(WebPlatformUpdateConstants.Importer.Customer.KEY);
		setName(WebPlatformUpdateConstants.Importer.Customer.NAME);
		setSort(WebPlatformUpdateConstants.Importer.Customer.SORT);
		setDescription(WebPlatformUpdateConstants.Importer.Customer.DESCRIPTION);
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

			List<CustomerCsv> customerCsvList = readCSVFile(reader, CustomerCsv.getUpdateProcessors());
			save(customerCsvList);
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

			List<CustomerCsv> customerCsvList = readCSVFile(reader, CustomerCsv.getRemoveProcessors());
			remove(customerCsvList);
		}
	}

	public List<CustomerCsv> readCSVFile(Reader reader, CellProcessor[] processors) {
		ICsvBeanReader beanReader = null;

		List<CustomerCsv> csvList = new ArrayList<CustomerCsv>();

		try {
			beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);

			// the header elements are used to map the values to the bean (names
			// must match)
			final String[] header = beanReader.getHeader(true);

			CustomerCsv csv;
			LOGGER.info("Start import "+WebPlatformUpdateConstants.Importer.Customer.NAME);
			while ((csv = beanReader.read(CustomerCsv.class, header, processors)) != null) {
				LOGGER.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(), csv);
				csvList.add(csv);
			}
			LOGGER.info("Finished import "+WebPlatformUpdateConstants.Importer.Customer.NAME);
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
	
	public void save(List<CustomerCsv> customerCsvList) throws Exception {

		for (CustomerCsv csv : customerCsvList) {

			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Customer.ID, csv.getId());

			Customer customer = modelService.findOneEntityByProperties(properties, Customer.class);

			if (customer == null) {
				customer = modelService.create(Customer.class);
				customer.setId(csv.getId());
			} else {
				Hibernate.initialize(customer.getFields());
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
				for (int i = 0; i < customer.getFields().size(); i++) {
					if (customer.getFields().get(i).getId().equals(dynamicFieldId)) {
						customer.getFields().get(i).setValue(value);
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
					for (int i = 0; i < customer.getFields().size(); i++) {
						if (customer.getFields().get(i).getId().equals(customer.getId()+"_"+dynamicFieldId)) {
							customer.getFields().get(i).setValue(value);
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
	
	public void remove(List<CustomerCsv> csvList) throws Exception {
		for (CustomerCsv csv : csvList) {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Customer.ID, csv.getId());
			Customer model = modelService.findOneEntityByProperties(properties, Customer.class);
			modelService.deleteByEntity(model, Customer.class);
		}
	}

}
