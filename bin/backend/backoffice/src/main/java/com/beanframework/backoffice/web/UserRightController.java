package com.beanframework.backoffice.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.backoffice.WebBackofficeConstants;
import com.beanframework.backoffice.WebUserRightConstants;
import com.beanframework.backoffice.domain.UserRightSearch;
import com.beanframework.common.controller.AbstractCommonController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.service.ModelService;
import com.beanframework.common.utils.ParamUtils;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.domain.UserRightSpecification;

@Controller
public class UserRightController extends AbstractCommonController {

	@Autowired
	private ModelService modelService;

	@Autowired
	private LocaleMessageService localeMessageService;

//	@Autowired
//	private BackofficeModuleFacade backofficeModuleFacade;

	@Value(WebUserRightConstants.Path.USERRIGHT)
	private String PATH_USERRIGHT;

	@Value(WebUserRightConstants.View.LIST)
	private String VIEW_USERRIGHT_LIST;

	@Value(WebUserRightConstants.LIST_SIZE)
	private int MODULE_USERRIGHT_LIST_SIZE;

	private Page<UserRight> getPagination(Model model, @RequestParam Map<String, Object> requestParams)
			throws Exception {
		int page = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_USERRIGHT_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.PROPERTIES));
		String[] properties = StringUtils.isEmpty(propertiesStr) ? null
				: propertiesStr.split(WebBackofficeConstants.Pagination.PROPERTIES_SPLIT);

		String directionStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.DIRECTION));
		Direction direction = StringUtils.isEmpty(directionStr) ? Direction.ASC : Direction.fromString(directionStr);

		UserRightSearch userrightSearch = (UserRightSearch) model.asMap()
				.get(WebUserRightConstants.ModelAttribute.SEARCH);

		UserRight userright = new UserRight();
		userright.setId(userrightSearch.getIdSearch());

		if (properties == null) {
			properties = new String[1];
			properties[0] = UserRight.CREATED_DATE;
			direction = Sort.Direction.DESC;
		}

		Page<UserRight> pagination = modelService.findPage(UserRightSpecification.findByCriteria(userright),
				PageRequest.of(page <= 0 ? 0 : page - 1, size <= 0 ? 1 : size, direction, properties), UserRight.class);

		model.addAttribute(WebBackofficeConstants.Pagination.PROPERTIES, propertiesStr);
		model.addAttribute(WebBackofficeConstants.Pagination.DIRECTION, directionStr);

		return pagination;
	}

	private RedirectAttributes setPaginationRedirectAttributes(RedirectAttributes redirectAttributes,
			@RequestParam Map<String, Object> requestParams, UserRightSearch userrightSearch) {
		int page = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_USERRIGHT_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.PROPERTIES));
		String directionStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.DIRECTION));

		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.PAGE, page);
		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.SIZE, size);
		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.PROPERTIES, propertiesStr);
		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.DIRECTION, directionStr);
		redirectAttributes.addAttribute(UserRightSearch.ID_SEARCH, userrightSearch.getIdSearch());
		redirectAttributes.addFlashAttribute(WebUserRightConstants.ModelAttribute.SEARCH, userrightSearch);

		return redirectAttributes;
	}

	@ModelAttribute(WebUserRightConstants.ModelAttribute.CREATE)
	public UserRight populateUserRightCreate(HttpServletRequest request) throws Exception {
		return modelService.create(UserRight.class);
	}

	@ModelAttribute(WebUserRightConstants.ModelAttribute.UPDATE)
	public UserRight populateUserRightForm(HttpServletRequest request) throws Exception {
		return modelService.create(UserRight.class);
	}

	@ModelAttribute(WebUserRightConstants.ModelAttribute.SEARCH)
	public UserRightSearch populateUserRightSearch(HttpServletRequest request) {
		return new UserRightSearch();
	}

	@PreAuthorize(WebUserRightConstants.PreAuthorize.READ)
	@GetMapping(value = WebUserRightConstants.Path.USERRIGHT)
	public String list(@ModelAttribute(WebUserRightConstants.ModelAttribute.SEARCH) UserRightSearch userrightSearch,
			@ModelAttribute(WebUserRightConstants.ModelAttribute.UPDATE) UserRight userrightUpdate, Model model,
			@RequestParam Map<String, Object> requestParams) throws Exception {

		model.addAttribute(WebBackofficeConstants.PAGINATION, getPagination(model, requestParams));

		if (userrightUpdate.getUuid() != null) {

			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(UserRight.UUID, userrightUpdate.getUuid());

			UserRight existingUserRight = modelService.findOneDtoByProperties(properties, UserRight.class);

			if (existingUserRight != null) {
				model.addAttribute(WebUserRightConstants.ModelAttribute.UPDATE, existingUserRight);
			} else {
				userrightUpdate.setUuid(null);
				model.addAttribute(WebBackofficeConstants.Model.ERROR,
						localeMessageService.getMessage(WebBackofficeConstants.Locale.RECORD_UUID_NOT_FOUND));
			}
		}

		return VIEW_USERRIGHT_LIST;
	}

	@PreAuthorize(WebUserRightConstants.PreAuthorize.CREATE)
	@PostMapping(value = WebUserRightConstants.Path.USERRIGHT, params = "create")
	public RedirectView create(
			@ModelAttribute(WebUserRightConstants.ModelAttribute.SEARCH) UserRightSearch userrightSearch,
			@ModelAttribute(WebUserRightConstants.ModelAttribute.CREATE) UserRight userrightCreate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (userrightCreate.getUuid() != null) {
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR,
					"Create new record doesn't need UUID.");
		} else {

			try {
				modelService.saveDto(userrightCreate);

				addSuccessMessage(redirectAttributes, WebBackofficeConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(UserRight.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(UserRight.UUID, userrightCreate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, userrightSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERRIGHT);
		return redirectView;
	}

	@PreAuthorize(WebUserRightConstants.PreAuthorize.UPDATE)
	@PostMapping(value = WebUserRightConstants.Path.USERRIGHT, params = "update")
	public RedirectView update(
			@ModelAttribute(WebUserRightConstants.ModelAttribute.SEARCH) UserRightSearch userrightSearch,
			@ModelAttribute(WebUserRightConstants.ModelAttribute.UPDATE) UserRight userrightUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (userrightUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR,
					"Update record needed existing UUID.");
		} else {
			try {
				modelService.saveDto(userrightUpdate);

				addSuccessMessage(redirectAttributes, WebBackofficeConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(UserRight.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(UserRight.UUID, userrightUpdate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, userrightSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERRIGHT);
		return redirectView;
	}

	@PreAuthorize(WebUserRightConstants.PreAuthorize.DELETE)
	@PostMapping(value = WebUserRightConstants.Path.USERRIGHT, params = "delete")
	public RedirectView delete(
			@ModelAttribute(WebUserRightConstants.ModelAttribute.SEARCH) UserRightSearch userrightSearch,
			@ModelAttribute(WebUserRightConstants.ModelAttribute.UPDATE) UserRight userrightUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		try {
//			backofficeModuleFacade.deleteAllModuleUserRightByUserRightUuid(userrightUpdate.getUuid(), bindingResult);

			modelService.remove(userrightUpdate.getUuid(), UserRight.class);

			addSuccessMessage(redirectAttributes, WebBackofficeConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(UserRight.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addFlashAttribute(WebUserRightConstants.ModelAttribute.UPDATE, userrightUpdate);
		}

		setPaginationRedirectAttributes(redirectAttributes, requestParams, userrightSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERRIGHT);
		return redirectView;

	}
}
