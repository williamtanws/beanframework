package com.beanframework.console.listener;

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

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.beanframework.common.service.ModelService;
import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.console.converter.EntityCsvCustomerConverter;
import com.beanframework.console.csv.CustomerCsv;
import com.beanframework.console.registry.ImportListener;
import com.beanframework.customer.domain.Customer;
import com.beanframework.user.service.UserService;

public class CustomerImportListener extends ImportListener {
	protected static Logger LOGGER = LoggerFactory.getLogger(CustomerImportListener.class);
	
	@Autowired
	private ModelService modelService;

	@Autowired
	private UserService userService;

	@Autowired
	private EntityCsvCustomerConverter converter;

	@Value("${module.console.import.update.customer}")
	private String IMPORT_UPDATE;

	@Value("${module.console.import.remove.customer}")
	private String IMPORT_REMOVE;

	@PostConstruct
	public void importer() {
		setKey(ConsoleImportListenerConstants.CustomerImport.KEY);
		setName(ConsoleImportListenerConstants.CustomerImport.NAME);
		setSort(ConsoleImportListenerConstants.CustomerImport.SORT);
		setDescription(ConsoleImportListenerConstants.CustomerImport.DESCRIPTION);
	}

	@Override
	public void update() throws Exception {
		updateByPath(IMPORT_UPDATE);
	}

	@Override
	public void updateByPath(String path) throws Exception {
		PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
		Resource[] resources = loader.getResources(path);
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
		removeByPath(IMPORT_REMOVE);
	}

	@Override
	public void removeByPath(String path) throws Exception {
		PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
		Resource[] resources = loader.getResources(path);
		for (Resource resource : resources) {
			InputStream in = resource.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtils.copy(in, baos);
			BufferedReader reader = new BufferedReader(new StringReader(new String(baos.toByteArray())));

			List<CustomerCsv> customerCsvList = readCSVFile(reader, CustomerCsv.getRemoveProcessors());
			remove(customerCsvList);
		}
	}

	@Override
	public void updateByContent(String content) throws Exception {
		List<CustomerCsv> csvList = readCSVFile(new StringReader(content), CustomerCsv.getUpdateProcessors());
		save(csvList);
	}

	@Override
	public void removeByContent(String content) throws Exception {
		List<CustomerCsv> csvList = readCSVFile(new StringReader(content), CustomerCsv.getUpdateProcessors());
		remove(csvList);
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
			LOGGER.info("Start import " + ConsoleImportListenerConstants.CustomerImport.NAME);
			while ((csv = beanReader.read(CustomerCsv.class, header, processors)) != null) {
				LOGGER.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(), csv);
				csvList.add(csv);
			}
			LOGGER.info("Finished import " + ConsoleImportListenerConstants.CustomerImport.NAME);
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

			Customer model = converter.convert(csv);
			model = (Customer) modelService.saveEntity(model, Customer.class);

			ClassPathResource resource = new ClassPathResource(csv.getProfilePicture());
			userService.saveProfilePicture(model, resource.getInputStream());
		}
	}

	public void remove(List<CustomerCsv> csvList) throws Exception {
		for (CustomerCsv csv : csvList) {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Customer.ID, csv.getId());

			Customer entity = modelService.findOneByProperties(properties, Customer.class);
			modelService.deleteByEntity(entity, Customer.class);
		}
	}

}
