package com.beanframework.console.listener;

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
import org.springframework.data.domain.Sort;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.beanframework.common.service.ModelService;
import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.console.csv.UserAuthorityCsv;
import com.beanframework.console.registry.ImportListener;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserRight;

public class UserAuthorityImportListener extends ImportListener {
	protected static Logger LOGGER = LoggerFactory.getLogger(UserAuthorityImportListener.class);

	@Autowired
	private ModelService modelService;

	@Value("${module.console.import.update.userauthority}")
	private String IMPORT_UPDATE;

	@Value("${module.console.import.remove.userauthority}")
	private String IMPORT_REMOVE;

	@PostConstruct
	public void importer() {
		setKey(ConsoleImportListenerConstants.UserAuthorityImport.KEY);
		setName(ConsoleImportListenerConstants.UserAuthorityImport.NAME);
		setSort(ConsoleImportListenerConstants.UserAuthorityImport.SORT);
		setDescription(ConsoleImportListenerConstants.UserAuthorityImport.DESCRIPTION);
	}

	@Override
	public void update() throws Exception {
		updateByPath(IMPORT_UPDATE);
	}

	@Override
	public void updateByPath(String path) throws Exception {
		PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
		Resource[] resources = loader.getResources(path);
		for (Resource resource : resources) {
			InputStream in = resource.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtils.copy(in, baos);
			BufferedReader reader = new BufferedReader(new StringReader(new String(baos.toByteArray())));

			List<UserAuthorityCsv> csvList = readCSVFile(reader, UserAuthorityCsv.getUpdateProcessors());
			save(csvList);
		}
	}

	@Override
	public void remove() throws Exception {
		removeByPath(IMPORT_REMOVE);
	}

	@Override
	public void removeByPath(String path) throws Exception {
		PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
		Resource[] resources = loader.getResources(path);
		for (Resource resource : resources) {
			InputStream in = resource.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtils.copy(in, baos);
			BufferedReader reader = new BufferedReader(new StringReader(new String(baos.toByteArray())));

			List<UserAuthorityCsv> csvList = readCSVFile(reader, UserAuthorityCsv.getRemoveProcessors());
			remove(csvList);
		}
	}

	@Override
	public void updateByContent(String content) throws Exception {
		List<UserAuthorityCsv> csvList = readCSVFile(new StringReader(content), UserAuthorityCsv.getUpdateProcessors());
		save(csvList);
	}

	@Override
	public void removeByContent(String content) throws Exception {
		List<UserAuthorityCsv> csvList = readCSVFile(new StringReader(content), UserAuthorityCsv.getUpdateProcessors());
		remove(csvList);
	}

	public List<UserAuthorityCsv> readCSVFile(Reader reader, CellProcessor[] processors) {
		ICsvBeanReader beanReader = null;

		List<UserAuthorityCsv> csvList = new ArrayList<UserAuthorityCsv>();

		try {
			beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);

			// the header elements are used to map the values to the bean (names
			// must match)
			final String[] header = beanReader.getHeader(true);

			UserAuthorityCsv csv;
			LOGGER.info("Start import " + ConsoleImportListenerConstants.UserAuthorityImport.NAME);
			while ((csv = beanReader.read(UserAuthorityCsv.class, header, processors)) != null) {
				LOGGER.info("lineNo={}, rowNo={}, {}", beanReader.getLineNumber(), beanReader.getRowNumber(), csv);
				csvList.add(csv);
			}
			LOGGER.info("Finished import " + ConsoleImportListenerConstants.UserAuthorityImport.NAME);
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

	public void save(List<UserAuthorityCsv> csvList) throws Exception {

		// Group permissions by userGroupId
		Map<String, List<UserAuthorityCsv>> userGroupMap = new HashMap<String, List<UserAuthorityCsv>>();
		for (UserAuthorityCsv userAuthorityCsv : csvList) {
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

				generateUserAuthority(userGroup);

				for (int i = 0; i < userGroup.getUserAuthorities().size(); i++) {

					for (UserAuthorityCsv userAuthorityCsv : userGroupAuthorityList) {
						if(StringUtils.isNotBlank(userAuthorityCsv.getUserPermissionId())) {
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
				}

				modelService.saveEntity(userGroup, UserGroup.class);
			}
		}
	}

	private void generateUserAuthority(UserGroup model) throws Exception {

		Map<String, Sort.Direction> userPermissionSorts = new HashMap<String, Sort.Direction>();
		userPermissionSorts.put(UserPermission.SORT, Sort.Direction.ASC);
		List<UserPermission> userPermissions = modelService.findEntityByPropertiesAndSorts(null, userPermissionSorts, null, null, UserPermission.class);

		Map<String, Sort.Direction> userRightSorts = new HashMap<String, Sort.Direction>();
		userRightSorts.put(UserRight.SORT, Sort.Direction.ASC);
		List<UserRight> userRights = modelService.findEntityByPropertiesAndSorts(null, userRightSorts, null, null, UserRight.class);

		for (UserPermission userPermission : userPermissions) {
			for (UserRight userRight : userRights) {

				String authorityId = model.getId() + "_" + userPermission.getId() + "_" + userRight.getId();

				boolean add = true;
				if (model.getUserAuthorities() != null && model.getUserAuthorities().isEmpty() == false) {
					for (UserAuthority userAuthority : model.getUserAuthorities()) {
						if (userAuthority.getId().equals(authorityId)) {
							add = false;
						}
					}
				}

				if (add) {
					UserAuthority userAuthority = modelService.create(UserAuthority.class);
					userAuthority.setId(authorityId);
					userAuthority.setUserPermission(userPermission);
					userAuthority.setUserRight(userRight);

					model.getUserAuthorities().add(userAuthority);
				}
			}
		}
	}

	public void remove(List<UserAuthorityCsv> csvList) throws Exception {
	}
}
