package com.beanframework.backoffice.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
import com.beanframework.backoffice.data.UserRightSearch;
import com.beanframework.backoffice.data.UserRightSpecification;
import com.beanframework.common.controller.AbstractCommonController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.utils.ParamUtils;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.service.UserRightFacade;

@Controller
public class UserRightController extends AbstractCommonController {

	@Autowired
	private UserRightFacade userRightFacade;

	@Autowired
	private LocaleMessageService localeMessageService;

	@Value(WebUserRightConstants.Path.USERRIGHT)
	private String PATH_USERRIGHT;

	@Value(WebUserRightConstants.View.LIST)
	private String VIEW_USERRIGHT_LIST;

	@Value(WebUserRightConstants.LIST_SIZE)
	private int MODULE_USERRIGHT_LIST_SIZE;

	private Page<UserRight> getPagination(UserRightSearch userRightSearch, Model model, @RequestParam Map<String, Object> requestParams)
			throws Exception {
		int page = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_USERRIGHT_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.PROPERTIES));
		String[] properties = StringUtils.isBlank(propertiesStr) ? null
				: propertiesStr.split(WebBackofficeConstants.Pagination.PROPERTIES_SPLIT);

		String directionStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.DIRECTION));
		Direction direction = StringUtils.isBlank(directionStr) ? Direction.ASC : Direction.fromString(directionStr);

		if (properties == null) {
			properties = new String[1];
			properties[0] = UserRight.CREATED_DATE;
			direction = Sort.Direction.DESC;
		}

		Page<UserRight> pagination = userRightFacade.findPage(
				UserRightSpecification.findByCriteria(userRightSearch),
				PageRequest.of(page <= 0 ? 0 : page - 1, size <= 0 ? 1 : size, direction, properties));

		model.addAttribute(WebBackofficeConstants.Pagination.PROPERTIES, propertiesStr);
		model.addAttribute(WebBackofficeConstants.Pagination.DIRECTION, directionStr);

		return pagination;
	}

	private RedirectAttributes setPaginationRedirectAttributes(RedirectAttributes redirectAttributes,
			@RequestParam Map<String, Object> requestParams, UserRightSearch userrightSearch) {
		
		userrightSearch.setSearchAll((String)requestParams.get("userrightSearch.searchAll"));
		userrightSearch.setId((String)requestParams.get("userrightSearch.id"));
		
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
		redirectAttributes.addAttribute("searchAll", userrightSearch.getSearchAll());
		redirectAttributes.addAttribute("id", userrightSearch.getId());

		return redirectAttributes;
	}

	@ModelAttribute(WebUserRightConstants.ModelAttribute.CREATE)
	public UserRight populateUserRightCreate(HttpServletRequest request) throws Exception {
		return userRightFacade.create();
	}

	@ModelAttribute(WebUserRightConstants.ModelAttribute.UPDATE)
	public UserRight populateUserRightForm(HttpServletRequest request) throws Exception {
		return userRightFacade.create();
	}

	@ModelAttribute(WebUserRightConstants.ModelAttribute.SEARCH)
	public UserRightSearch populateUserRightSearch(HttpServletRequest request) {
		return new UserRightSearch();
	}

	@GetMapping(value = WebUserRightConstants.Path.USERRIGHT)
	public String list(
			@ModelAttribute(WebUserRightConstants.ModelAttribute.SEARCH) UserRightSearch userrightSearch,
			@ModelAttribute(WebUserRightConstants.ModelAttribute.UPDATE) UserRight userrightUpdate,
			Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		model.addAttribute(WebBackofficeConstants.PAGINATION, getPagination(userrightSearch, model, requestParams));

		if (userrightUpdate.getUuid() != null) {

			UserRight existingUserRight = userRightFacade.findOneDtoByUuid(userrightUpdate.getUuid());

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

	@PostMapping(value = WebUserRightConstants.Path.USERRIGHT, params = "create")
	public RedirectView create(
			@ModelAttribute(WebUserRightConstants.ModelAttribute.SEARCH) UserRightSearch userrightSearch,
			@ModelAttribute(WebUserRightConstants.ModelAttribute.CREATE) UserRight userrightCreate,
			Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (userrightCreate.getUuid() != null) {
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR,
					"Create new record doesn't need UUID.");
		} else {

			try {
				userrightCreate = userRightFacade.createDto(userrightCreate);

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

	@PostMapping(value = WebUserRightConstants.Path.USERRIGHT, params = "update")
	public RedirectView update(
			@ModelAttribute(WebUserRightConstants.ModelAttribute.SEARCH) UserRightSearch userrightSearch,
			@ModelAttribute(WebUserRightConstants.ModelAttribute.UPDATE) UserRight userrightUpdate,
			Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (userrightUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR,
					"Update record needed existing UUID.");
		} else {
			try {
				userrightUpdate = userRightFacade.updateDto(userrightUpdate);

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

	@PostMapping(value = WebUserRightConstants.Path.USERRIGHT, params = "delete")
	public RedirectView delete(
			@ModelAttribute(WebUserRightConstants.ModelAttribute.SEARCH) UserRightSearch userrightSearch,
			@ModelAttribute(WebUserRightConstants.ModelAttribute.UPDATE) UserRight userrightUpdate,
			Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		try {

			userRightFacade.delete(userrightUpdate.getUuid());

			addSuccessMessage(redirectAttributes, WebBackofficeConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(UserRight.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addFlashAttribute(WebUserRightConstants.ModelAttribute.UPDATE,
					userrightUpdate);
		}

		setPaginationRedirectAttributes(redirectAttributes, requestParams, userrightSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERRIGHT);
		return redirectView;

	}
}
