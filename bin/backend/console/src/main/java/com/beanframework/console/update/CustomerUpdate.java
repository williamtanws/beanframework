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

import javax.annotation.PostConstruct;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.beanframework.common.domain.Updater;
import com.beanframework.console.WebPlatformConstants;
import com.beanframework.console.domain.CustomerCsv;
import com.beanframework.customer.domain.Customer;
import com.beanframework.customer.service.CustomerFacade;
import com.beanframework.user.domain.UserGroup;

public class CustomerUpdate extends Updater {
	protected final Logger logger = LoggerFactory.getLogger(CustomerUpdate.class);

	@Autowired
	private CustomerFacade customerFacade;

	@Value("${module.console.import.update.customer}")
	private String IMPORT_UPDATE_EMPLOYEE_PATH;

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
			resources = loader.getResources(IMPORT_UPDATE_EMPLOYEE_PATH);
			for (Resource resource : resources) {
				try {
					InputStream in = resource.getInputStream();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					IOUtils.copy(in, baos);
					BufferedReader reader = new BufferedReader(new StringReader(new String(baos.toByteArray())));

					List<CustomerCsv> customerCsvList = readCSVFile(reader);
					save(customerCsvList);

				} catch (IOException ex) {
					logger.error("Error reading the resource file: " + ex);
				}
			}
		} catch (IOException ex) {
			logger.error("Error reading the resource file: " + ex);
		}
	}

	public void save(List<CustomerCsv> customerCsvList) {

		for (CustomerCsv customerCsv : customerCsvList) {
			Customer customer = customerFacade.create();
			customer.setId(customerCsv.getId());
//			customer.setName(customerCsv.getName());
			customer.setPassword(customerCsv.getPassword());
			customer.setAccountNonExpired(customerCsv.isAccountNonExpired());
			customer.setAccountNonLocked(customerCsv.isAccountNonLocked());
			customer.setCredentialsNonExpired(customerCsv.isCredentialsNonExpired());
			customer.setEnabled(customerCsv.isEnabled());

			customer.getUserGroups().clear();
			String[] userGroupIds = customerCsv.getUserGroupIds().split(SPLITTER);
			for (int i = 0; i < userGroupIds.length; i++) {
				UserGroup userGroup = new UserGroup();
				userGroup.setId(userGroupIds[i]);
				customer.getUserGroups().add(userGroup);
			}

			MapBindingResult bindingResult = new MapBindingResult(new HashMap<String, Object>(),
					Customer.class.getName());
			customerFacade.save(customer, bindingResult);

			if (bindingResult.hasErrors()) {
				for (ObjectError objectError : bindingResult.getAllErrors()) {
					logger.error(objectError.toString());
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
		final CellProcessor[] processors = new CellProcessor[] { 
				new UniqueHashCode(), // id
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
