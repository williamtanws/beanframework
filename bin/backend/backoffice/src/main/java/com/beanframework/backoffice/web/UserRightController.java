package com.beanframework.backoffice.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.backoffice.WebBackofficeConstants;
import com.beanframework.backoffice.WebUserRightConstants;
import com.beanframework.backoffice.domain.UserRightSearch;
import com.beanframework.backoffice.service.BackofficeModuleFacade;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.utils.ParamUtils;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.service.UserRightFacade;

@Controller
public class UserRightController {

	@Autowired
	private UserRightFacade userrightFacade;

	@Autowired
	private LocaleMessageService localeMessageService;
	
	@Autowired
	private BackofficeModuleFacade backofficeModuleFacade;

	@Value(WebUserRightConstants.Path.USERRIGHT)
	private String PATH_USERRIGHT;

	@Value(WebUserRightConstants.View.LIST)
	private String VIEW_USERRIGHT_LIST;

	@Value(WebUserRightConstants.LIST_SIZE)
	private int MODULE_USERRIGHT_LIST_SIZE;

	private Page<UserRight> getPagination(Model model, @RequestParam Map<String, Object> requestParams) {
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

		Page<UserRight> pagination = userrightFacade.page(userright, page, size, direction, properties);

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
	public UserRight populateUserRightCreate(HttpServletRequest request) {
		return userrightFacade.create();
	}

	@ModelAttribute(WebUserRightConstants.ModelAttribute.UPDATE)
	public UserRight populateUserRightForm(HttpServletRequest request) {
		return userrightFacade.create();
	}

	@ModelAttribute(WebUserRightConstants.ModelAttribute.SEARCH)
	public UserRightSearch populateUserRightSearch(HttpServletRequest request) {
		return new UserRightSearch();
	}

	@PreAuthorize(WebUserRightConstants.PreAuthorize.READ)
	@GetMapping(value = WebUserRightConstants.Path.USERRIGHT)
	public String list(@ModelAttribute(WebUserRightConstants.ModelAttribute.SEARCH) UserRightSearch userrightSearch,
			@ModelAttribute(WebUserRightConstants.ModelAttribute.UPDATE) UserRight userrightUpdate, Model model,
			@RequestParam Map<String, Object> requestParams) {

		model.addAttribute(WebBackofficeConstants.PAGINATION, getPagination(model, requestParams));

		if (userrightUpdate.getUuid() != null) {
			UserRight existingUserRight = userrightFacade.findByUuid(userrightUpdate.getUuid());
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
			userrightCreate = userrightFacade.save(userrightCreate, bindingResult);
			if (bindingResult.hasErrors()) {

				StringBuilder errorMessage = new StringBuilder();
				List<ObjectError> errors = bindingResult.getAllErrors();
				for (ObjectError error : errors) {
					if (errorMessage.length() != 0) {
						errorMessage.append("<br>");
					}
					errorMessage.append(error.getObjectName() + ": " + error.getDefaultMessage());
				}

				redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR, errorMessage.toString());

			} else {

				redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.SUCCESS,
						localeMessageService.getMessage(WebBackofficeConstants.Locale.SAVE_SUCCESS));
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
			userrightUpdate = userrightFacade.save(userrightUpdate, bindingResult);
			if (bindingResult.hasErrors()) {

				StringBuilder errorMessage = new StringBuilder();
				List<ObjectError> errors = bindingResult.getAllErrors();
				for (ObjectError error : errors) {
					if (errorMessage.length() != 0) {
						errorMessage.append("<br>");
					}
					errorMessage.append(error.getObjectName() + ": " + error.getDefaultMessage());
				}

				redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR, errorMessage.toString());

			} else {

				redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.SUCCESS,
						localeMessageService.getMessage(WebBackofficeConstants.Locale.SAVE_SUCCESS));
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
		
		backofficeModuleFacade.deleteAllModuleUserRightByUserRightUuid(userrightUpdate.getUuid(), bindingResult);

		if (bindingResult.hasErrors() == false) {
			userrightFacade.delete(userrightUpdate.getUuid(), bindingResult);
		}

		if (bindingResult.hasErrors()) {

			StringBuilder errorMessage = new StringBuilder();
			List<ObjectError> errors = bindingResult.getAllErrors();
			for (ObjectError error : errors) {
				if (errorMessage.length() != 0) {
					errorMessage.append("<br>");
				}
				errorMessage.append(error.getObjectName() + ": " + error.getDefaultMessage());
			}

			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR, errorMessage.toString());
			redirectAttributes.addFlashAttribute(WebUserRightConstants.ModelAttribute.UPDATE, userrightUpdate);
		} else {

			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.SUCCESS,
					localeMessageService.getMessage(WebBackofficeConstants.Locale.DELETE_SUCCESS));
		}

		setPaginationRedirectAttributes(redirectAttributes, requestParams, userrightSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERRIGHT);
		return redirectView;

	}
}