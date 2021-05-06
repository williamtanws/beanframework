package com.beanframework.core.listenerimport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.service.ModelService;
import com.beanframework.core.ImportListenerConstants;
import com.beanframework.core.csv.UserAuthorityCsv;
import com.beanframework.imex.registry.ImportListener;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.service.UserGroupService;

public class UserAuthorityImportListener extends ImportListener {
	protected static Logger LOGGER = LoggerFactory.getLogger(UserAuthorityImportListener.class);

	@Autowired
	private ModelService modelService;
	
	@Autowired
	private UserGroupService userGroupService;

	@PostConstruct
	public void importer() {
		setType(ImportListenerConstants.UserAuthorityImport.TYPE);
		setName(ImportListenerConstants.UserAuthorityImport.NAME);
		setSort(ImportListenerConstants.UserAuthorityImport.SORT);
		setDescription(ImportListenerConstants.UserAuthorityImport.DESCRIPTION);
		setClassCsv(ImportListenerConstants.UserAuthorityImport.CLASS_CSV);
		setClassEntity(ImportListenerConstants.UserAuthorityImport.CLASS_ENTITY);
		setCustomImport(true);
	}

	@Override
	public boolean customImport(Object objectCsv) throws Exception {
		UserAuthorityCsv csv = (UserAuthorityCsv) objectCsv;
		return save(csv);
	}

	public boolean save(UserAuthorityCsv csv) throws Exception {
		boolean imported = false;

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

				userGroupService.generateUserAuthority(userGroup);

				for (int i = 0; i < userGroup.getUserAuthorities().size(); i++) {

					for (UserAuthorityCsv userAuthorityCsv : userGroupAuthorityList) {
						if (StringUtils.isNotBlank(userAuthorityCsv.getUserPermissionId())) {
							if (userGroup.getUserAuthorities().get(i).getUserPermission().getId().equals(userAuthorityCsv.getUserPermissionId())) {
								if (userGroup.getUserAuthorities().get(i).getUserRight().getId().equals("create")) {
									if (StringUtils.isNotBlank(userAuthorityCsv.getCreate())) {
										userGroup.getUserAuthorities().get(i).setEnabled(POSITIVE.equals(userAuthorityCsv.getCreate()) ? Boolean.TRUE : Boolean.FALSE);
									}
								}
								if (userGroup.getUserAuthorities().get(i).getUserRight().getId().equals("read")) {
									if (StringUtils.isNotBlank(userAuthorityCsv.getRead())) {
										userGroup.getUserAuthorities().get(i).setEnabled(POSITIVE.equals(userAuthorityCsv.getRead()) ? Boolean.TRUE : Boolean.FALSE);
									}
								}
								if (userGroup.getUserAuthorities().get(i).getUserRight().getId().equals("update")) {
									if (StringUtils.isNotBlank(userAuthorityCsv.getUpdate())) {
										userGroup.getUserAuthorities().get(i).setEnabled(POSITIVE.equals(userAuthorityCsv.getUpdate()) ? Boolean.TRUE : Boolean.FALSE);
									}
								}
								if (userGroup.getUserAuthorities().get(i).getUserRight().getId().equals("delete")) {
									if (StringUtils.isNotBlank(userAuthorityCsv.getDelete())) {
										userGroup.getUserAuthorities().get(i).setEnabled(POSITIVE.equals(userAuthorityCsv.getDelete()) ? Boolean.TRUE : Boolean.FALSE);
									}
								}
							}
						}
					}
				}

				modelService.saveEntity(userGroup);
				imported = true;
			}
		}

		return imported;
	}
}
