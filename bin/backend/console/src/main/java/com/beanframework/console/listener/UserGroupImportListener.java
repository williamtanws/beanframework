package com.beanframework.console.listener;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

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

import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.console.converter.ImportEntityUserGroupConverter;
import com.beanframework.console.csv.UserGroupCsv;
import com.beanframework.console.registry.ImportListener;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.service.UserGroupService;

public class UserGroupImportListener extends ImportListener {
	protected static Logger LOGGER = LoggerFactory.getLogger(UserGroupImportListener.class);

	@Autowired
	private UserGroupService userGroupService;

	@Autowired
	private ImportEntityUserGroupConverter converter;

	@Value("${module.console.import.update.usergroup}")
	private String IMPORT_UPDATE;

	@Value("${module.console.import.remove.usergroup}")
	private String IMPORT_REMOVE;

	@PostConstruct
	public void importer() {
		setKey(ConsoleImportListenerConstants.UserGroupImportListener.KEY);
		setName(ConsoleImportListenerConstants.UserGroupImportListener.NAME);
		setSort(ConsoleImportListenerConstants.UserGroupImportListener.SORT);
		setDescription(ConsoleImportListenerConstants.UserGroupImportListener.DESCRIPTION);
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

			List<UserGroupCsv> csvList = readCSVFile(reader, UserGroupCsv.getUpdateProcessors());
			save(csvList);
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

			List<UserGroupCsv> csvList = readCSVFile(reader, UserGroupCsv.getRemoveProcessors());
			remove(csvList);
		}
	}

	public List<UserGroupCsv> readCSVFile(Reader reader, CellProcessor[] processors) {
		ICsvBeanReader beanReader = null;

		List<UserGroupCsv> csvList = new ArrayList<UserGroupCsv>();

		try {
			beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);

			// the header elements are used to map the values to the bean (names
			// must match)
			final String[] header = beanReader.getHeader(true);

			UserGroupCsv csv;
			LOGGER.info("Start import " + ConsoleImportListenerConstants.UserGroupImportListener.NAME);
			while ((csv = beanReader.read(UserGroupCsv.class, header, processors)) != null) {
				LOGGER.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(), csv);
				csvList.add(csv);
			}
			LOGGER.info("Finished import " + ConsoleImportListenerConstants.UserGroupImportListener.NAME);
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

	public void save(List<UserGroupCsv> userGroupCsvList) throws Exception {

		for (UserGroupCsv csv : userGroupCsvList) {

			UserGroup model = converter.convert(csv);
			userGroupService.saveEntity(model);
		}
	}

	public void remove(List<UserGroupCsv> csvList) throws Exception {

	}
}
