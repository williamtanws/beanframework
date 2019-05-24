package com.beanframework.console.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.beanframework.common.service.ModelService;
import com.beanframework.console.ConsoleImportListenerConstants;
import com.beanframework.console.csv.UserAuthorityCsv;
import com.beanframework.imex.registry.ImportListener;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserRight;

public class UserAuthorityImportListener extends ImportListener {
	protected static Logger LOGGER = LoggerFactory.getLogger(UserAuthorityImportListener.class);

	@Autowired
	private ModelService modelService;

	@PostConstruct
	public void importer() {
		setKey(ConsoleImportListenerConstants.UserAuthorityImport.KEY);
		setName(ConsoleImportListenerConstants.UserAuthorityImport.NAME);
		setSort(ConsoleImportListenerConstants.UserAuthorityImport.SORT);
		setDescription(ConsoleImportListenerConstants.UserAuthorityImport.DESCRIPTION);
		setClassCsv(ConsoleImportListenerConstants.UserAuthorityImport.CLASS_CSV);
		setClassEntity(ConsoleImportListenerConstants.UserAuthorityImport.CLASS_ENTITY);
		setCustomImport(true);
	}

	@Override
	public void customImport(Object objectCsv) throws Exception {
		UserAuthorityCsv csv = (UserAuthorityCsv) objectCsv;
		save(csv);
	}

	public void save(UserAuthorityCsv csv) throws Exception {

		// Group permissions by userGroupId
		Map<String, List<UserAuthorityCsv>> userGroupMap = new HashMap<String, List<UserAuthorityCsv>>();
		String userGroupId = csv.getUserGroupId();

		if (userGroupMap.get(userGroupId) == null) {
			userGroupMap.put(userGroupId, new ArrayList<UserAuthorityCsv>());
		}

		List<UserAuthorityCsv> userGroupUserAuthorityList = userGroupMap.get(userGroupId);
		userGroupUserAuthorityList.add(csv);

		userGroupMap.put(csv.getUserGroupId(), userGroupUserAuthorityList);
		

		for (Map.Entry<String, List<UserAuthorityCsv>> entry : userGroupMap.entrySet()) {
			String entryKeyUserGroupId = entry.getKey();
			List<UserAuthorityCsv> userGroupAuthorityList = entry.getValue();

			Map<String, Object> userGroupProperties = new HashMap<String, Object>();
			userGroupProperties.put(UserGroup.ID, entryKeyUserGroupId);
			UserGroup userGroup = modelService.findOneByProperties(userGroupProperties, UserGroup.class);

			if (userGroup == null) {
				LOGGER.error("userGroupId not exists: " + entryKeyUserGroupId);
			} else {

				generateUserAuthority(userGroup);

				for (int i = 0; i < userGroup.getUserAuthorities().size(); i++) {

					for (UserAuthorityCsv userAuthorityCsv : userGroupAuthorityList) {
						if (StringUtils.isNotBlank(userAuthorityCsv.getUserPermissionId())) {
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
		List<UserPermission> userPermissions = modelService.findByPropertiesBySortByResult(null, userPermissionSorts, null, null, UserPermission.class);

		Map<String, Sort.Direction> userRightSorts = new HashMap<String, Sort.Direction>();
		userRightSorts.put(UserRight.SORT, Sort.Direction.ASC);
		List<UserRight> userRights = modelService.findByPropertiesBySortByResult(null, userRightSorts, null, null, UserRight.class);

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
}
