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
import com.beanframework.console.domain.LanguageCsv;
import com.beanframework.language.domain.Language;

public class LanguageUpdate extends Updater {
	private static Logger LOGGER = LoggerFactory.getLogger(LanguageUpdate.class);

	@Autowired
	private ModelService modelService;

	@Value("${module.console.import.update.language}")
	private String LANGUAGE_IMPORT_UPDATE;

	@PostConstruct
	public void updater() {
		setKey(WebPlatformConstants.Update.Language.KEY);
		setName(WebPlatformConstants.Update.Language.NAME);
		setSort(WebPlatformConstants.Update.Language.SORT);
		setDescription(WebPlatformConstants.Update.Language.DESCRIPTION);
	}

	@Override
	public void update() throws Exception {
		PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
		Resource[] resources = loader.getResources(LANGUAGE_IMPORT_UPDATE);
		for (Resource resource : resources) {
			InputStream in = resource.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtils.copy(in, baos);
			BufferedReader reader = new BufferedReader(new StringReader(new String(baos.toByteArray())));

			List<LanguageCsv> languageCsvList = readCSVFile(reader);
			save(languageCsvList);
		}
	}

	public void save(List<LanguageCsv> languageCsvList) throws Exception {

		for (LanguageCsv csv : languageCsvList) {

			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Language.ID, csv.getId());

			Language language = modelService.findOneEntityByProperties(properties, Language.class);

			if (language == null) {
				language = modelService.create(Language.class);
				language.setId(csv.getId());
			}
			language.setName(csv.getName());
			language.setActive(csv.isActive());
			language.setSort(csv.getSort());

			modelService.saveEntity(language, Language.class);
		}

		modelService.saveAll();
	}

	public List<LanguageCsv> readCSVFile(Reader reader) {
		ICsvBeanReader beanReader = null;

		List<LanguageCsv> languageCsvList = new ArrayList<LanguageCsv>();

		try {
			beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);

			// the header elements are used to map the values to the bean (names
			// must match)
			final String[] header = beanReader.getHeader(true);
			final CellProcessor[] processors = getProcessors();

			LanguageCsv languageCsv;
			LOGGER.info("Start import Language Csv.");
			while ((languageCsv = beanReader.read(LanguageCsv.class, header, processors)) != null) {
				LOGGER.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(),
						languageCsv);
				languageCsvList.add(languageCsv);
			}
			LOGGER.info("Finished import Language Csv.");
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
		return languageCsvList;
	}

	public CellProcessor[] getProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { new UniqueHashCode(), // id
				new NotNull(), // name
				new ParseBool(), // active
				new ParseInt() // sort
		};

		return processors;
	}

}
