package com.beanframework.console.remove;

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
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.beanframework.common.Remover;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.WebPlatformConstants;
import com.beanframework.console.domain.CustomerCsv;
import com.beanframework.customer.domain.Customer;

public class CustomerRemove extends Remover {
	protected final Logger logger = LoggerFactory.getLogger(CustomerRemove.class);
	
	@Autowired
	private ModelService modelService;

	@Value("${module.console.import.remove.customer}")
	private String IMPORT_REMOVE_EMPLOYEE_PATH;

	@PostConstruct
	public void updater() {
		setKey(WebPlatformConstants.Remove.Customer.KEY);
		setName(WebPlatformConstants.Remove.Customer.NAME);
		setSort(WebPlatformConstants.Remove.Customer.SORT);
		setDescription(WebPlatformConstants.Remove.Customer.DESCRIPTION);
	}

	@Override
	public void remove() {
		PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
		Resource[] resources = null;
		try {
			resources = loader.getResources(IMPORT_REMOVE_EMPLOYEE_PATH);
			for (Resource resource : resources) {
				try {
					InputStream in = resource.getInputStream();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					IOUtils.copy(in, baos);
					BufferedReader reader = new BufferedReader(new StringReader(new String(baos.toByteArray())));

					List<CustomerCsv> customerCsvList = readCSVFile(reader);
					remove(customerCsvList);

				} catch (Exception ex) {
					logger.error("Error reading the resource file: " + ex);
				}
			}
		} catch (IOException ex) {
			logger.error("Error reading the resource file: " + ex);
		}
	}

	public void remove(List<CustomerCsv> customerCsvList) throws Exception {

		for (CustomerCsv csv : customerCsvList) {
			
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Customer.ID, csv.getId());
			
			Customer customer = modelService.findOneDtoByProperties(properties, Customer.class);
			try {
				modelService.remove(customer.getUuid(), Customer.class);
			} catch (BusinessException e) {
				logger.error(e.getMessage(), e);
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
				new UniqueHashCode() // id
		};

		return processors;
	}

}
