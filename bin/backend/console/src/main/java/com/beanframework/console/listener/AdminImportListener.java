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

import com.beanframework.admin.domain.Admin;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.console.converter.EntityCsvAdminConverter;
import com.beanframework.console.csv.AdminCsv;
import com.beanframework.console.registry.ImportListener;

public class AdminImportListener extends ImportListener {
	protected static Logger LOGGER = LoggerFactory.getLogger(AdminImportListener.class);

	@Autowired
	private ModelService modelService;

	@Autowired
	private EntityCsvAdminConverter converter;

	@Value("${module.console.import.update.admin}")
	private String IMPORT_UPDATE;

	@Value("${module.console.import.remove.admin}")
	private String IMPORT_REMOVE;

	@PostConstruct
	public void importer() {
		setKey(ConsoleImportListenerConstants.AdminImport.KEY);
		setName(ConsoleImportListenerConstants.AdminImport.NAME);
		setSort(ConsoleImportListenerConstants.AdminImport.SORT);
		setDescription(ConsoleImportListenerConstants.AdminImport.DESCRIPTION);
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

			List<AdminCsv> adminCsvList = readCSVFile(reader, AdminCsv.getUpdateProcessors());
			save(adminCsvList);
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

			List<AdminCsv> adminCsvList = readCSVFile(reader, AdminCsv.getRemoveProcessors());
			remove(adminCsvList);
		}
	}

	@Override
	public void updateByContent(String content) throws Exception {
		List<AdminCsv> adminCsvList = readCSVFile(new StringReader(content), AdminCsv.getUpdateProcessors());
		save(adminCsvList);
	}

	@Override
	public void removeByContent(String content) throws Exception {
		List<AdminCsv> adminCsvList = readCSVFile(new StringReader(content), AdminCsv.getUpdateProcessors());
		remove(adminCsvList);
	}

	public List<AdminCsv> readCSVFile(Reader reader, CellProcessor[] processors) {
		ICsvBeanReader beanReader = null;

		List<AdminCsv> csvList = new ArrayList<AdminCsv>();

		try {
			beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);

			// the header elements are used to map the values to the bean (names
			// must match)
			final String[] header = beanReader.getHeader(true);

			AdminCsv csv;
			LOGGER.info("Start import " + ConsoleImportListenerConstants.AdminImport.NAME);
			while ((csv = beanReader.read(AdminCsv.class, header, processors)) != null) {
				LOGGER.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(), csv);
				csvList.add(csv);
			}
			LOGGER.info("Finished import " + ConsoleImportListenerConstants.AdminImport.NAME);
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

	public void save(List<AdminCsv> csvList) throws Exception {

		for (AdminCsv csv : csvList) {

			Admin model = converter.convert(csv);
			modelService.saveEntity(model, Admin.class);
		}
	}

	public void remove(List<AdminCsv> csvList) throws Exception {
		for (AdminCsv csv : csvList) {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Admin.ID, csv.getId());

			Admin entity = modelService.findByProperties(properties, Admin.class);
			modelService.deleteByEntity(entity, Admin.class);
		}
	}
}
