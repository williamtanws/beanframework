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
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.beanframework.common.Updater;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.WebPlatformConstants;
import com.beanframework.console.domain.UserAuthorityCsv;
import com.beanframework.user.domain.UserGroup;

public class UserAuthorityUpdate extends Updater {
	private static Logger LOGGER = LoggerFactory.getLogger(UserAuthorityUpdate.class);

	@Autowired
	private ModelService modelService;

	@Value("${module.console.import.update.userauthority}")
	private String USERAUTHORITY_IMPORT_UPDATE;

	@PostConstruct
	public void updater() {
		setKey(WebPlatformConstants.Update.UserAuthority.KEY);
		setName(WebPlatformConstants.Update.UserAuthority.NAME);
		setSort(WebPlatformConstants.Update.UserAuthority.SORT);
		setDescription(WebPlatformConstants.Update.UserAuthority.DESCRIPTION);
	}

	@Override
	public void update() throws Exception {
		PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
		Resource[] resources = loader.getResources(USERAUTHORITY_IMPORT_UPDATE);
		for (Resource resource : resources) {
			InputStream in = resource.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtils.copy(in, baos);
			BufferedReader reader = new BufferedReader(new StringReader(new String(baos.toByteArray())));

			List<UserAuthorityCsv> userAuthorityCsvList = readCSVFile(reader);
			save(userAuthorityCsvList);
		}
	}

	public void save(List<UserAuthorityCsv> userAuthorityCsvList) throws Exception {

		// Group permissions by userGroupId
		Map<String, List<UserAuthorityCsv>> userGroupMap = new HashMap<String, List<UserAuthorityCsv>>();
		for (UserAuthorityCsv userAuthorityCsv : userAuthorityCsvList) {
			String userGroupId = userAuthorityCsv.getUserGroupId();

			if (userGroupMap.get(userGroupId) == null) {
				userGroupMap.put(userGroupId, new ArrayList<UserAuthorityCsv>());
			}

			List<UserAuthorityCsv> userGroupUserAuthorityList = userGroupMap.get(userGroupId);
			userGroupUserAuthorityList.add(userAuthorityCsv);

			userGroupMap.put(userAuthorityCsv.getUserGroupId(), userGroupUserAuthorityList);
		}

		for (Map.Entry<String, List<UserAuthorityCsv>> entry : userGroupMap.entrySet()) {
			String userGroupId = entry.getKey();
			List<UserAuthorityCsv> userGroupAuthorityList = entry.getValue();

			Map<String, Object> userGroupProperties = new HashMap<String, Object>();
			userGroupProperties.put(UserGroup.ID, userGroupId);
			UserGroup userGroup = modelService.findOneEntityByProperties(userGroupProperties, UserGroup.class);

			if (userGroup == null) {
				LOGGER.error("userGroupId not exists: " + userGroupId);
			} else {

				Hibernate.initialize(userGroup.getUserAuthorities());

				for (int i = 0; i < userGroup.getUserAuthorities().size(); i++) {
					Hibernate.initialize(userGroup.getUserAuthorities().get(i).getUserRight());
					Hibernate.initialize(userGroup.getUserAuthorities().get(i).getUserPermission());

					for (UserAuthorityCsv userAuthorityCsv : userGroupAuthorityList) {
						if (userGroup.getUserAuthorities().get(i).getUserPermission().getId().equals(userAuthorityCsv.getUserPermissionId())) {
							if (userGroup.getUserAuthorities().get(i).getUserRight().getId().equals("create")) {
								userGroup.getUserAuthorities().get(i).setEnabled(POSITIVE.equals(userAuthorityCsv.getCreate()) ? Boolean.TRUE : Boolean.FALSE);
							}
							if (userGroup.getUserAuthorities().get(i).getUserRight().getId().equals("read")) {
								userGroup.getUserAuthorities().get(i).setEnabled(POSITIVE.equals(userAuthorityCsv.getRead()) ? Boolean.TRUE : Boolean.FALSE);
							}
							if (userGroup.getUserAuthorities().get(i).getUserRight().getId().equals("update")) {
								userGroup.getUserAuthorities().get(i).setEnabled(POSITIVE.equals(userAuthorityCsv.getUpdate()) ? Boolean.TRUE : Boolean.FALSE);
							}
							if (userGroup.getUserAuthorities().get(i).getUserRight().getId().equals("delete")) {
								userGroup.getUserAuthorities().get(i).setEnabled(POSITIVE.equals(userAuthorityCsv.getDelete()) ? Boolean.TRUE : Boolean.FALSE);
							}
						}
					}
				}
				
				modelService.saveEntity(userGroup, UserGroup.class);
			}
		}
	}

	public List<UserAuthorityCsv> readCSVFile(Reader reader) {
		ICsvBeanReader beanReader = null;

		List<UserAuthorityCsv> userAuthorityCsvList = new ArrayList<UserAuthorityCsv>();

		try {
			beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);

			// the header elements are used to map the values to the bean (names
			// must match)
			final String[] header = beanReader.getHeader(true);
			final CellProcessor[] processors = getProcessors();

			UserAuthorityCsv userAuthorityCsv;
			LOGGER.info("Start import UserAuthority Csv.");
			while ((userAuthorityCsv = beanReader.read(UserAuthorityCsv.class, header, processors)) != null) {
				LOGGER.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(), userAuthorityCsv);
				userAuthorityCsvList.add(userAuthorityCsv);
			}
			LOGGER.info("Finished import UserAuthority Csv.");
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
		return userAuthorityCsvList;
	}

	public CellProcessor[] getProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { new NotNull(), // userGroupId
				new NotNull(), // userPermissionId
				new Optional(), // create
				new Optional(), // read
				new Optional(), // update
				new Optional() // delete
		};

		return processors;
	}

}
