package com.beanframework.core.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.beanframework.comment.service.CommentService;
import com.beanframework.common.exception.ListenerException;
import com.beanframework.common.registry.BeforeRemoveEvent;
import com.beanframework.common.registry.BeforeRemoveListener;
import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;
import com.beanframework.dynamicfield.service.DynamicFieldService;
import com.beanframework.dynamicfield.service.DynamicFieldTemplateService;
import com.beanframework.enumuration.domain.Enumeration;
import com.beanframework.imex.domain.Imex;
import com.beanframework.internationalization.domain.Country;
import com.beanframework.internationalization.domain.Language;
import com.beanframework.internationalization.domain.Region;
import com.beanframework.internationalization.service.RegionService;
import com.beanframework.media.MediaConstants;
import com.beanframework.media.domain.Media;
import com.beanframework.media.service.MediaService;
import com.beanframework.menu.service.MenuService;
import com.beanframework.user.UserConstants;
import com.beanframework.user.domain.Address;
import com.beanframework.user.domain.Company;
import com.beanframework.user.domain.User;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.service.AddressService;
import com.beanframework.user.service.CompanyService;
import com.beanframework.user.service.UserGroupService;
import com.beanframework.user.service.UserService;

public class CoreBeforeRemoveListener implements BeforeRemoveListener {
	
	@Autowired
	private ModelService modelService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private DynamicFieldService dynamicFieldService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserGroupService userGroupService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private AddressService addressService;

	@Autowired
	private RegionService regionService;
	
	@Autowired
	private MediaService mediaService;

	@Autowired
	private DynamicFieldTemplateService dynamicFieldTemplateService;

	@Value(UserConstants.USER_MEDIA_LOCATION)
	public String PROFILE_PICTURE_LOCATION;

	@Value(MediaConstants.MEDIA_LOCATION)
	public String MEDIA_LOCATION;

	@Override
	public void beforeRemove(final Object model, final BeforeRemoveEvent event) throws ListenerException {

		try {
			if (model instanceof Language) {
				Language language = (Language) model;
				dynamicFieldService.removeLanguageRel(language);

			} else if (model instanceof Enumeration) {
				Enumeration enumeration = (Enumeration) model;
				dynamicFieldService.removeEnumerationsRel(enumeration);

			} else if (model instanceof UserGroup) {
				UserGroup userGroup = (UserGroup) model;
				menuService.removeUserGroupsRel(userGroup);
				userService.removeUserGroupsRel(userGroup);
				userGroupService.removeUserGroupsRel(userGroup);

			} else if (model instanceof User) {
				User user = (User) model;
				companyService.removeContactPersonRel(user);
				commentService.removeUserRel(user);
				addressService.removeOwnerRel(user);
				userService.deleteProfilePictureFileByUuid(user.getUuid());

			} else if (model instanceof Media) {
				Media media = (Media) model;
				mediaService.removeFile(media);

			} else if (model instanceof Imex) {
				Imex imex = modelService.findOneByUuid(((Imex) model).getUuid(), Imex.class);
				for (Media media : imex.getMedias()) {
					mediaService.removeFile(media);
				}

			} else if (model instanceof UserRight) {
				UserRight userRight = (UserRight) model;
				userGroupService.removeUserRightsRel(userRight);

			} else if (model instanceof UserPermission) {
				UserPermission userPermission = (UserPermission) model;
				userGroupService.removeUserPermissionsRel(userPermission);

			} else if (model instanceof DynamicFieldSlot) {
				DynamicFieldSlot dynamicFieldSlot = (DynamicFieldSlot) model;
				dynamicFieldTemplateService.removeDynamicFieldSlotsRel(dynamicFieldSlot);

			} else if (model instanceof Country) {
				Country country = (Country) model;
				companyService.removeCountryRel(country);
				addressService.removeCountryRel(country);
				regionService.removeCountryRel(country);

			} else if (model instanceof Region) {
				Region region = (Region) model;
				addressService.removeRegionRel(region);

			} else if (model instanceof Address) {
				Address address = (Address) model;
				addressService.removeShippingAddressRel(address);
				addressService.removeBillingAddressRel(address);
				addressService.removeContactAddressRel(address);
				addressService.removeDefaultPaymentAddressRel(address);
				addressService.removeDefaultShipmentAddressRel(address);
				
				companyService.removeAddressesRel(address);
				companyService.removeShippingAddressRel(address);
				companyService.removeUnloadingAddressRel(address);
				companyService.removeBillingAddressRel(address);
				companyService.removeContactAddressRel(address);

			} else if (model instanceof Company) {
				Company company = (Company) model;
				companyService.removeResponsibleCompanyRel(company);
			}

		} catch (Exception e) {
			throw new ListenerException(e.getMessage(), e);
		}
	}
}
