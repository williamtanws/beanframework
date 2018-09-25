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
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.beanframework.common.domain.Updater;
import com.beanframework.console.WebPlatformConstants;
import com.beanframework.console.domain.UserGroupCsv;
import com.beanframework.language.domain.Language;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserGroupLang;
import com.beanframework.user.service.UserGroupFacade;

public class UserGroupUpdate extends Updater {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserGroupFacade userGroupFacade;

	@Value("${module.console.import.update.usergroup}")
	private String USERGROUP_IMPORT_UPDATE;

	@PostConstruct
	public void updater() {
		setKey(WebPlatformConstants.Update.UserGroup.KEY);
		setName(WebPlatformConstants.Update.UserGroup.NAME);
		setSort(WebPlatformConstants.Update.UserGroup.SORT);
		setDescription(WebPlatformConstants.Update.UserGroup.DESCRIPTION);
	}

	@Override
	public void update() {
		PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
		Resource[] resources = null;
		try {
			resources = loader.getResources(USERGROUP_IMPORT_UPDATE);
			for (Resource resource : resources) {
				try {
					InputStream in = resource.getInputStream();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					IOUtils.copy(in, baos);
					BufferedReader reader = new BufferedReader(new StringReader(new String(baos.toByteArray())));

					List<UserGroupCsv> userGroupCsvList = readCSVFile(reader);
					save(userGroupCsvList);

				} catch (IOException ex) {
					logger.error("Error reading the resource file: " + ex);
				}
			}
		} catch (IOException ex) {
			logger.error("Error reading the resource file: " + ex);
		}
	}

	public void save(List<UserGroupCsv> userGroupCsvList) {

		for (UserGroupCsv userGroupCsv : userGroupCsvList) {
			UserGroup userGroup = userGroupFacade.create();
			userGroup.setId(userGroupCsv.getId());
			
			Language language = new Language();
			language.setId("en");
			UserGroupLang userGroupLang = new UserGroupLang();
			userGroupLang.setLanguage(language);
			userGroupLang.setName(userGroupCsv.getName_en());
			userGroup.getUserGroupLangs().add(userGroupLang);
			
			language = new Language();
			language.setId("cn");
			userGroupLang = new UserGroupLang();
			userGroupLang.setLanguage(language);
			userGroupLang.setName(userGroupCsv.getName_cn());
			userGroup.getUserGroupLangs().add(userGroupLang);
			
			MapBindingResult bindingResult = new MapBindingResult(new HashMap<String, Object>(),
					UserGroup.class.getName());
			userGroupFacade.save(userGroup, bindingResult);

			if (bindingResult.hasErrors()) {
				for (ObjectError objectError : bindingResult.getAllErrors()) {
					logger.error(objectError.toString());
				}
			}
		}
	}

	public List<UserGroupCsv> readCSVFile(Reader reader) {
		ICsvBeanReader beanReader = null;

		List<UserGroupCsv> userGroupCsvList = new ArrayList<UserGroupCsv>();

		try {
			beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);

			// the header elements are used to map the values to the bean (names
			// must match)
			final String[] header = beanReader.getHeader(true);
			final CellProcessor[] processors = getProcessors();

			UserGroupCsv userGroupCsv;
			logger.info("Start import UserGroup Csv.");
			while ((userGroupCsv = beanReader.read(UserGroupCsv.class, header, processors)) != null) {
				logger.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(),
						userGroupCsv);
				userGroupCsvList.add(userGroupCsv);
			}
			logger.info("Finished import UserGroup Csv.");
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
		return userGroupCsvList;
	}

	public CellProcessor[] getProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { 
				new UniqueHashCode(), // ID
				new NotNull(), // name_en
				new NotNull() // name_cn
		};

		return processors;
	}

}
