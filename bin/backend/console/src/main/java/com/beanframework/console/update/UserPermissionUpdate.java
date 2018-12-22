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
import com.beanframework.console.domain.UserPermissionCsv;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.domain.DynamicFieldTypeEnum;
import com.beanframework.language.domain.Language;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserPermissionField;

public class UserPermissionUpdate extends Updater {
	private static Logger LOGGER = LoggerFactory.getLogger(UserPermissionUpdate.class);

	@Autowired
	private ModelService modelService;

	@Value("${module.console.import.update.userpermission}")
	private String USERPERMISSION_IMPORT_UPDATE;

	@PostConstruct
	public void updater() {
		setKey(WebPlatformConstants.Update.UserPermission.KEY);
		setName(WebPlatformConstants.Update.UserPermission.NAME);
		setSort(WebPlatformConstants.Update.UserPermission.SORT);
		setDescription(WebPlatformConstants.Update.UserPermission.DESCRIPTION);
	}

	@Override
	public void update() {
		PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
		Resource[] resources = null;
		try {
			resources = loader.getResources(USERPERMISSION_IMPORT_UPDATE);
			for (Resource resource : resources) {
				try {
					InputStream in = resource.getInputStream();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					IOUtils.copy(in, baos);
					BufferedReader reader = new BufferedReader(new StringReader(new String(baos.toByteArray())));

					List<UserPermissionCsv> userPermissionCsvList = readCSVFile(reader);
					save(userPermissionCsvList);

				} catch (Exception ex) {
					LOGGER.error("Error reading the resource file: " + ex);
				}
			}
		} catch (IOException ex) {
			LOGGER.error("Error reading the resource file: " + ex);
		}
	}

