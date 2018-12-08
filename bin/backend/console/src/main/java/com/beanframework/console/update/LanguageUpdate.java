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

import javax.annotation.PostConstruct;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.beanframework.common.domain.Updater;
import com.beanframework.console.WebPlatformConstants;
import com.beanframework.console.domain.LanguageCsv;
import com.beanframework.language.domain.Language;
import com.beanframework.language.service.LanguageFacade;

public class LanguageUpdate extends Updater {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private LanguageFacade languageFacade;

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
	public void update() {
		PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
		Resource[] resources = null;
		try {
			resources = loader.getResources(LANGUAGE_IMPORT_UPDATE);
			for (Resource resource : resources) {
				try {
					InputStream in = resource.getInputStream();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					IOUtils.copy(in, baos);
					BufferedReader reader = new BufferedReader(new StringReader(new String(baos.toByteArray())));

					List<LanguageCsv> languageCsvList = readCSVFile(reader);
					save(languageCsvList);

				} catch (IOException ex) {
					logger.error("Error reading the resource file: " + ex);
				}
			}
		} catch (IOException ex) {
			logger.error("Error reading the resource file: " + ex);
		}
	}

	public void save(List<LanguageCsv> languageCsvList) {

		for (LanguageCsv languageCsv : languageCsvList) {
			Language language = languageFacade.create();
			language.setId(languageCsv.getId());
			language.setName(languageCsv.getName());
			language.setActive(languageCsv.isActive());
			language.setSort(languageCsv.getSort());

			MapBindingResult bindingResult = new MapBindingResult(new HashMap<String, Object>(),
					Language.class.getName());
			languageFacade.save(language, bindingResult);

			if (bindingResult.hasErrors()) {
				for (ObjectError objectError : bindingResult.getAllErrors()) {
					logger.error(objectError.toString());
				}
			}
		}
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
			logger.info("Start import Language Csv.");
			while ((languageCsv = beanReader.read(LanguageCsv.class, header, processors)) != null) {
				logger.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(),
						languageCsv);
				languageCsvList.add(languageCsv);
			}
			logger.info("Finished import Language Csv.");
		} catch (FileNotFoundException ex) {
			logger.error("Could not find the CSV file: " + ex);
		} catch (IOException ex) {
			logger.error("Error reading the CSV file: " + ex);
		} finally {
			if (beanReader != null) {
				try {
					beanReader.close();
				} catch (IOException ex) {
					logger.error("Error closing the reader: " + ex);
				}
			}
		}
		return languageCsvList;
	}

	public CellProcessor[] getProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { 
				new UniqueHashCode(), // id
				new NotNull(), // name
				new ParseBool(), // active
				new ParseInt() // sort
		};

		return processors;
	}

}