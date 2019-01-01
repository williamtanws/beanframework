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
import org.hibernate.Hibernate;
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
import com.beanframework.console.WebPlatformUpdateConstants;
import com.beanframework.console.csv.UserGroupCsv;
import com.beanframework.console.registry.Importer;
import com.beanframework.user.domain.UserGroup;

public class UserGroupImporter extends Importer {
	private static Logger LOGGER = LoggerFactory.getLogger(UserGroupImporter.class);

	@Autowired
	private ModelService modelService;

	@Value("${module.console.import.update.usergroup}")
	private String IMPORT_UPDATE;

	@Value("${module.console.import.remove.usergroup}")
	private String IMPORT_REMOVE;

	@PostConstruct
	public void importer() {
		setKey(WebPlatformUpdateConstants.Importer.UserGroup.KEY);
		setName(WebPlatformUpdateConstants.Importer.UserGroup.NAME);
		setSort(WebPlatformUpdateConstants.Importer.UserGroup.SORT);
		setDescription(WebPlatformUpdateConstants.Importer.UserGroup.DESCRIPTION);
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
			LOGGER.info("Start import " + WebPlatformUpdateConstants.Importer.UserGroup.NAME);
			while ((csv = beanReader.read(UserGroupCsv.class, header, processors)) != null) {
				LOGGER.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(), csv);
				csvList.add(csv);
			}
			LOGGER.info("Finished import " + WebPlatformUpdateConstants.Importer.UserGroup.NAME);
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

			// UserGroup

			Map<String, Object> userGroupProperties = new HashMap<String, Object>();
			userGroupProperties.put(UserGroup.ID, csv.getId());

			UserGroup userGroup = modelService.findOneEntityByProperties(userGroupProperties, UserGroup.class);

			if (userGroup == null) {
				userGroup = modelService.create(UserGroup.class);
				userGroup.setId(csv.getId());
			} else {
				Hibernate.initialize(userGroup.getUserGroups());
				Hibernate.initialize(userGroup.getFields());
			}

			modelService.saveEntity(userGroup, UserGroup.class);

			// UserGroup Field

			if (csv.getDynamicField() != null) {
				String[] dynamicFields = csv.getDynamicField().split(";");
				for (String dynamicField : dynamicFields) {
					String dynamicFieldId = dynamicField.split("=")[0];
					String value = dynamicField.split("=")[1];
					for (int i = 0; i < userGroup.getFields().size(); i++) {
						if (userGroup.getFields().get(i).getId().equals(userGroup.getId() + "_" + dynamicFieldId)) {
							userGroup.getFields().get(i).setValue(value);
						}
					}
				}
			}

			modelService.saveEntity(userGroup, UserGroup.class);
		}

		// User Group

		for (UserGroupCsv csv : userGroupCsvList) {

			Map<String, Object> userGroupProperties = new HashMap<String, Object>();
			userGroupProperties.put(UserGroup.ID, csv.getId());

			UserGroup userGroup = modelService.findOneEntityByProperties(userGroupProperties, UserGroup.class);
			Hibernate.initialize(userGroup.getUserGroups());

			if (csv.getUserGroupIds() != null) {
				String[] userGroupIds = csv.getUserGroupIds().split(SPLITTER);
				for (int i = 0; i < userGroupIds.length; i++) {
					Map<String, Object> userGroupChildProperties = new HashMap<String, Object>();
					userGroupChildProperties.put(UserGroup.ID, userGroupIds[i]);
					UserGroup userGroupChild = modelService.findOneEntityByProperties(userGroupChildProperties, UserGroup.class);

					if (userGroupChild == null) {
						LOGGER.error("UserGroup not exists: " + userGroupIds[i]);
					} else {
						userGroup.getUserGroups().add(userGroupChild);

						modelService.saveEntity(userGroupChild, UserGroup.class);
					}
				}
			}
		}
	}

	public void remove(List<UserGroupCsv> csvList) throws Exception {
		for (UserGroupCsv csv : csvList) {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(UserGroup.ID, csv.getId());
			UserGroup model = modelService.findOneEntityByProperties(properties, UserGroup.class);
			modelService.deleteByEntity(model, UserGroup.class);
		}
	}
}
