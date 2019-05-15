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
import com.beanframework.console.converter.EntityCsvDynamicFieldTemplateConverter;
import com.beanframework.console.csv.DynamicFieldTemplateCsv;
import com.beanframework.console.registry.ImportListener;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;

public class DynamicFieldTemplateImportListener extends ImportListener {
	protected static Logger LOGGER = LoggerFactory.getLogger(DynamicFieldTemplateImportListener.class);

	@Autowired
	private ModelService modelService;

	@Autowired
	private EntityCsvDynamicFieldTemplateConverter converter;

	@Value("${module.console.import.update.dynamicfieldtemplate}")
	private String IMPORT_UPDATE;

	@Value("${module.console.import.remove.dynamicfieldtemplate}")
	private String IMPORT_REMOVE;

	@PostConstruct
	public void importer() {
		setKey(ConsoleImportListenerConstants.DynamicFieldTemplateImport.KEY);
		setName(ConsoleImportListenerConstants.DynamicFieldTemplateImport.NAME);
		setSort(ConsoleImportListenerConstants.DynamicFieldTemplateImport.SORT);
		setDescription(ConsoleImportListenerConstants.DynamicFieldTemplateImport.DESCRIPTION);
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

			List<DynamicFieldTemplateCsv> cronjobCsvList = readCSVFile(reader, DynamicFieldTemplateCsv.getUpdateProcessors());
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

			List<DynamicFieldTemplateCsv> cronjobCsvList = readCSVFile(reader, DynamicFieldTemplateCsv.getRemoveProcessors());
			remove(cronjobCsvList);
		}
	}

	@Override
	public void updateByContent(String content) throws Exception {
		List<DynamicFieldTemplateCsv> csvList = readCSVFile(new StringReader(content), DynamicFieldTemplateCsv.getUpdateProcessors());
		save(csvList);
	}

	@Override
	public void removeByContent(String content) throws Exception {
		List<DynamicFieldTemplateCsv> csvList = readCSVFile(new StringReader(content), DynamicFieldTemplateCsv.getUpdateProcessors());
		remove(csvList);
	}

	public List<DynamicFieldTemplateCsv> readCSVFile(Reader reader, CellProcessor[] processors) {
		ICsvBeanReader beanReader = null;

		List<DynamicFieldTemplateCsv> csvList = new ArrayList<DynamicFieldTemplateCsv>();

		try {
			beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);

			// the header elements are used to map the values to the bean (names
			// must match)
			final String[] header = beanReader.getHeader(true);

			DynamicFieldTemplateCsv csv;
			LOGGER.info("Start import " + ConsoleImportListenerConstants.DynamicFieldTemplateImport.NAME);
			while ((csv = beanReader.read(DynamicFieldTemplateCsv.class, header, processors)) != null) {
				LOGGER.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(), csv);
				csvList.add(csv);
			}
			LOGGER.info("Finished import " + ConsoleImportListenerConstants.DynamicFieldTemplateImport.NAME);
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

	public void save(List<DynamicFieldTemplateCsv> csvList) throws Exception {

		for (DynamicFieldTemplateCsv csv : csvList) {

			DynamicFieldTemplate model = converter.convert(csv);
			modelService.saveEntity(model, DynamicFieldTemplate.class);
		}
	}

	public void remove(List<DynamicFieldTemplateCsv> csvList) throws Exception {
		for (DynamicFieldTemplateCsv csv : csvList) {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(DynamicFieldTemplate.ID, csv.getId());

			DynamicFieldTemplate entity = modelService.findOneByProperties(properties, DynamicFieldTemplate.class);
			modelService.deleteByEntity(entity, DynamicFieldTemplate.class);
		}
	}

}
