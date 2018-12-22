package com.beanframework.console.web;

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

import com.beanframework.admin.domain.Admin;
import com.beanframework.admin.domain.AdminSpecification;
import com.beanframework.common.controller.AbstractCommonController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.common.utils.ParamUtils;
import com.beanframework.console.WebAdminConstants;
import com.beanframework.console.WebConsoleConstants;
import com.beanframework.console.domain.AdminSearch;

@Controller
public class AdminController extends AbstractCommonController {

	@Autowired
	private ModelService modelService;

	@Value(WebAdminConstants.Path.ADMIN)
	private String PATH_ADMIN;

	@Value(WebAdminConstants.View.LIST)
	private String VIEW_ADMIN_LIST;

	@Value(WebAdminConstants.LIST_SIZE)
	private int MODULE_ADMIN_LIST_SIZE;

	private Page<Admin> getPagination(Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		int page = ParamUtils.parseInt(requestParams.get(WebConsoleConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(WebConsoleConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_ADMIN_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(WebConsoleConstants.Pagination.PROPERTIES));
		String[] properties = StringUtils.isEmpty(propertiesStr) ? null
				: propertiesStr.split(WebConsoleConstants.Pagination.PROPERTIES_SPLIT);

		String directionStr = ParamUtils.parseString(requestParams.get(WebConsoleConstants.Pagination.DIRECTION));
		Direction direction = StringUtils.isEmpty(directionStr) ? Direction.ASC : Direction.fromString(directionStr);

		AdminSearch adminSearch = (AdminSearch) model.asMap().get(WebAdminConstants.ModelAttribute.SEARCH);

		Admin admin = new Admin();
		admin.setId(adminSearch.getIdSearch());

		if (properties == null) {
			properties = new String[1];
			properties[0] = Admin.CREATED_DATE;
			direction = Sort.Direction.DESC;
		}

		Page<Admin> pagination = modelService.findPage(AdminSpecification.findByCriteria(admin),
				PageRequest.of(page <= 0 ? 0 : page - 1, size <= 0 ? 1 : size, direction, properties), Admin.class);

		model.addAttribute(WebConsoleConstants.Pagination.PROPERTIES, propertiesStr);
		model.addAttribute(WebConsoleConstants.Pagination.DIRECTION, directionStr);

		return pagination;
	}

	private RedirectAttributes setPaginationRedirectAttributes(RedirectAttributes redirectAttributes,
			@RequestParam Map<String, Object> requestParams, AdminSearch adminSearch) {
		int page = ParamUtils.parseInt(requestParams.get(WebConsoleConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(WebConsoleConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_ADMIN_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(WebConsoleConstants.Pagination.PROPERTIES));
		String directionStr = ParamUtils.parseString(requestParams.get(WebConsoleConstants.Pagination.DIRECTION));

		redirectAttributes.addAttribute(WebConsoleConstants.Pagination.PAGE, page);
		redirectAttributes.addAttribute(WebConsoleConstants.Pagination.SIZE, size);
		redirectAttributes.addAttribute(WebConsoleConstants.Pagination.PROPERTIES, propertiesStr);
		redirectAttributes.addAttribute(WebConsoleConstants.Pagination.DIRECTION, directionStr);
		redirectAttributes.addAttribute(AdminSearch.ID_SEARCH, adminSearch.getIdSearch());
		redirectAttributes.addFlashAttribute(WebAdminConstants.ModelAttribute.SEARCH, adminSearch);

		return redirectAttributes;
	}

	@ModelAttribute(WebAdminConstants.ModelAttribute.CREATE)
	public Admin populateAdminCreate(HttpServletRequest request) throws Exception {
		return modelService.create(Admin.class);
	}

	@ModelAttribute(WebAdminConstants.ModelAttribute.UPDATE)
	public Admin populateAdminForm(HttpServletRequest request) throws Exception {
		return modelService.create(Admin.class);
	}

	@ModelAttribute(WebAdminConstants.ModelAttribute.SEARCH)
	public AdminSearch populateAdminSearch(HttpServletRequest request) {
		return new AdminSearch();
	}

	@GetMapping(value = WebAdminConstants.Path.ADMIN)
	public String list(@ModelAttribute(WebAdminConstants.ModelAttribute.SEARCH) AdminSearch adminSearch,
			@ModelAttribute(WebAdminConstants.ModelAttribute.UPDATE) Admin adminUpdate, Model model,
			@RequestParam Map<String, Object> requestParams) throws Exception {

		model.addAttribute(WebConsoleConstants.PAGINATION, getPagination(model, requestParams));

		if (adminUpdate.getUuid() != null) {
			Admin existingAdmin = modelService.findOneEntityByUuid(adminUpdate.getUuid(), Admin.class);

			if (existingAdmin != null) {
				model.addAttribute(WebAdminConstants.ModelAttribute.UPDATE, existingAdmin);
			} else {
				adminUpdate.setUuid(null);
				addErrorMessage(model, WebConsoleConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_ADMIN_LIST;
	}

	@PostMapping(value = WebAdminConstants.Path.ADMIN, params = "create")
	public RedirectView create(@ModelAttribute(WebAdminConstants.ModelAttribute.SEARCH) AdminSearch adminSearch,
			@ModelAttribute(WebAdminConstants.ModelAttribute.CREATE) Admin adminCreate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (adminCreate.getUuid() != null) {
			redirectAttributes.addFlashAttribute(WebConsoleConstants.Model.ERROR,
					"Create new record doesn't need UUID.");
		} else {
			try {
				modelService.saveDto(adminCreate, Admin.class);

				addSuccessMessage(redirectAttributes, WebConsoleConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(Admin.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(Admin.UUID, adminCreate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, adminSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_ADMIN);
		return redirectView;
	}

	@PostMapping(value = WebAdminConstants.Path.ADMIN, params = "update")
	public RedirectView update(@ModelAttribute(WebAdminConstants.ModelAttribute.SEARCH) AdminSearch adminSearch,
			@ModelAttribute(WebAdminConstants.ModelAttribute.UPDATE) Admin adminUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (adminUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(WebConsoleConstants.Model.ERROR,
					"Update record needed existing UUID.");
		} else {
			try {
				modelService.saveDto(adminUpdate, Admin.class);

				addSuccessMessage(redirectAttributes, WebConsoleConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(Admin.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(Admin.UUID, adminUpdate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, adminSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_ADMIN);
		return redirectView;
	}

	@PostMapping(value = WebAdminConstants.Path.ADMIN, params = "delete")
	public RedirectView delete(@ModelAttribute(WebAdminConstants.ModelAttribute.SEARCH) AdminSearch adminSearch,
			@ModelAttribute(WebAdminConstants.ModelAttribute.UPDATE) Admin adminUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		try {
			modelService.remove(adminUpdate.getUuid(), Admin.class);

			addSuccessMessage(redirectAttributes, WebConsoleConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(Admin.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addFlashAttribute(WebAdminConstants.ModelAttribute.UPDATE, adminUpdate);
		}

		setPaginationRedirectAttributes(redirectAttributes, requestParams, adminSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_ADMIN);
		return redirectView;

	}
}
