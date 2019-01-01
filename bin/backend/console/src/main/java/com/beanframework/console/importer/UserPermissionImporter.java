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
import com.beanframework.console.csv.UserPermissionCsv;
import com.beanframework.console.registry.Importer;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserRight;

public class UserPermissionImporter extends Importer {
	private static Logger LOGGER = LoggerFactory.getLogger(UserPermissionImporter.class);

	@Autowired
	private ModelService modelService;

	@Value("${module.console.import.update.userpermission}")
	private String IMPORT_UPDATE;
	
	@Value("${module.console.import.remove.userpermission}")
	private String IMPORT_REMOVE;

	@PostConstruct
	public void importer() {
		setKey(WebPlatformUpdateConstants.Importer.UserPermission.KEY);
		setName(WebPlatformUpdateConstants.Importer.UserPermission.NAME);
		setSort(WebPlatformUpdateConstants.Importer.UserPermission.SORT);
		setDescription(WebPlatformUpdateConstants.Importer.UserPermission.DESCRIPTION);
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

			List<UserPermissionCsv> userPermissionCsvList = readCSVFile(reader, UserPermissionCsv.getUpdateProcessors());
			save(userPermissionCsvList);
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

			List<UserPermissionCsv> csvList = readCSVFile(reader, UserPermissionCsv.getRemoveProcessors());
			remove(csvList);
		}
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
			LOGGER.info("Start import "+WebPlatformUpdateConstants.Importer.UserPermission.NAME);
			while ((csv = beanReader.read(UserPermissionCsv.class, header, processors)) != null) {
				LOGGER.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(), csv);
				csvList.add(csv);
			}
			LOGGER.info("Finished import "+WebPlatformUpdateConstants.Importer.UserPermission.NAME);
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

			// UserPermission

			Map<String, Object> userPermissionProperties = new HashMap<String, Object>();
			userPermissionProperties.put(UserPermission.ID, csv.getId());

			UserPermission userPermission = modelService.findOneEntityByProperties(userPermissionProperties, UserPermission.class);

			if (userPermission == null) {
				userPermission = modelService.create(UserPermission.class);
				userPermission.setId(csv.getId());
			} else {
				Hibernate.initialize(userPermission.getFields());
			}
			userPermission.setSort(csv.getSort());

			modelService.saveEntity(userPermission, UserPermission.class);

			// UserPermission Field

			if (csv.getDynamicField() != null) {
				String[] dynamicFields = csv.getDynamicField().split(";");
				for (String dynamicField : dynamicFields) {
					String dynamicFieldId = dynamicField.split("=")[0];
					String value = dynamicField.split("=")[1];
					for (int i = 0; i < userPermission.getFields().size(); i++) {
						if (userPermission.getFields().get(i).getId().equals(userPermission.getId()+"_"+dynamicFieldId)) {
							userPermission.getFields().get(i).setValue(value);
						}
					}
				}
			}

			modelService.saveEntity(userPermission, UserRight.class);
		}
	}

	public void remove(List<UserPermissionCsv> csvList) throws Exception {
		for (UserPermissionCsv csv : csvList) {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(UserPermission.ID, csv.getId());
			UserPermission model = modelService.findOneEntityByProperties(properties, UserPermission.class);
			modelService.deleteByEntity(model, UserPermission.class);
		}
	}
}
