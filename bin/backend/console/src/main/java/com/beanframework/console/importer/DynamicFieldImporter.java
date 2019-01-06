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
import com.beanframework.console.PlatformUpdateWebConstants;
import com.beanframework.console.converter.EntityDynamicFieldImporterConverter;
import com.beanframework.console.csv.DynamicFieldCsv;
import com.beanframework.console.registry.Importer;
import com.beanframework.dynamicfield.domain.DynamicField;

public class DynamicFieldImporter extends Importer {
	protected static Logger LOGGER = LoggerFactory.getLogger(DynamicFieldImporter.class);

	@Autowired
	private ModelService modelService;

	@Autowired
	private EntityDynamicFieldImporterConverter converter;

	@Value("${module.console.import.update.dynamicfield}")
	private String IMPORT_UPDATE;

	@Value("${module.console.import.remove.dynamicfield}")
	private String IMPORT_REMOVE;

	@PostConstruct
	public void importer() {
		setKey(PlatformUpdateWebConstants.Importer.DynamicFieldImporter.KEY);
		setName(PlatformUpdateWebConstants.Importer.DynamicFieldImporter.NAME);
		setSort(PlatformUpdateWebConstants.Importer.DynamicFieldImporter.SORT);
		setDescription(PlatformUpdateWebConstants.Importer.DynamicFieldImporter.DESCRIPTION);
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

			List<DynamicFieldCsv> cronjobCsvList = readCSVFile(reader, DynamicFieldCsv.getUpdateProcessors());
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

			List<DynamicFieldCsv> cronjobCsvList = readCSVFile(reader, DynamicFieldCsv.getRemoveProcessors());
			remove(cronjobCsvList);
		}
	}

	public List<DynamicFieldCsv> readCSVFile(Reader reader, CellProcessor[] processors) {
		ICsvBeanReader beanReader = null;

		List<DynamicFieldCsv> csvList = new ArrayList<DynamicFieldCsv>();

		try {
			beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);

			// the header elements are used to map the values to the bean (names
			// must match)
			final String[] header = beanReader.getHeader(true);

			DynamicFieldCsv csv;
			LOGGER.info("Start import " + PlatformUpdateWebConstants.Importer.DynamicFieldImporter.NAME);
			while ((csv = beanReader.read(DynamicFieldCsv.class, header, processors)) != null) {
				LOGGER.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(), csv);
				csvList.add(csv);
			}
			LOGGER.info("Finished import " + PlatformUpdateWebConstants.Importer.DynamicFieldImporter.NAME);
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

	public void save(List<DynamicFieldCsv> csvList) throws Exception {

		for (DynamicFieldCsv csv : csvList) {

			DynamicField model = converter.convert(csv);

			modelService.saveEntity(model, DynamicField.class);
		}
	}

	private void remove(List<DynamicFieldCsv> csvList) throws Exception {
		for (DynamicFieldCsv csv : csvList) {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(DynamicField.ID, csv.getId());
			DynamicField model = modelService.findOneEntityByProperties(properties, DynamicField.class);
			modelService.deleteByEntity(model, DynamicField.class);
		}
	}

}