	public void save(List<UserPermissionCsv> userPermissionCsvList) throws Exception {

		try {
			// Dynamic Field

			Map<String, Object> enNameDynamicFieldProperties = new HashMap<String, Object>();
			enNameDynamicFieldProperties.put(DynamicField.ID, "userpermission_name_en");
			DynamicField enNameDynamicField = modelService.findOneEntityByProperties(enNameDynamicFieldProperties,
					DynamicField.class);

			if (enNameDynamicField == null) {
				enNameDynamicField = modelService.create(DynamicField.class);
				enNameDynamicField.setId("userpermission_name_en");
			}
			enNameDynamicField.setName("Name");
			enNameDynamicField.setRequired(true);
			enNameDynamicField.setRule(null);
			enNameDynamicField.setSort(0);
			enNameDynamicField.setType(DynamicFieldTypeEnum.TEXT);
			modelService.saveEntity(enNameDynamicField, DynamicField.class);

			Map<String, Object> cnNameDynamicFieldProperties = new HashMap<String, Object>();
			cnNameDynamicFieldProperties.put(DynamicField.ID, "userpermission_name_cn");
			DynamicField cnNameDynamicField = modelService.findOneEntityByProperties(cnNameDynamicFieldProperties,
					DynamicField.class);

			if (cnNameDynamicField == null) {
				cnNameDynamicField = modelService.create(DynamicField.class);
				cnNameDynamicField.setId("userpermission_name_cn");
			}
			cnNameDynamicField.setName("Name");
			cnNameDynamicField.setRequired(true);
			cnNameDynamicField.setRule(null);
			cnNameDynamicField.setSort(1);
			cnNameDynamicField.setType(DynamicFieldTypeEnum.TEXT);
			modelService.saveEntity(cnNameDynamicField, DynamicField.class);

			// Language

			Map<String, Object> enlanguageProperties = new HashMap<String, Object>();
			enlanguageProperties.put(Language.ID, "en");
			Language enLanguage = modelService.findOneEntityByProperties(enlanguageProperties, Language.class);

			Map<String, Object> cnlanguageProperties = new HashMap<String, Object>();
			cnlanguageProperties.put(Language.ID, "cn");
			Language cnLanguage = modelService.findOneEntityByProperties(cnlanguageProperties, Language.class);

			for (UserPermissionCsv csv : userPermissionCsvList) {

				// UserPermission

				Map<String, Object> userPermissionProperties = new HashMap<String, Object>();
				userPermissionProperties.put(UserPermission.ID, csv.getId());

				UserPermission userPermission = modelService.findOneEntityByProperties(userPermissionProperties, UserPermission.class);

				if (userPermission == null) {
					userPermission = modelService.create(UserPermission.class);
					userPermission.setId(csv.getId());
				}
				else {
					Hibernate.initialize(userPermission.getUserPermissionFields());
				}
				userPermission.setSort(csv.getSort());
				
				boolean enCreate = true;
				boolean cnCreate = true;

				if (enLanguage != null) {
					for (int i = 0; i < userPermission.getUserPermissionFields().size(); i++) {
						if (userPermission.getUserPermissionFields().get(i).getId().equals(csv.getId() + "_name_en")
								&& userPermission.getUserPermissionFields().get(i).getLanguage().getId().equals("en")) {
							userPermission.getUserPermissionFields().get(i).setLabel("Name");
							userPermission.getUserPermissionFields().get(i).setValue(csv.getName_en());
							enCreate = false;
						}
					}
				}
				if (cnLanguage != null) {
					for (int i = 0; i < userPermission.getUserPermissionFields().size(); i++) {
						if (userPermission.getUserPermissionFields().get(i).getId().equals(csv.getId() + "_name_cn")
								&& userPermission.getUserPermissionFields().get(i).getLanguage().getId().equals("cn")) {
							userPermission.getUserPermissionFields().get(i).setLabel("名称");
							userPermission.getUserPermissionFields().get(i).setValue(csv.getName_cn());
							cnCreate = false;
						}
					}
				}

				modelService.saveEntity(userPermission, UserPermission.class);
				
				// UserPermission Field
				
				if (enCreate) {
					UserPermissionField userPermissionField = modelService.create(UserPermissionField.class);
					userPermissionField.setId(csv.getId() + "_name_en");
					userPermissionField.setDynamicField(enNameDynamicField);
					userPermissionField.setLanguage(enLanguage);
					userPermissionField.setLabel("Name");
					userPermissionField.setValue(csv.getName_en());
					userPermissionField.setUserPermission(userPermission);
					userPermission.getUserPermissionFields().add(userPermissionField);
					
					modelService.saveEntity(userPermissionField, UserPermissionField.class);
				}
				if (cnCreate) {
					UserPermissionField userPermissionField = modelService.create(UserPermissionField.class);
					userPermissionField.setId(csv.getId() + "_name_cn");
					userPermissionField.setDynamicField(cnNameDynamicField);
					userPermissionField.setLanguage(cnLanguage);
					userPermissionField.setLabel("名称");
					userPermissionField.setValue(csv.getName_cn());
					userPermissionField.setUserPermission(userPermission);
					userPermission.getUserPermissionFields().add(userPermissionField);
					
					modelService.saveEntity(userPermissionField, UserPermissionField.class);
				}
			}
			
			modelService.saveAll();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
		}
	}

	public List<UserPermissionCsv> readCSVFile(Reader reader) {
		ICsvBeanReader beanReader = null;

		List<UserPermissionCsv> permissionCsvList = new ArrayList<UserPermissionCsv>();

		try {
			beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);

			// the header elements are used to map the values to the bean (names
			// must match)
			final String[] header = beanReader.getHeader(true);
			final CellProcessor[] processors = getProcessors();

			UserPermissionCsv permissionCsv;
			LOGGER.info("Start import UserPermission Csv.");
			while ((permissionCsv = beanReader.read(UserPermissionCsv.class, header, processors)) != null) {
				LOGGER.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(),
						permissionCsv);
				permissionCsvList.add(permissionCsv);
			}
			LOGGER.info("Finished import UserPermission Csv.");
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
				new ParseInt(), // sort
				new NotNull(), // name_en
				new NotNull() // name_cn
		};

		return processors;
	}

}
