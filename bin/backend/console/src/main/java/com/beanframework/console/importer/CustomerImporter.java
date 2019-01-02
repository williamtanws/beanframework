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

import org.apache.tomcat.util.http.fileupload.IOUtils;
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
import com.beanframework.console.converter.EntityCustomerImporterConverter;
import com.beanframework.console.csv.CustomerCsv;
import com.beanframework.console.registry.Importer;
import com.beanframework.customer.domain.Customer;

public class CustomerImporter extends Importer {
	protected static Logger LOGGER = LoggerFactory.getLogger(CustomerImporter.class);

	@Autowired
	private ModelService modelService;
	
	@Autowired
	private EntityCustomerImporterConverter converter;

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

			Customer model = converter.convert(csv);
			modelService.saveEntity(model, Customer.class);
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
