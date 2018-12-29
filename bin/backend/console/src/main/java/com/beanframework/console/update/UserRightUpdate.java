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
import com.beanframework.console.data.UserRightCsv;
import com.beanframework.user.domain.UserRight;

public class UserRightUpdate extends Updater {
	private static Logger LOGGER = LoggerFactory.getLogger(UserRightUpdate.class);

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
	public void update() throws Exception {
		PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
		Resource[] resources = loader.getResources(USERRIGHT_IMPORT_UPDATE);
		for (Resource resource : resources) {
			InputStream in = resource.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtils.copy(in, baos);
			BufferedReader reader = new BufferedReader(new StringReader(new String(baos.toByteArray())));

			List<UserRightCsv> userRightCsvList = readCSVFile(reader);
			save(userRightCsvList);
		}
	}

	public void save(List<UserRightCsv> userRightCsvList) throws Exception {

		for (UserRightCsv csv : userRightCsvList) {

			// UserRight

			Map<String, Object> userRightProperties = new HashMap<String, Object>();
			userRightProperties.put(UserRight.ID, csv.getId());

			UserRight userRight = modelService.findOneEntityByProperties(userRightProperties, UserRight.class);

			if (userRight == null) {
				userRight = modelService.create(UserRight.class);
				userRight.setId(csv.getId());
			} else {
				Hibernate.initialize(userRight.getFields());
			}
			userRight.setSort(csv.getSort());

			modelService.saveEntity(userRight, UserRight.class);

			// UserRight Field

			if (csv.getDynamicField() != null) {
				String[] dynamicFields = csv.getDynamicField().split(";");
				for (String dynamicField : dynamicFields) {
					String dynamicFieldId = dynamicField.split("=")[0];
					String value = dynamicField.split("=")[1];
					for (int i = 0; i < userRight.getFields().size(); i++) {
						if (userRight.getFields().get(i).getId().equals(userRight.getId()+"_"+dynamicFieldId)) {
							userRight.getFields().get(i).setValue(value);
						}
					}
				}
			}

			modelService.saveEntity(userRight, UserRight.class);
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
				LOGGER.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(), permissionCsv);
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
				new Optional() // dynamicField
		};

		return processors;
	}

}
