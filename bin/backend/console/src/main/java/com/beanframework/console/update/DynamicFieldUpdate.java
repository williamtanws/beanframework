package com.beanframework.console.update;

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
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.beanframework.common.Updater;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.WebPlatformConstants;
import com.beanframework.console.data.DynamicFieldCsv;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.domain.DynamicFieldTypeEnum;
import com.beanframework.language.domain.Language;

public class DynamicFieldUpdate extends Updater {
	private static Logger LOGGER = LoggerFactory.getLogger(DynamicFieldUpdate.class);

	@Autowired
	private ModelService modelService;

	@Value("${module.console.import.update.dynamicfield}")
	private String IMPORT_UPDATE;

	@PostConstruct
	public void updater() {
		setKey(WebPlatformConstants.Update.DynamicField.KEY);
		setName(WebPlatformConstants.Update.DynamicField.NAME);
		setSort(WebPlatformConstants.Update.DynamicField.SORT);
		setDescription(WebPlatformConstants.Update.DynamicField.DESCRIPTION);
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

			List<DynamicFieldCsv> cronjobCsvList = readCSVFile(reader);
			save(cronjobCsvList);
		}
	}

	public void save(List<DynamicFieldCsv> cronjobCsvList) throws Exception {

		for (DynamicFieldCsv csv : cronjobCsvList) {
			Map<String, Object> dynamicFieldProperties = new HashMap<String, Object>();
			dynamicFieldProperties.put(DynamicField.ID, csv.getId());
			DynamicField model = modelService.findOneEntityByProperties(dynamicFieldProperties, DynamicField.class);
			
			if(model == null) {
				model = modelService.create(DynamicField.class);
				model.setId(csv.getId());
			}
			model.setName(csv.getName());
			model.setFieldType(DynamicFieldTypeEnum.valueOf(csv.getType()));
			model.setSort(csv.getSort());
			model.setRequired(csv.isRequired());
			model.setRule(csv.getRule());
			model.setFieldGroup(csv.getGroup());
			model.setLabel(csv.getLabel());
			
			Map<String, Object> languageProperties = new HashMap<String, Object>();
			languageProperties.put(Language.ID, csv.getLanguage());
			Language modelLanguage = modelService.findOneEntityByProperties(languageProperties, Language.class);
			model.setLanguage(modelLanguage);
			
			modelService.saveEntity(model, DynamicField.class);
		}
	}

	public List<DynamicFieldCsv> readCSVFile(Reader reader) {
		ICsvBeanReader beanReader = null;

		List<DynamicFieldCsv> cronjobCsvList = new ArrayList<DynamicFieldCsv>();

		try {
			beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);

			// the header elements are used to map the values to the bean (names
			// must match)
			final String[] header = beanReader.getHeader(true);
			final CellProcessor[] processors = getProcessors();

			DynamicFieldCsv cronjobCsv;
			LOGGER.info("Start import DynamicField Csv.");
			while ((cronjobCsv = beanReader.read(DynamicFieldCsv.class, header, processors)) != null) {
				LOGGER.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(),
						cronjobCsv);
				cronjobCsvList.add(cronjobCsv);
			}
			LOGGER.info("Finished import DynamicField Csv.");
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
		return cronjobCsvList;
	}

	public CellProcessor[] getProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { 
				new UniqueHashCode(), // id
				new NotNull(), // name
				new NotNull(), // type
				new ParseInt(), // sort
				new ParseBool(), // required
				new Optional(), // rule
				new NotNull(), // group
				new NotNull(), // label
				new NotNull() // language
		};

		return processors;
	}

}
