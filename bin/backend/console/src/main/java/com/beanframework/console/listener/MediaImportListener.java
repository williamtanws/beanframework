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
import com.beanframework.console.converter.EntityCsvMediaConverter;
import com.beanframework.console.csv.MediaCsv;
import com.beanframework.console.registry.ImportListener;
import com.beanframework.media.domain.Media;

public class MediaImportListener extends ImportListener {
	protected static Logger LOGGER = LoggerFactory.getLogger(MediaImportListener.class);

	@Autowired
	private ModelService modelService;

	@Autowired
	private EntityCsvMediaConverter converter;

	@Value("${module.console.import.update.media}")
	private String IMPORT_UPDATE;

	@Value("${module.console.import.remove.media}")
	private String IMPORT_REMOVE;

	@PostConstruct
	public void importer() {
		setKey(ConsoleImportListenerConstants.MediaImport.KEY);
		setName(ConsoleImportListenerConstants.MediaImport.NAME);
		setSort(ConsoleImportListenerConstants.MediaImport.SORT);
		setDescription(ConsoleImportListenerConstants.MediaImport.DESCRIPTION);
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

			List<MediaCsv> mediaCsvList = readCSVFile(reader, MediaCsv.getUpdateProcessors());
			save(mediaCsvList);
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

			List<MediaCsv> mediaCsvList = readCSVFile(reader, MediaCsv.getRemoveProcessors());
			remove(mediaCsvList);
		}
	}

	@Override
	public void updateByContent(String content) throws Exception {
		List<MediaCsv> csvList = readCSVFile(new StringReader(content), MediaCsv.getUpdateProcessors());
		save(csvList);
	}

	@Override
	public void removeByContent(String content) throws Exception {
		List<MediaCsv> csvList = readCSVFile(new StringReader(content), MediaCsv.getUpdateProcessors());
		remove(csvList);
	}

	public List<MediaCsv> readCSVFile(Reader reader, CellProcessor[] processors) {
		ICsvBeanReader beanReader = null;

		List<MediaCsv> csvList = new ArrayList<MediaCsv>();

		try {
			beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);

			// the header elements are used to map the values to the bean (names
			// must match)
			final String[] header = beanReader.getHeader(true);

			MediaCsv csv;
			LOGGER.info("Start import " + ConsoleImportListenerConstants.MediaImport.NAME);
			while ((csv = beanReader.read(MediaCsv.class, header, processors)) != null) {
				LOGGER.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(), csv);
				csvList.add(csv);
			}
			LOGGER.info("Finished import " + ConsoleImportListenerConstants.MediaImport.NAME);
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

	public void save(List<MediaCsv> csvList) throws Exception {

		for (MediaCsv csv : csvList) {

			Media model = converter.convert(csv);
			modelService.saveEntity(model, Media.class);
		}
	}

	public void remove(List<MediaCsv> csvList) throws Exception {
		for (MediaCsv csv : csvList) {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Media.ID, csv.getId());

			Media entity = modelService.findOneByProperties(properties, Media.class);
			modelService.deleteByEntity(entity, Media.class);
		}
	}

}
