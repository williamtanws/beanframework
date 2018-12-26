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
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.beanframework.common.Updater;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.WebPlatformConstants;
import com.beanframework.console.data.UserGroupCsv;
import com.beanframework.user.domain.UserGroup;

public class UserGroupUpdate extends Updater {
	private static Logger LOGGER = LoggerFactory.getLogger(UserGroupUpdate.class);

	@Autowired
	private ModelService modelService;

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
	public void update() throws Exception {
		PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
		Resource[] resources = loader.getResources(USERGROUP_IMPORT_UPDATE);
		for (Resource resource : resources) {
			InputStream in = resource.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtils.copy(in, baos);
			BufferedReader reader = new BufferedReader(new StringReader(new String(baos.toByteArray())));

			List<UserGroupCsv> userGroupCsvList = readCSVFile(reader);
			save(userGroupCsvList);
		}
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
				Hibernate.initialize(userGroup.getUserGroupFields());
			}

			modelService.saveEntity(userGroup, UserGroup.class);

			// UserGroup Field

			if (csv.getDynamicField() != null) {
				String[] dynamicFields = csv.getDynamicField().split(";");
				for (String dynamicField : dynamicFields) {
					String dynamicFieldId = dynamicField.split("=")[0];
					String value = dynamicField.split("=")[1];
					for (int i = 0; i < userGroup.getUserGroupFields().size(); i++) {
						if (userGroup.getUserGroupFields().get(i).getId().equals(userGroup.getId()+"_"+dynamicFieldId)) {
							userGroup.getUserGroupFields().get(i).setValue(value);
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
					UserGroup userGroupChild = modelService.findOneEntityByProperties(userGroupChildProperties,
							UserGroup.class);

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

	public List<UserGroupCsv> readCSVFile(Reader reader) {
		ICsvBeanReader beanReader = null;

		List<UserGroupCsv> permissionCsvList = new ArrayList<UserGroupCsv>();

		try {
			beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);

			// the header elements are used to map the values to the bean (names
			// must match)
			final String[] header = beanReader.getHeader(true);
			final CellProcessor[] processors = getProcessors();

			UserGroupCsv permissionCsv;
			LOGGER.info("Start import UserGroup Csv.");
			while ((permissionCsv = beanReader.read(UserGroupCsv.class, header, processors)) != null) {
				LOGGER.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(),
						permissionCsv);
				permissionCsvList.add(permissionCsv);
			}
			LOGGER.info("Finished import UserGroup Csv.");
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
		return permissionCsvList;
	}

	public CellProcessor[] getProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { new UniqueHashCode(), // id
				new Optional(), // parent
				new Optional() // dynamicField
		};

		return processors;
	}

}
