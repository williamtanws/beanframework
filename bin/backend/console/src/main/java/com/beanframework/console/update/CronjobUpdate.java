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

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.Token;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.beanframework.common.Updater;
import com.beanframework.console.WebPlatformConstants;
import com.beanframework.console.domain.CronjobCsv;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.domain.CronjobData;
import com.beanframework.cronjob.domain.CronjobEnum;
import com.beanframework.cronjob.service.CronjobFacade;

public class CronjobUpdate extends Updater {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CronjobFacade cronjobFacade;

	@Value("${module.console.import.update.cronjob}")
	private String LANGUAGE_IMPORT_UPDATE;

	@PostConstruct
	public void updater() {
		setKey(WebPlatformConstants.Update.Cronjob.KEY);
		setName(WebPlatformConstants.Update.Cronjob.NAME);
		setSort(WebPlatformConstants.Update.Cronjob.SORT);
		setDescription(WebPlatformConstants.Update.Cronjob.DESCRIPTION);
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

					List<CronjobCsv> cronjobCsvList = readCSVFile(reader);
					save(cronjobCsvList);

				} catch (IOException ex) {
					logger.error("Error reading the resource file: " + ex);
				}
			}
		} catch (IOException ex) {
			logger.error("Error reading the resource file: " + ex);
		}
	}

	public void save(List<CronjobCsv> cronjobCsvList) {

		for (CronjobCsv cronjobCsv : cronjobCsvList) {
			Cronjob cronjob = cronjobFacade.create();
			cronjob.setId(cronjobCsv.getId());
			cronjob.setJobClass(cronjobCsv.getJobClass());
			cronjob.setJobGroup(cronjobCsv.getJobGroup());
			cronjob.setJobName(cronjobCsv.getJobName());
			cronjob.setDescription(cronjobCsv.getDescription());
			cronjob.setCronExpression(cronjobCsv.getCronExpression());
			cronjob.setStartup(cronjobCsv.isStartup());

			if (StringUtils.isNotEmpty(cronjobCsv.getCronjobData())) {
				String[] cronjobDataList = cronjobCsv.getCronjobData().split(SPLITTER);

				for (String cronjobDataString : cronjobDataList) {
					String name = cronjobDataString.split(EQUALS)[0];
					String value = cronjobDataString.split(EQUALS)[1];

					CronjobData cronjobData = new CronjobData();
					cronjobData.setName(name);
					cronjobData.setValue(value);
					cronjob.getCronjobDatas().add(cronjobData);
				}
			}

			MapBindingResult bindingResult = new MapBindingResult(new HashMap<String, Object>(),
					Cronjob.class.getName());
			cronjobFacade.save(cronjob, bindingResult);

			if (StringUtils.isNotEmpty(cronjobCsv.getJobTrigger())) {
				cronjob.setJobTrigger(CronjobEnum.JobTrigger.fromName(cronjobCsv.getJobTrigger()));
				cronjob.setTriggerStartDate(cronjobCsv.getTriggerStartDate());
				cronjobFacade.trigger(cronjob, bindingResult);
			}

			if (bindingResult.hasErrors()) {
				for (ObjectError objectError : bindingResult.getAllErrors()) {
					logger.error(objectError.toString());
				}
			}
		}
	}

	public List<CronjobCsv> readCSVFile(Reader reader) {
		ICsvBeanReader beanReader = null;

		List<CronjobCsv> cronjobCsvList = new ArrayList<CronjobCsv>();

		try {
			beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);

			// the header elements are used to map the values to the bean (names
			// must match)
			final String[] header = beanReader.getHeader(true);
			final CellProcessor[] processors = getProcessors();

			CronjobCsv cronjobCsv;
			logger.info("Start import Cronjob Csv.");
			while ((cronjobCsv = beanReader.read(CronjobCsv.class, header, processors)) != null) {
				logger.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(),
						cronjobCsv);
				cronjobCsvList.add(cronjobCsv);
			}
			logger.info("Finished import Cronjob Csv.");
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
		return cronjobCsvList;
	}

	public CellProcessor[] getProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { new UniqueHashCode(), // id
				new NotNull(), // jobClass
				new NotNull(), // jobGroup
				new NotNull(), // jobName
				new Optional(), // description
				new NotNull(), // cronExpression
				new ParseBool(), // startup
				new NotNull(), // jobTrigger
				new Optional(new Token(" ", null, new ParseDate("dd/MM/yyyy h:mm a", true))), // triggerStartDate
				new Optional() // cronjobData
		};

		return processors;
	}

}
