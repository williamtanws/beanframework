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
import com.beanframework.console.converter.EntityCsvUserPermissionConverter;
import com.beanframework.console.csv.UserPermissionCsv;
import com.beanframework.console.registry.ImportListener;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.service.UserPermissionService;

public class UserPermissionImportListener extends ImportListener {
	protected static Logger LOGGER = LoggerFactory.getLogger(UserPermissionImportListener.class);

	@Autowired
	private UserPermissionService userPermissionService;

	@Autowired
	private EntityCsvUserPermissionConverter converter;

	@Value("${module.console.import.update.userpermission}")
	private String IMPORT_UPDATE;

	@Value("${module.console.import.remove.userpermission}")
	private String IMPORT_REMOVE;

	@PostConstruct
	public void importer() {
		setKey(ConsoleImportListenerConstants.UserPermissionImport.KEY);
		setName(ConsoleImportListenerConstants.UserPermissionImport.NAME);
		setSort(ConsoleImportListenerConstants.UserPermissionImport.SORT);
		setDescription(ConsoleImportListenerConstants.UserPermissionImport.DESCRIPTION);
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

			List<UserPermissionCsv> userPermissionCsvList = readCSVFile(reader, UserPermissionCsv.getUpdateProcessors());
			save(userPermissionCsvList);
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

			List<UserPermissionCsv> csvList = readCSVFile(reader, UserPermissionCsv.getRemoveProcessors());
			remove(csvList);
		}
	}

	@Override
	public void updateByContent(String content) throws Exception {
		List<UserPermissionCsv> csvList = readCSVFile(new StringReader(content), UserPermissionCsv.getUpdateProcessors());
		save(csvList);
	}

	@Override
	public void removeByContent(String content) throws Exception {
		List<UserPermissionCsv> csvList = readCSVFile(new StringReader(content), UserPermissionCsv.getUpdateProcessors());
		remove(csvList);
	}

	public List<UserPermissionCsv> readCSVFile(Reader reader, CellProcessor[] processors) {
		ICsvBeanReader beanReader = null;

		List<UserPermissionCsv> csvList = new ArrayList<UserPermissionCsv>();

		try {
			beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);

			// the header elements are used to map the values to the bean (names
			// must match)
			final String[] header = beanReader.getHeader(true);

			UserPermissionCsv csv;
			LOGGER.info("Start import " + ConsoleImportListenerConstants.UserPermissionImport.NAME);
			while ((csv = beanReader.read(UserPermissionCsv.class, header, processors)) != null) {
				LOGGER.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(), csv);
				csvList.add(csv);
			}
			LOGGER.info("Finished import " + ConsoleImportListenerConstants.UserPermissionImport.NAME);
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

	public void save(List<UserPermissionCsv> csvList) throws Exception {

		for (UserPermissionCsv csv : csvList) {
			UserPermission model = converter.convert(csv);
			userPermissionService.saveEntity(model);
		}
	}

	public void remove(List<UserPermissionCsv> csvList) throws Exception {

	}

}
