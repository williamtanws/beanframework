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
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.beanframework.common.service.ModelService;
import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.console.converter.EntityCsvEmployeeConverter;
import com.beanframework.console.csv.EmployeeCsv;
import com.beanframework.console.registry.ImportListener;
import com.beanframework.employee.domain.Employee;
import com.beanframework.employee.service.EmployeeService;

public class EmployeeImportListener extends ImportListener {
	protected static Logger LOGGER = LoggerFactory.getLogger(EmployeeImportListener.class);

	@Autowired
	private ModelService modelService;
	
	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EntityCsvEmployeeConverter converter;

	@Value("${module.console.import.update.employee}")
	private String IMPORT_UPDATE;

	@Value("${module.console.import.remove.employee}")
	private String IMPORT_REMOVE;

	@PostConstruct
	public void importer() {
		setKey(ConsoleImportListenerConstants.EmployeeImportListener.KEY);
		setName(ConsoleImportListenerConstants.EmployeeImportListener.NAME);
		setSort(ConsoleImportListenerConstants.EmployeeImportListener.SORT);
		setDescription(ConsoleImportListenerConstants.EmployeeImportListener.DESCRIPTION);
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
			LOGGER.info("Start import " + ConsoleImportListenerConstants.EmployeeImportListener.NAME);
			while ((csv = beanReader.read(EmployeeCsv.class, header, processors)) != null) {
				LOGGER.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(), csv);
				csvList.add(csv);
			}
			LOGGER.info("Finished import " + ConsoleImportListenerConstants.EmployeeImportListener.NAME);
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

			Employee model = converter.convert(csv);
			employeeService.saveEntity(model);
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
