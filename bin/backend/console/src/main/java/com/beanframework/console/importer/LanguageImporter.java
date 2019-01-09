package com.beanframework.console.importer;

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

import com.beanframework.console.PlatformUpdateWebConstants;
import com.beanframework.console.converter.ImportEntityLanguageConverter;
import com.beanframework.console.csv.LanguageCsv;
import com.beanframework.console.registry.Importer;
import com.beanframework.language.domain.Language;
import com.beanframework.language.service.LanguageFacade;

public class LanguageImporter extends Importer {
	protected static Logger LOGGER = LoggerFactory.getLogger(LanguageImporter.class);

	@Autowired
	private LanguageFacade languageFacade;

	@Autowired
	private ImportEntityLanguageConverter converter;

	@Value("${module.console.import.update.language}")
	private String IMPORT_UPDATE;

	@Value("${module.console.import.remove.language}")
	private String IMPORT_REMOVE;

	@PostConstruct
	public void importer() {
		setKey(PlatformUpdateWebConstants.Importer.LanguageImporter.KEY);
		setName(PlatformUpdateWebConstants.Importer.LanguageImporter.NAME);
		setSort(PlatformUpdateWebConstants.Importer.LanguageImporter.SORT);
		setDescription(PlatformUpdateWebConstants.Importer.LanguageImporter.DESCRIPTION);
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

			List<LanguageCsv> languageCsvList = readCSVFile(reader, LanguageCsv.getUpdateProcessors());
			save(languageCsvList);
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

			List<LanguageCsv> languageCsvList = readCSVFile(reader, LanguageCsv.getRemoveProcessors());
			remove(languageCsvList);
		}
	}

	public List<LanguageCsv> readCSVFile(Reader reader, CellProcessor[] processors) {
		ICsvBeanReader beanReader = null;

		List<LanguageCsv> csvList = new ArrayList<LanguageCsv>();

		try {
			beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);

			// the header elements are used to map the values to the bean (names
			// must match)
			final String[] header = beanReader.getHeader(true);

			LanguageCsv csv;
			LOGGER.info("Start import " + PlatformUpdateWebConstants.Importer.LanguageImporter.NAME);
			while ((csv = beanReader.read(LanguageCsv.class, header, processors)) != null) {
				LOGGER.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(), csv);
				csvList.add(csv);
			}
			LOGGER.info("Finished import " + PlatformUpdateWebConstants.Importer.LanguageImporter.NAME);
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

	public void save(List<LanguageCsv> csvList) throws Exception {

		for (LanguageCsv csv : csvList) {

			Language model = converter.convert(csv);
			languageFacade.saveEntity(model);
		}
	}

	public void remove(List<LanguageCsv> csvList) throws Exception {
		for (LanguageCsv csv : csvList) {
			languageFacade.deleteById(csv.getId());
		}
	}

}
