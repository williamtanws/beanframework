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
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.beanframework.common.Updater;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.WebPlatformConstants;
import com.beanframework.console.domain.UserAuthorityCsv;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserRight;

public class UserAuthorityUpdate extends Updater {
	private static Logger LOGGER = LoggerFactory.getLogger(UserAuthorityUpdate.class);

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

				} catch (Exception ex) {
					LOGGER.error("Error reading the resource file: " + ex);
				}
			}
		} catch (IOException ex) {
			LOGGER.error("Error reading the resource file: " + ex);
		}
	}

	public void save(List<UserAuthorityCsv> userAuthorityCsvList) {

		try {

			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(UserRight.ID, "create");
			UserRight create = modelService.findOneEntityByProperties(properties, UserRight.class);

			properties = new HashMap<String, Object>();
			properties.put(UserRight.ID, "read");
			UserRight read = modelService.findOneEntityByProperties(properties, UserRight.class);

			properties = new HashMap<String, Object>();
			properties.put(UserRight.ID, "update");
			UserRight update = modelService.findOneEntityByProperties(properties, UserRight.class);

			properties = new HashMap<String, Object>();
			properties.put(UserRight.ID, "delete");
			UserRight delete = modelService.findOneEntityByProperties(properties, UserRight.class);

			if (create == null) {
				LOGGER.error("User Right not exists: create");
			} else if (read == null) {
				LOGGER.error("User Right not exists: read");
			} else if (update == null) {
				LOGGER.error("User Right not exists: update");
			} else if (delete == null) {
				LOGGER.error("User Right not exists: delete");
			} else {

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

						for (UserAuthorityCsv userAuthorityCsv : userGroupAuthorityList) {

							String userPermissionId = userAuthorityCsv.getUserPermissionId();

							Map<String, Object> userPermissionProperties = new HashMap<String, Object>();
							userPermissionProperties.put(UserPermission.ID, userPermissionId);
							UserPermission userPermission = modelService
									.findOneEntityByProperties(userPermissionProperties, UserPermission.class);

							if (userPermission == null) {
								LOGGER.error("userPermissionId not exists: " + userPermissionId);
							} else {

								// UserGroup

								boolean createAuthority = true;
								boolean readAuthority = true;
								boolean updateAuthority = true;
								boolean deleteAuthority = true;

								if (StringUtils.isNotEmpty(userAuthorityCsv.getCreate())) {

									for (int i = 0; i < userGroup.getUserAuthorities().size(); i++) {
										if (userGroup.getUserAuthorities().get(i).getId()
												.equals(userGroup.getId() + "_" + userPermission.getId() + "_create")) {
											userGroup.getUserAuthorities().get(i).setEnabled(
													userAuthorityCsv.getCreate().equals(POSITIVE) ? true : false);
											createAuthority = false;
										}
									}
								}
								if (StringUtils.isNotEmpty(userAuthorityCsv.getRead())) {

									for (int i = 0; i < userGroup.getUserAuthorities().size(); i++) {
										if (userGroup.getUserAuthorities().get(i).getId()
												.equals(userGroup.getId() + "_" + userPermission.getId() + "_read")) {
											userGroup.getUserAuthorities().get(i).setEnabled(
													userAuthorityCsv.getRead().equals(POSITIVE) ? true : false);
											readAuthority = false;
										}
									}
								}
								if (StringUtils.isNotEmpty(userAuthorityCsv.getUpdate())) {

									for (int i = 0; i < userGroup.getUserAuthorities().size(); i++) {
										if (userGroup.getUserAuthorities().get(i).getId()
												.equals(userGroup.getId() + "_" + userPermission.getId() + "_update")) {
											userGroup.getUserAuthorities().get(i).setEnabled(
													userAuthorityCsv.getUpdate().equals(POSITIVE) ? true : false);
											updateAuthority = false;
										}
									}
								}
								if (StringUtils.isNotEmpty(userAuthorityCsv.getDelete())) {

									for (int i = 0; i < userGroup.getUserAuthorities().size(); i++) {
										if (userGroup.getUserAuthorities().get(i).getId()
												.equals(userGroup.getId() + "_" + userPermission.getId() + "_delete")) {
											userGroup.getUserAuthorities().get(i).setEnabled(
													userAuthorityCsv.getDelete().equals(POSITIVE) ? true : false);
											deleteAuthority = false;
										}
									}
								}

								modelService.saveEntity(userGroup, UserGroup.class);

								// UserAuthority

								if (createAuthority && userAuthorityCsv.getCreate() != null) {
									UserAuthority createRight = new UserAuthority();
									createRight.setId(userGroup.getId() + "_" + userPermission.getId() + "_create");
									createRight.setUserGroup(userGroup);
									createRight.setUserPermission(userPermission);
									createRight.setUserRight(create);
									createRight
											.setEnabled(userAuthorityCsv.getCreate().equals(POSITIVE) ? true : false);
									userGroup.getUserAuthorities().add(createRight);

									modelService.saveEntity(createRight, UserAuthority.class);
								}
								if (readAuthority && userAuthorityCsv.getRead() != null) {
									UserAuthority readRight = new UserAuthority();
									readRight.setId(userGroup.getId() + "_" + userPermission.getId() + "_read");
									readRight.setUserGroup(userGroup);
									readRight.setUserPermission(userPermission);
									readRight.setUserRight(read);
									readRight.setEnabled(userAuthorityCsv.getRead().equals(POSITIVE) ? true : false);
									userGroup.getUserAuthorities().add(readRight);

									modelService.saveEntity(readRight, UserAuthority.class);
								}
								if (updateAuthority && userAuthorityCsv.getUpdate() != null) {
									UserAuthority updateRight = new UserAuthority();
									updateRight.setId(userGroup.getId() + "_" + userPermission.getId() + "_update");
									updateRight.setUserGroup(userGroup);
									updateRight.setUserPermission(userPermission);
									updateRight.setUserRight(update);
									updateRight
											.setEnabled(userAuthorityCsv.getUpdate().equals(POSITIVE) ? true : false);
									userGroup.getUserAuthorities().add(updateRight);

									modelService.saveEntity(updateRight, UserAuthority.class);
								}
								if (deleteAuthority && userAuthorityCsv.getDelete() != null) {
									UserAuthority deleteRight = new UserAuthority();
									deleteRight.setId(userGroup.getId() + "_" + userPermission.getId() + "_delete");
									deleteRight.setUserGroup(userGroup);
									deleteRight.setUserPermission(userPermission);
									deleteRight.setUserRight(delete);
									deleteRight
											.setEnabled(userAuthorityCsv.getDelete().equals(POSITIVE) ? true : false);
									userGroup.getUserAuthorities().add(deleteRight);

									modelService.saveEntity(deleteRight, UserAuthority.class);
								}

							}
						}
					}

				}
			}

			modelService.saveAll();

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
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
				LOGGER.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(),
						userAuthorityCsv);
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
