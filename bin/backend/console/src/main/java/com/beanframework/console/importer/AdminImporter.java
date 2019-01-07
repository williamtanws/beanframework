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

import com.beanframework.admin.domain.Admin;
import com.beanframework.admin.service.AdminFacade;
import com.beanframework.console.PlatformUpdateWebConstants;
import com.beanframework.console.converter.EntityAdminImporterConverter;
import com.beanframework.console.csv.AdminCsv;
import com.beanframework.console.registry.Importer;

public class AdminImporter extends Importer {
	protected static Logger LOGGER = LoggerFactory.getLogger(AdminImporter.class);

	@Autowired
	private AdminFacade adminFacade;

	@Autowired
	private EntityAdminImporterConverter converter;

	@Value("${module.console.import.update.admin}")
	private String IMPORT_UPDATE;

	@Value("${module.console.import.remove.admin}")
	private String IMPORT_REMOVE;

	@PostConstruct
	public void importer() {
		setKey(PlatformUpdateWebConstants.Importer.AdminImporter.KEY);
		setName(PlatformUpdateWebConstants.Importer.AdminImporter.NAME);
		setSort(PlatformUpdateWebConstants.Importer.AdminImporter.SORT);
		setDescription(PlatformUpdateWebConstants.Importer.AdminImporter.DESCRIPTION);
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

			List<AdminCsv> adminCsvList = readCSVFile(reader, AdminCsv.getUpdateProcessors());
			save(adminCsvList);
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

			List<AdminCsv> adminCsvList = readCSVFile(reader, AdminCsv.getRemoveProcessors());
			remove(adminCsvList);
		}
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
			LOGGER.info("Start import " + PlatformUpdateWebConstants.Importer.AdminImporter.NAME);
			while ((csv = beanReader.read(AdminCsv.class, header, processors)) != null) {
				LOGGER.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(), csv);
				csvList.add(csv);
			}
			LOGGER.info("Finished import " + PlatformUpdateWebConstants.Importer.AdminImporter.NAME);
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
			adminFacade.saveEntity(model);
		}
	}

	public void remove(List<AdminCsv> csvList) throws Exception {
		for (AdminCsv csv : csvList) {
			adminFacade.deleteById(csv.getId());
		}
	}

}
