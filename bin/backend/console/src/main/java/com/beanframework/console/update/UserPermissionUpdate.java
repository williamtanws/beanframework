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
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.beanframework.common.Updater;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.WebPlatformConstants;
import com.beanframework.console.domain.UserPermissionCsv;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserRight;

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
	public void update() throws Exception {
		PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
		Resource[] resources = loader.getResources(USERPERMISSION_IMPORT_UPDATE);
		for (Resource resource : resources) {
			InputStream in = resource.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtils.copy(in, baos);
			BufferedReader reader = new BufferedReader(new StringReader(new String(baos.toByteArray())));

			List<UserPermissionCsv> userPermissionCsvList = readCSVFile(reader);
			save(userPermissionCsvList);
		}
	}

	public void save(List<UserPermissionCsv> userPermissionCsvList) throws Exception {

		for (UserPermissionCsv csv : userPermissionCsvList) {

			// UserPermission

			Map<String, Object> userPermissionProperties = new HashMap<String, Object>();
			userPermissionProperties.put(UserPermission.ID, csv.getId());

			UserPermission userPermission = modelService.findOneEntityByProperties(userPermissionProperties, UserPermission.class);

			if (userPermission == null) {
				userPermission = modelService.create(UserPermission.class);
				userPermission.setId(csv.getId());
			} else {
				Hibernate.initialize(userPermission.getUserPermissionFields());
			}
			userPermission.setSort(csv.getSort());

			modelService.saveEntity(userPermission, UserPermission.class);

			// UserPermission Field

			if (csv.getDynamicField() != null) {
				String[] dynamicFields = csv.getDynamicField().split(";");
				for (String dynamicField : dynamicFields) {
					String dynamicFieldId = dynamicField.split("=")[0];
					String value = dynamicField.split("=")[1];
					for (int i = 0; i < userPermission.getUserPermissionFields().size(); i++) {
						if (userPermission.getUserPermissionFields().get(i).getId().equals(userPermission.getId()+"_"+dynamicFieldId)) {
							userPermission.getUserPermissionFields().get(i).setValue(value);
						}
					}
				}
			}

			modelService.saveEntity(userPermission, UserRight.class);
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
				LOGGER.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(), permissionCsv);
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
				new Optional() // dynamicField
		};

		return processors;
	}

}
