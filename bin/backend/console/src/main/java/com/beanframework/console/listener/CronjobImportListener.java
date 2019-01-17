package com.beanframework.console.listener;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

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

import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.console.converter.EntityCsvCronjobConverter;
import com.beanframework.console.csv.CronjobCsv;
import com.beanframework.console.registry.ImportListener;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.service.CronjobManagerService;
import com.beanframework.cronjob.service.CronjobService;

public class CronjobImportListener extends ImportListener {
	protected static Logger LOGGER = LoggerFactory.getLogger(CronjobImportListener.class);

	@Autowired
	private CronjobService cronjobService;

	@Autowired
	private EntityCsvCronjobConverter converter;

	@Autowired
	private CronjobManagerService cronjobManagerService;

	@Value("${module.console.import.update.cronjob}")
	private String IMPORT_UPDATE;

	@Value("${module.console.import.remove.cronjob}")
	private String IMPORT_REMOVE;

	@PostConstruct
	public void importer() {
		setKey(ConsoleImportListenerConstants.CronjobImportListener.KEY);
		setName(ConsoleImportListenerConstants.CronjobImportListener.NAME);
		setSort(ConsoleImportListenerConstants.CronjobImportListener.SORT);
		setDescription(ConsoleImportListenerConstants.CronjobImportListener.DESCRIPTION);
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

			List<CronjobCsv> cronjobCsvList = readCSVFile(reader, CronjobCsv.getUpdateProcessors());
			save(cronjobCsvList);
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

			List<CronjobCsv> cronjobCsvList = readCSVFile(reader, CronjobCsv.getRemoveProcessors());
			remove(cronjobCsvList);
		}
	}

	public List<CronjobCsv> readCSVFile(Reader reader, CellProcessor[] processors) {
		ICsvBeanReader beanReader = null;

		List<CronjobCsv> csvList = new ArrayList<CronjobCsv>();

		try {
			beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);

			// the header elements are used to map the values to the bean (names
			// must match)
			final String[] header = beanReader.getHeader(true);

			CronjobCsv csv;
			LOGGER.info("Start import " + ConsoleImportListenerConstants.CronjobImportListener.NAME);
			while ((csv = beanReader.read(CronjobCsv.class, header, processors)) != null) {
				LOGGER.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(), csv);
				csvList.add(csv);
			}
			LOGGER.info("Finished import " + ConsoleImportListenerConstants.CronjobImportListener.NAME);
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

	public void save(List<CronjobCsv> csvList) throws Exception {

		cronjobManagerService.clearAllScheduler();

		for (CronjobCsv csv : csvList) {

			Cronjob model = converter.convert(csv);
			cronjobService.saveEntity(model);
		}

		cronjobManagerService.initCronJob();
	}

	public void remove(List<CronjobCsv> configurationCsvList) throws Exception {
	}
}