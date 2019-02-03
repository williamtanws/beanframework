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

import com.beanframework.cms.domain.Site;
import com.beanframework.cms.service.SiteService;
import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.console.converter.EntityCsvSiteConverter;
import com.beanframework.console.csv.SiteCsv;
import com.beanframework.console.registry.ImportListener;

public class SiteImportListener extends ImportListener {
	protected static Logger LOGGER = LoggerFactory.getLogger(SiteImportListener.class);

	@Autowired
	private SiteService mediaService;

	@Autowired
	private EntityCsvSiteConverter converter;

	@Value("${module.console.import.update.site}")
	private String IMPORT_UPDATE;

	@Value("${module.console.import.remove.site}")
	private String IMPORT_REMOVE;

	@PostConstruct
	public void importer() {
		setKey(ConsoleImportListenerConstants.SiteImport.KEY);
		setName(ConsoleImportListenerConstants.SiteImport.NAME);
		setSort(ConsoleImportListenerConstants.SiteImport.SORT);
		setDescription(ConsoleImportListenerConstants.SiteImport.DESCRIPTION);
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

			List<SiteCsv> mediaCsvList = readCSVFile(reader, SiteCsv.getUpdateProcessors());
			save(mediaCsvList);
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

			List<SiteCsv> mediaCsvList = readCSVFile(reader, SiteCsv.getRemoveProcessors());
			remove(mediaCsvList);
		}
	}

	public List<SiteCsv> readCSVFile(Reader reader, CellProcessor[] processors) {
		ICsvBeanReader beanReader = null;

		List<SiteCsv> csvList = new ArrayList<SiteCsv>();

		try {
			beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);

			// the header elements are used to map the values to the bean (names
			// must match)
			final String[] header = beanReader.getHeader(true);

			SiteCsv csv;
			LOGGER.info("Start import " + ConsoleImportListenerConstants.SiteImport.NAME);
			while ((csv = beanReader.read(SiteCsv.class, header, processors)) != null) {
				LOGGER.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(), csv);
				csvList.add(csv);
			}
			LOGGER.info("Finished import " + ConsoleImportListenerConstants.SiteImport.NAME);
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

	public void save(List<SiteCsv> csvList) throws Exception {

		for (SiteCsv csv : csvList) {

			Site model = converter.convert(csv);
			mediaService.saveEntity(model);
		}
	}

	public void remove(List<SiteCsv> csvList) throws Exception {

	}

}