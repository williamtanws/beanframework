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

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.beanframework.common.Updater;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.WebPlatformConstants;
import com.beanframework.console.domain.UserGroupCsv;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.domain.DynamicFieldType;
import com.beanframework.language.domain.Language;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserGroupField;

public class UserGroupUpdate extends Updater {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

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

		// Dynamic Field

		Map<String, Object> enNameDynamicFieldProperties = new HashMap<String, Object>();
		enNameDynamicFieldProperties.put(DynamicField.ID, "usergroup_name_en");
		DynamicField enNameDynamicField = modelService.findOneEntityByProperties(enNameDynamicFieldProperties,
				DynamicField.class);

		if (enNameDynamicField == null) {
			enNameDynamicField = modelService.create(DynamicField.class);
			enNameDynamicField.setId("usergroup_name_en");
		}
		enNameDynamicField.setRequired(true);
		enNameDynamicField.setRule(null);
		enNameDynamicField.setSort(0);
		enNameDynamicField.setType(DynamicFieldType.TEXT);
		modelService.saveEntity(enNameDynamicField);

		Map<String, Object> cnNameDynamicFieldProperties = new HashMap<String, Object>();
		cnNameDynamicFieldProperties.put(DynamicField.ID, "usergroup_name_cn");
		DynamicField cnNameDynamicField = modelService.findOneEntityByProperties(cnNameDynamicFieldProperties,
				DynamicField.class);

		if (cnNameDynamicField == null) {
			cnNameDynamicField = modelService.create(DynamicField.class);
			cnNameDynamicField.setId("usergroup_name_cn");
		}
		cnNameDynamicField.setRequired(true);
		cnNameDynamicField.setRule(null);
		cnNameDynamicField.setSort(1);
		cnNameDynamicField.setType(DynamicFieldType.TEXT);
		modelService.saveEntity(cnNameDynamicField);

		// Language

		Map<String, Object> enlanguageProperties = new HashMap<String, Object>();
		enlanguageProperties.put(Language.ID, "en");
		Language enLanguage = modelService.findOneEntityByProperties(enlanguageProperties, Language.class);

		Map<String, Object> cnlanguageProperties = new HashMap<String, Object>();
		cnlanguageProperties.put(Language.ID, "cn");
		Language cnLanguage = modelService.findOneEntityByProperties(cnlanguageProperties, Language.class);

		for (UserGroupCsv csv : userGroupCsvList) {

			// UserGroup

			Map<String, Object> userGroupProperties = new HashMap<String, Object>();
			userGroupProperties.put(UserGroup.ID, csv.getId());

			UserGroup userGroup = modelService.findOneEntityByProperties(userGroupProperties, UserGroup.class);

			if (userGroup == null) {
				userGroup = modelService.create(UserGroup.class);
				userGroup.setId(csv.getId());
			} else {
				Hibernate.initialize(userGroup.getUserGroupFields());
			}

			if (StringUtils.isNotEmpty(csv.getParent())) {
				Map<String, Object> parentProperties = new HashMap<String, Object>();
				parentProperties.put(UserGroup.ID, csv.getId());
				UserGroup parent = modelService.findOneEntityByProperties(parentProperties, UserGroup.class);

				if (parent == null) {
					logger.error("Parent not exists: " + csv.getParent());
				} else {
					userGroup.setParent(parent);
				}
			}

			boolean enCreate = true;
			boolean cnCreate = true;

			if (enLanguage != null) {
				for (int i = 0; i < userGroup.getUserGroupFields().size(); i++) {
					if (userGroup.getUserGroupFields().get(i).getId().equals(csv.getId() + "_name_en")
							&& userGroup.getUserGroupFields().get(i).getLanguage().getId().equals("en")) {
						userGroup.getUserGroupFields().get(i).setLabel("Name");
						userGroup.getUserGroupFields().get(i).setValue(csv.getName_en());
						enCreate = false;
					}
				}
			}
			if (cnLanguage != null) {
				for (int i = 0; i < userGroup.getUserGroupFields().size(); i++) {
					if (userGroup.getUserGroupFields().get(i).getId().equals(csv.getId() + "_name_cn")
							&& userGroup.getUserGroupFields().get(i).getLanguage().getId().equals("cn")) {
						userGroup.getUserGroupFields().get(i).setLabel("名称");
						userGroup.getUserGroupFields().get(i).setValue(csv.getName_cn());
						cnCreate = false;
					}
				}
			}

			modelService.saveEntity(userGroup);

			// UserGroup Field

			if (enCreate) {
				UserGroupField userGroupField = modelService.create(UserGroupField.class);
				userGroupField.setId(csv.getId() + "_name_en");
				userGroupField.setDynamicField(enNameDynamicField);
				userGroupField.setLanguage(enLanguage);
				userGroupField.setLabel("Name");
				userGroupField.setValue(csv.getName_en());
				userGroupField.setUserGroup(userGroup);
				userGroup.getUserGroupFields().add(userGroupField);

				modelService.saveEntity(userGroupField);
			}
			if (cnCreate) {
				UserGroupField userGroupField = modelService.create(UserGroupField.class);
				userGroupField.setId(csv.getId() + "_name_cn");
				userGroupField.setDynamicField(cnNameDynamicField);
				userGroupField.setLanguage(cnLanguage);
				userGroupField.setLabel("名称");
				userGroupField.setValue(csv.getName_cn());
				userGroupField.setUserGroup(userGroup);
				userGroup.getUserGroupFields().add(userGroupField);

				modelService.saveEntity(userGroupField);
			}
		}

		modelService.saveAll();
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
			logger.info("Start import UserGroup Csv.");
			while ((permissionCsv = beanReader.read(UserGroupCsv.class, header, processors)) != null) {
				logger.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(),
						permissionCsv);
				permissionCsvList.add(permissionCsv);
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
		return permissionCsvList;
	}

	public CellProcessor[] getProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { new UniqueHashCode(), // id
				new Optional(), // parent
				new NotNull(), // name_en
				new NotNull() // name_cn
		};

		return processors;
	}

}
