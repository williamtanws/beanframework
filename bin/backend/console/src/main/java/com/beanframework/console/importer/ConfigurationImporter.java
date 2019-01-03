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
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.console.WebPlatformUpdateConstants;
import com.beanframework.console.converter.EntityConfigurationImporterConverter;
import com.beanframework.console.csv.ConfigurationCsv;
import com.beanframework.console.registry.Importer;

public class ConfigurationImporter extends Importer {
	protected static Logger LOGGER = LoggerFactory.getLogger(ConfigurationImporter.class);

	@Autowired
	private ModelService modelService;
	
	@Autowired
	private EntityConfigurationImporterConverter converter;

	@Value("${module.console.import.update.configuration}")
	private String IMPORT_UPDATE;

	@Value("${module.console.import.remove.configuration}")
	private String IMPORT_REMOVE;

	@PostConstruct
	public void importer() {
		setKey(WebPlatformUpdateConstants.Importer.Configuration.KEY);
		setName(WebPlatformUpdateConstants.Importer.Configuration.NAME);
		setSort(WebPlatformUpdateConstants.Importer.Configuration.SORT);
		setDescription(WebPlatformUpdateConstants.Importer.Configuration.DESCRIPTION);
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

			List<ConfigurationCsv> configurationCsvList = readCSVFile(reader, ConfigurationCsv.getUpdateProcessors());
			save(configurationCsvList);
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

			List<ConfigurationCsv> configurationCsvList = readCSVFile(reader, ConfigurationCsv.getRemoveProcessors());
			remove(configurationCsvList);
		}
	}

	public List<ConfigurationCsv> readCSVFile(Reader reader, CellProcessor[] processors) {
		ICsvBeanReader beanReader = null;

		List<ConfigurationCsv> csvList = new ArrayList<ConfigurationCsv>();

		try {
			beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);

			// the header elements are used to map the values to the bean (names
			// must match)
			final String[] header = beanReader.getHeader(true);

			ConfigurationCsv csv;
			LOGGER.info("Start import "+WebPlatformUpdateConstants.Importer.Configuration.NAME);
			while ((csv = beanReader.read(ConfigurationCsv.class, header, processors)) != null) {
				LOGGER.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(), csv);
				csvList.add(csv);
			}
			LOGGER.info("Finished import "+WebPlatformUpdateConstants.Importer.Configuration.NAME);
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

	public void save(List<ConfigurationCsv> csvList) throws Exception {

		for (ConfigurationCsv csv : csvList) {

			Configuration model = converter.convert(csv);

			modelService.saveEntity(model, Configuration.class);
		}
	}

	public void remove(List<ConfigurationCsv> csvList) throws Exception {
		for (ConfigurationCsv csv : csvList) {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Configuration.ID, csv.getId());
			Configuration model = modelService.findOneEntityByProperties(properties, Configuration.class);
			modelService.deleteByEntity(model, Configuration.class);
		}
	}

}