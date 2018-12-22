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
import com.beanframework.console.domain.UserRightCsv;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.domain.DynamicFieldTypeEnum;
import com.beanframework.language.domain.Language;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.domain.UserRightField;

public class UserRightUpdate extends Updater {
	private static Logger LOGGER = LoggerFactory.getLogger(UserAuthorityUpdate.class);

	@Autowired
	private ModelService modelService;

	@Value("${module.console.import.update.userright}")
	private String USERRIGHT_IMPORT_UPDATE;

	@PostConstruct
	public void updater() {
		setKey(WebPlatformConstants.Update.UserRight.KEY);
		setName(WebPlatformConstants.Update.UserRight.NAME);
		setSort(WebPlatformConstants.Update.UserRight.SORT);
		setDescription(WebPlatformConstants.Update.UserRight.DESCRIPTION);
	}

	@Override
	public void update() {
		PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
		Resource[] resources = null;
		try {
			resources = loader.getResources(USERRIGHT_IMPORT_UPDATE);
			for (Resource resource : resources) {
				try {
					InputStream in = resource.getInputStream();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					IOUtils.copy(in, baos);
					BufferedReader reader = new BufferedReader(new StringReader(new String(baos.toByteArray())));

					List<UserRightCsv> userRightCsvList = readCSVFile(reader);
					save(userRightCsvList);

				} catch (Exception ex) {
					LOGGER.error("Error reading the resource file: " + ex);
				}
			}
		} catch (IOException ex) {
			LOGGER.error("Error reading the resource file: " + ex);
		}
	}

	public void save(List<UserRightCsv> userRightCsvList) throws Exception {

		try {
			// Dynamic Field

			Map<String, Object> enNameDynamicFieldProperties = new HashMap<String, Object>();
			enNameDynamicFieldProperties.put(DynamicField.ID, "userright_name_en");
			DynamicField enNameDynamicField = modelService.findOneEntityByProperties(enNameDynamicFieldProperties,
					DynamicField.class);

			if (enNameDynamicField == null) {
				enNameDynamicField = modelService.create(DynamicField.class);
				enNameDynamicField.setId("userright_name_en");
			}
			enNameDynamicField.setName("Name");
			enNameDynamicField.setRequired(true);
			enNameDynamicField.setRule(null);
			enNameDynamicField.setSort(0);
			enNameDynamicField.setType(DynamicFieldTypeEnum.TEXT);
			modelService.saveEntity(enNameDynamicField, DynamicField.class);

			Map<String, Object> cnNameDynamicFieldProperties = new HashMap<String, Object>();
			cnNameDynamicFieldProperties.put(DynamicField.ID, "userright_name_cn");
			DynamicField cnNameDynamicField = modelService.findOneEntityByProperties(cnNameDynamicFieldProperties,
					DynamicField.class);

			if (cnNameDynamicField == null) {
				cnNameDynamicField = modelService.create(DynamicField.class);
				cnNameDynamicField.setId("userright_name_cn");
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

			for (UserRightCsv csv : userRightCsvList) {

				// UserRight

				Map<String, Object> userRightProperties = new HashMap<String, Object>();
				userRightProperties.put(UserRight.ID, csv.getId());

				UserRight userRight = modelService.findOneEntityByProperties(userRightProperties, UserRight.class);

				if (userRight == null) {
					userRight = modelService.create(UserRight.class);
					userRight.setId(csv.getId());
				}
				else {
					Hibernate.initialize(userRight.getUserRightFields());
				}
				userRight.setSort(csv.getSort());
				
				boolean enCreate = true;
				boolean cnCreate = true;

				if (enLanguage != null) {
					for (int i = 0; i < userRight.getUserRightFields().size(); i++) {
						if (userRight.getUserRightFields().get(i).getId().equals(csv.getId() + "_name_en")
								&& userRight.getUserRightFields().get(i).getLanguage().getId().equals("en")) {
							userRight.getUserRightFields().get(i).setLabel("Name");
							userRight.getUserRightFields().get(i).setValue(csv.getName_en());
							enCreate = false;
						}
					}
				}
				if (cnLanguage != null) {
					for (int i = 0; i < userRight.getUserRightFields().size(); i++) {
						if (userRight.getUserRightFields().get(i).getId().equals(csv.getId() + "_name_cn")
								&& userRight.getUserRightFields().get(i).getLanguage().getId().equals("cn")) {
							userRight.getUserRightFields().get(i).setLabel("名称");
							userRight.getUserRightFields().get(i).setValue(csv.getName_cn());
							cnCreate = false;
						}
					}
				}

				modelService.saveEntity(userRight, UserRight.class);
				
				// UserRight Field
				
				if (enCreate) {
					UserRightField userRightField = modelService.create(UserRightField.class);
					userRightField.setId(csv.getId() + "_name_en");
					userRightField.setDynamicField(enNameDynamicField);
					userRightField.setLanguage(enLanguage);
					userRightField.setLabel("Name");
					userRightField.setValue(csv.getName_en());
					userRightField.setUserRight(userRight);
					userRight.getUserRightFields().add(userRightField);
					
					modelService.saveEntity(userRightField, UserRightField.class);
				}
				if (cnCreate) {
					UserRightField userRightField = modelService.create(UserRightField.class);
					userRightField.setId(csv.getId() + "_name_cn");
					userRightField.setDynamicField(cnNameDynamicField);
					userRightField.setLanguage(cnLanguage);
					userRightField.setLabel("名称");
					userRightField.setValue(csv.getName_cn());
					userRightField.setUserRight(userRight);
					userRight.getUserRightFields().add(userRightField);
					
					modelService.saveEntity(userRightField, UserRightField.class);
				}
			}
			
			modelService.saveAll();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
		}
	}

	public List<UserRightCsv> readCSVFile(Reader reader) {
		ICsvBeanReader beanReader = null;

		List<UserRightCsv> permissionCsvList = new ArrayList<UserRightCsv>();

		try {
			beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);

			// the header elements are used to map the values to the bean (names
			// must match)
			final String[] header = beanReader.getHeader(true);
			final CellProcessor[] processors = getProcessors();

			UserRightCsv permissionCsv;
			LOGGER.info("Start import UserRight Csv.");
			while ((permissionCsv = beanReader.read(UserRightCsv.class, header, processors)) != null) {
				LOGGER.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(),
						permissionCsv);
				permissionCsvList.add(permissionCsv);
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
