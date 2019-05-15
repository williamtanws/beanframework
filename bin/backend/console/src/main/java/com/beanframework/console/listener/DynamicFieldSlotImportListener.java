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
import com.beanframework.console.converter.EntityCsvDynamicFieldSlotConverter;
import com.beanframework.console.csv.DynamicFieldSlotCsv;
import com.beanframework.console.registry.ImportListener;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;

public class DynamicFieldSlotImportListener extends ImportListener {
	protected static Logger LOGGER = LoggerFactory.getLogger(DynamicFieldSlotImportListener.class);

	@Autowired
	private ModelService modelService;

	@Autowired
	private EntityCsvDynamicFieldSlotConverter converter;

	@Value("${module.console.import.update.dynamicfieldslot}")
	private String IMPORT_UPDATE;

	@Value("${module.console.import.remove.dynamicfieldslot}")
	private String IMPORT_REMOVE;

	@PostConstruct
	public void importer() {
		setKey(ConsoleImportListenerConstants.DynamicFieldSlotImport.KEY);
		setName(ConsoleImportListenerConstants.DynamicFieldSlotImport.NAME);
		setSort(ConsoleImportListenerConstants.DynamicFieldSlotImport.SORT);
		setDescription(ConsoleImportListenerConstants.DynamicFieldSlotImport.DESCRIPTION);
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

			List<DynamicFieldSlotCsv> cronjobCsvList = readCSVFile(reader, DynamicFieldSlotCsv.getUpdateProcessors());
			save(cronjobCsvList);
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

			List<DynamicFieldSlotCsv> cronjobCsvList = readCSVFile(reader, DynamicFieldSlotCsv.getRemoveProcessors());
			remove(cronjobCsvList);
		}
	}

	@Override
	public void updateByContent(String content) throws Exception {
		List<DynamicFieldSlotCsv> csvList = readCSVFile(new StringReader(content), DynamicFieldSlotCsv.getUpdateProcessors());
		save(csvList);
	}

	@Override
	public void removeByContent(String content) throws Exception {
		List<DynamicFieldSlotCsv> csvList = readCSVFile(new StringReader(content), DynamicFieldSlotCsv.getUpdateProcessors());
		remove(csvList);
	}

	public List<DynamicFieldSlotCsv> readCSVFile(Reader reader, CellProcessor[] processors) {
		ICsvBeanReader beanReader = null;

		List<DynamicFieldSlotCsv> csvList = new ArrayList<DynamicFieldSlotCsv>();

		try {
			beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);

			// the header elements are used to map the values to the bean (names
			// must match)
			final String[] header = beanReader.getHeader(true);

			DynamicFieldSlotCsv csv;
			LOGGER.info("Start import " + ConsoleImportListenerConstants.DynamicFieldSlotImport.NAME);
			while ((csv = beanReader.read(DynamicFieldSlotCsv.class, header, processors)) != null) {
				LOGGER.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(), csv);
				csvList.add(csv);
			}
			LOGGER.info("Finished import " + ConsoleImportListenerConstants.DynamicFieldSlotImport.NAME);
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

	public void save(List<DynamicFieldSlotCsv> csvList) throws Exception {

		for (DynamicFieldSlotCsv csv : csvList) {

			DynamicFieldSlot model = converter.convert(csv);
			modelService.saveEntity(model, DynamicFieldSlot.class);
		}
	}

	public void remove(List<DynamicFieldSlotCsv> csvList) throws Exception {
		for (DynamicFieldSlotCsv csv : csvList) {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(DynamicFieldSlot.ID, csv.getId());

			DynamicFieldSlot entity = modelService.findOneByProperties(properties, DynamicFieldSlot.class);
			modelService.deleteByEntity(entity, DynamicFieldSlot.class);
		}
	}

}
