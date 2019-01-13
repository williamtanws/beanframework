package com.beanframework.backoffice.web;

import java.util.List;
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

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.UserRightWebConstants;
import com.beanframework.backoffice.data.UserRightSearch;
import com.beanframework.backoffice.data.UserRightSpecification;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.utils.ParamUtils;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.service.UserRightFacade;
import com.beanframework.user.service.UserRightService;

@Controller
public class UserRightController extends AbstractController {

	@Autowired
	private UserRightFacade userRightFacade;
	
	@Autowired
	private UserRightService userRightService;

	@Autowired
	private LocaleMessageService localeMessageService;

	@Value(UserRightWebConstants.Path.USERRIGHT)
	private String PATH_USERRIGHT;

	@Value(UserRightWebConstants.View.LIST)
	private String VIEW_USERRIGHT_LIST;

	@Value(UserRightWebConstants.LIST_SIZE)
	private int MODULE_USERRIGHT_LIST_SIZE;

	private Page<UserRight> getPagination(UserRightSearch userRightSearch, Model model, @RequestParam Map<String, Object> requestParams)
			throws Exception {
		int page = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_USERRIGHT_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.PROPERTIES));
		String[] properties = StringUtils.isBlank(propertiesStr) ? null
				: propertiesStr.split(BackofficeWebConstants.Pagination.PROPERTIES_SPLIT);

		String directionStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.DIRECTION));
		Direction direction = StringUtils.isBlank(directionStr) ? Direction.ASC : Direction.fromString(directionStr);

		if (properties == null) {
			properties = new String[1];
			properties[0] = UserRight.CREATED_DATE;
			direction = Sort.Direction.DESC;
		}

		Page<UserRight> pagination = userRightFacade.findPage(
				UserRightSpecification.findByCriteria(userRightSearch),
				PageRequest.of(page <= 0 ? 0 : page - 1, size <= 0 ? 1 : size, direction, properties));

		model.addAttribute(BackofficeWebConstants.Pagination.PROPERTIES, propertiesStr);
		model.addAttribute(BackofficeWebConstants.Pagination.DIRECTION, directionStr);

		return pagination;
	}

	private RedirectAttributes setPaginationRedirectAttributes(RedirectAttributes redirectAttributes,
			@RequestParam Map<String, Object> requestParams, UserRightSearch userrightSearch) {
		
		userrightSearch.setSearchAll((String)requestParams.get("userrightSearch.searchAll"));
		userrightSearch.setId((String)requestParams.get("userrightSearch.id"));
		
		int page = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_USERRIGHT_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.PROPERTIES));
		String directionStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.DIRECTION));

		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.PAGE, page);
		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.SIZE, size);
		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.PROPERTIES, propertiesStr);
		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.DIRECTION, directionStr);
		redirectAttributes.addAttribute("searchAll", userrightSearch.getSearchAll());
		redirectAttributes.addAttribute("id", userrightSearch.getId());

		return redirectAttributes;
	}

	@ModelAttribute(UserRightWebConstants.ModelAttribute.CREATE)
	public UserRight populateUserRightCreate(HttpServletRequest request) throws Exception {
		return userRightService.create();
	}

	@ModelAttribute(UserRightWebConstants.ModelAttribute.UPDATE)
	public UserRight populateUserRightForm(HttpServletRequest request) throws Exception {
		return userRightService.create();
	}

	@ModelAttribute(UserRightWebConstants.ModelAttribute.SEARCH)
	public UserRightSearch populateUserRightSearch(HttpServletRequest request) {
		return new UserRightSearch();
	}

	@GetMapping(value = UserRightWebConstants.Path.USERRIGHT)
	public String list(
			@ModelAttribute(UserRightWebConstants.ModelAttribute.SEARCH) UserRightSearch userrightSearch,
			@ModelAttribute(UserRightWebConstants.ModelAttribute.UPDATE) UserRight userrightUpdate,
			Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		model.addAttribute(BackofficeWebConstants.PAGINATION, getPagination(userrightSearch, model, requestParams));

		if (userrightUpdate.getUuid() != null) {

			UserRight existingUserRight = userRightFacade.findOneDtoByUuid(userrightUpdate.getUuid());
			
			List<Object[]> revisions = userRightFacade.findHistoryByUuid(userrightUpdate.getUuid(), null, null);
			model.addAttribute(BackofficeWebConstants.Model.REVISIONS, revisions);
			
			List<Object[]> fieldRevisions = userRightFacade.findFieldHistoryByUuid(userrightUpdate.getUuid(), null, null);
			model.addAttribute(BackofficeWebConstants.Model.FIELD_REVISIONS, fieldRevisions);

			if (existingUserRight != null) {
				model.addAttribute(UserRightWebConstants.ModelAttribute.UPDATE, existingUserRight);
			} else {
				userrightUpdate.setUuid(null);
				model.addAttribute(BackofficeWebConstants.Model.ERROR,
						localeMessageService.getMessage(BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND));
			}
		}

		return VIEW_USERRIGHT_LIST;
	}

	@PostMapping(value = UserRightWebConstants.Path.USERRIGHT, params = "create")
	public RedirectView create(
			@ModelAttribute(UserRightWebConstants.ModelAttribute.SEARCH) UserRightSearch userrightSearch,
			@ModelAttribute(UserRightWebConstants.ModelAttribute.CREATE) UserRight userrightCreate,
			Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (userrightCreate.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
					"Create new record doesn't need UUID.");
		} else {

			try {
				userrightCreate = userRightFacade.createDto(userrightCreate);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
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

	@PostMapping(value = UserRightWebConstants.Path.USERRIGHT, params = "update")
	public RedirectView update(
			@ModelAttribute(UserRightWebConstants.ModelAttribute.SEARCH) UserRightSearch userrightSearch,
			@ModelAttribute(UserRightWebConstants.ModelAttribute.UPDATE) UserRight userrightUpdate,
			Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (userrightUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
					"Update record needed existing UUID.");
		} else {
			try {
				userrightUpdate = userRightFacade.updateDto(userrightUpdate);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
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

	@PostMapping(value = UserRightWebConstants.Path.USERRIGHT, params = "delete")
	public RedirectView delete(
			@ModelAttribute(UserRightWebConstants.ModelAttribute.SEARCH) UserRightSearch userrightSearch,
			@ModelAttribute(UserRightWebConstants.ModelAttribute.UPDATE) UserRight userrightUpdate,
			Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		try {

			userRightFacade.delete(userrightUpdate.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(UserRight.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addFlashAttribute(UserRightWebConstants.ModelAttribute.UPDATE,
					userrightUpdate);
		}

		setPaginationRedirectAttributes(redirectAttributes, requestParams, userrightSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERRIGHT);
		return redirectView;

	}
}
