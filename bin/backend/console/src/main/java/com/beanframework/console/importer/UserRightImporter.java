package com.beanframework.console.importer;

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

import com.beanframework.console.PlatformUpdateWebConstants;
import com.beanframework.console.converter.EntityUserRightConverterImporter;
import com.beanframework.console.csv.UserRightCsv;
import com.beanframework.console.registry.Importer;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.service.UserRightFacade;

public class UserRightImporter extends Importer {
	protected static Logger LOGGER = LoggerFactory.getLogger(UserRightImporter.class);
	
	@Autowired
	private UserRightFacade userRightFacade;

	@Autowired
	private EntityUserRightConverterImporter converter;

	@Value("${module.console.import.update.userright}")
	private String IMPORT_UPDATE;

	@Value("${module.console.import.remove.userright}")
	private String IMPORT_REMOVE;

	@PostConstruct
	public void importer() {
		setKey(PlatformUpdateWebConstants.Importer.UserRightImporter.KEY);
		setName(PlatformUpdateWebConstants.Importer.UserRightImporter.NAME);
		setSort(PlatformUpdateWebConstants.Importer.UserRightImporter.SORT);
		setDescription(PlatformUpdateWebConstants.Importer.UserRightImporter.DESCRIPTION);
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

			List<UserRightCsv> csvList = readCSVFile(reader, UserRightCsv.getUpdateProcessors());
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

			List<UserRightCsv> csvList = readCSVFile(reader, UserRightCsv.getRemoveProcessors());
			remove(csvList);
		}
	}

	public List<UserRightCsv> readCSVFile(Reader reader, CellProcessor[] processors) {
		ICsvBeanReader beanReader = null;

		List<UserRightCsv> csvList = new ArrayList<UserRightCsv>();

		try {
			beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);

			// the header elements are used to map the values to the bean (names
			// must match)
			final String[] header = beanReader.getHeader(true);

			UserRightCsv csv;
			LOGGER.info("Start import UserRight Csv.");
			while ((csv = beanReader.read(UserRightCsv.class, header, processors)) != null) {
				LOGGER.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(), csv);
				csvList.add(csv);
			}
			LOGGER.info("Finished import UserRight Csv.");
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

	public void save(List<UserRightCsv> csvList) throws Exception {

		for (UserRightCsv csv : csvList) {
			UserRight model = converter.convert(csv);
			userRightFacade.saveEntity(model);
		}
	}

	public void remove(List<UserRightCsv> csvList) throws Exception {
		for (UserRightCsv csv : csvList) {
			userRightFacade.deleteById(csv.getId());
		}
	}

}
