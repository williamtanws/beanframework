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
import com.beanframework.common.exception.ModelSavingException;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.WebPlatformConstants;
import com.beanframework.console.domain.UserAuthorityCsv;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserRight;

public class UserAuthorityUpdate extends Updater {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ModelService modelService;

	@Value("${module.console.import.update.userauthority}")
	private String USERAUTHORITY_IMPORT_UPDATE;

	@PostConstruct
	public void updater() {
		setKey(WebPlatformConstants.Update.UserAuthorityRight.KEY);
		setName(WebPlatformConstants.Update.UserAuthorityRight.NAME);
		setSort(WebPlatformConstants.Update.UserAuthorityRight.SORT);
		setDescription(WebPlatformConstants.Update.UserAuthorityRight.DESCRIPTION);
	}

	@Override
	public void update() {
		PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
		Resource[] resources = null;
		try {
			resources = loader.getResources(USERAUTHORITY_IMPORT_UPDATE);
			for (Resource resource : resources) {
				try {
					InputStream in = resource.getInputStream();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					IOUtils.copy(in, baos);
					BufferedReader reader = new BufferedReader(new StringReader(new String(baos.toByteArray())));

					List<UserAuthorityCsv> userAuthorityCsvList = readCSVFile(reader);
					save(userAuthorityCsvList);

				} catch (IOException ex) {
					logger.error("Error reading the resource file: " + ex);
				}
			}
		} catch (IOException ex) {
			logger.error("Error reading the resource file: " + ex);
		}
	}

	public void save(List<UserAuthorityCsv> userAuthorityCsvList) {
		
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(UserRight.ID, "create");
		UserRight create = modelService.findOneDtoByProperties(properties, UserRight.class);
		
		properties = new HashMap<String, Object>();
		properties.put(UserRight.ID, "read");
		UserRight read = modelService.findOneDtoByProperties(properties, UserRight.class);
		
		properties = new HashMap<String, Object>();
		properties.put(UserRight.ID, "update");
		UserRight update = modelService.findOneDtoByProperties(properties, UserRight.class);
		
		properties = new HashMap<String, Object>();
		properties.put(UserRight.ID, "delete");
		UserRight delete = modelService.findOneDtoByProperties(properties, UserRight.class);

		if (create == null) {
			logger.error("User Right not exists: create");
		} else if (read == null) {
			logger.error("User Right not exists: read");
		} else if (update == null) {
			logger.error("User Right not exists: update");
		} else if (delete == null) {
			logger.error("User Right not exists: delete");
		} else {

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
				UserGroup userGroup = modelService.findOneDtoByProperties(userGroupProperties, UserGroup.class);

				if (userGroup == null) {
					logger.warn("userGroupId not exists: " + userGroupId);
				} else {
					userGroup.getUserAuthorities().clear();
					List<UserAuthority> newUserAuthorities = new ArrayList<UserAuthority>();
					for (UserAuthorityCsv userAuthorityCsv : userGroupAuthorityList) {

						String userPermissionId = userAuthorityCsv.getUserPermissionId();
						
						Map<String, Object> userPermissionProperties = new HashMap<String, Object>();
						userPermissionProperties.put(UserPermission.ID, userPermissionId);
						UserPermission userPermission = modelService.findOneDtoByProperties(userPermissionProperties, UserPermission.class);

						if (userPermission == null) {
							logger.warn("userPermissionId not exists: " + userPermissionId);
						} else {

							if(StringUtils.isNotEmpty(userAuthorityCsv.getCreate())) {
								UserAuthority createRight = new UserAuthority();
								createRight.setUserGroup(userGroup);
								createRight.setUserPermission(userPermission);
								createRight.setUserRight(create);
								createRight.setEnabled(userAuthorityCsv.getCreate().equals(POSITIVE) ? true : false);
								newUserAuthorities.add(createRight);
							}

							if(StringUtils.isNotEmpty(userAuthorityCsv.getRead())) {
								UserAuthority readRight = new UserAuthority();
								readRight.setUserGroup(userGroup);
								readRight.setUserPermission(userPermission);
								readRight.setUserRight(read);
								readRight.setEnabled(userAuthorityCsv.getRead().equals(POSITIVE) ? true : false);
								newUserAuthorities.add(readRight);
							}

							if(StringUtils.isNotEmpty(userAuthorityCsv.getUpdate())) {
								UserAuthority updateRight = new UserAuthority();
								updateRight.setUserGroup(userGroup);
								updateRight.setUserPermission(userPermission);
								updateRight.setUserRight(update);
								updateRight.setEnabled(userAuthorityCsv.getUpdate().equals(POSITIVE) ? true : false);
								newUserAuthorities.add(updateRight);
							}
							
							if(StringUtils.isNotEmpty(userAuthorityCsv.getDelete())) {
								UserAuthority deleteRight = new UserAuthority();
								deleteRight.setUserGroup(userGroup);
								deleteRight.setUserPermission(userPermission);
								deleteRight.setUserRight(delete);
								deleteRight.setEnabled(userAuthorityCsv.getDelete().equals(POSITIVE) ? true : false);
								newUserAuthorities.add(deleteRight);
							}
						}
					}
					
					userGroup.getUserAuthorities().addAll(newUserAuthorities);
					
					try {
						modelService.save(userGroup);
					} catch (ModelSavingException e) {
						logger.error(e.getMessage());
					}
				}

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
			logger.info("Start import UserAuthority Csv.");
			while ((userAuthorityCsv = beanReader.read(UserAuthorityCsv.class, header, processors)) != null) {
				logger.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(),
						userAuthorityCsv);
				userAuthorityCsvList.add(userAuthorityCsv);
			}
			logger.info("Finished import UserAuthority Csv.");
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
