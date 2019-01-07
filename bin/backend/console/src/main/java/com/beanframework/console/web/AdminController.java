package com.beanframework.console.web;

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

import com.beanframework.admin.domain.Admin;
import com.beanframework.admin.service.AdminFacade;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.utils.ParamUtils;
import com.beanframework.console.AdminWebConstants;
import com.beanframework.console.ConsoleWebConstants;
import com.beanframework.console.data.AdminSearch;
import com.beanframework.console.data.AdminSpecification;

@Controller
public class AdminController extends AbstractController {

	@Autowired
	private AdminFacade adminFacade;

	@Value(AdminWebConstants.Path.ADMIN)
	private String PATH_ADMIN;

	@Value(AdminWebConstants.View.LIST)
	private String VIEW_ADMIN_LIST;

	@Value(AdminWebConstants.LIST_SIZE)
	private int MODULE_ADMIN_LIST_SIZE;

	private Page<Admin> getPagination(AdminSearch adminSearch, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		int page = ParamUtils.parseInt(requestParams.get(ConsoleWebConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(ConsoleWebConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_ADMIN_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(ConsoleWebConstants.Pagination.PROPERTIES));
		String[] properties = StringUtils.isBlank(propertiesStr) ? null
				: propertiesStr.split(ConsoleWebConstants.Pagination.PROPERTIES_SPLIT);

		String directionStr = ParamUtils.parseString(requestParams.get(ConsoleWebConstants.Pagination.DIRECTION));
		Direction direction = StringUtils.isBlank(directionStr) ? Direction.ASC : Direction.fromString(directionStr);

		if (properties == null) {
			properties = new String[1];
			properties[0] = Admin.CREATED_DATE;
			direction = Sort.Direction.DESC;
		}

		Page<Admin> pagination = adminFacade.findDtoPage(AdminSpecification.findByCriteria(adminSearch),
				PageRequest.of(page <= 0 ? 0 : page - 1, size <= 0 ? 1 : size, direction, properties));

		model.addAttribute(ConsoleWebConstants.Pagination.PROPERTIES, propertiesStr);
		model.addAttribute(ConsoleWebConstants.Pagination.DIRECTION, directionStr);

		return pagination;
	}

	private RedirectAttributes setPaginationRedirectAttributes(RedirectAttributes redirectAttributes,
			@RequestParam Map<String, Object> requestParams, AdminSearch adminSearch) {
		
		adminSearch.setSearchAll((String)requestParams.get("adminSearch.searchAll"));
		adminSearch.setId((String)requestParams.get("adminSearch.id"));
		
		int page = ParamUtils.parseInt(requestParams.get(ConsoleWebConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(ConsoleWebConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_ADMIN_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(ConsoleWebConstants.Pagination.PROPERTIES));
		String directionStr = ParamUtils.parseString(requestParams.get(ConsoleWebConstants.Pagination.DIRECTION));

		redirectAttributes.addAttribute(ConsoleWebConstants.Pagination.PAGE, page);
		redirectAttributes.addAttribute(ConsoleWebConstants.Pagination.SIZE, size);
		redirectAttributes.addAttribute(ConsoleWebConstants.Pagination.PROPERTIES, propertiesStr);
		redirectAttributes.addAttribute(ConsoleWebConstants.Pagination.DIRECTION, directionStr);
		redirectAttributes.addAttribute("searchAll", adminSearch.getSearchAll());
		redirectAttributes.addAttribute("id", adminSearch.getId());

		return redirectAttributes;
	}

	@ModelAttribute(AdminWebConstants.ModelAttribute.CREATE)
	public Admin populateAdminCreate(HttpServletRequest request) throws Exception {
		return adminFacade.create();
	}

	@ModelAttribute(AdminWebConstants.ModelAttribute.UPDATE)
	public Admin populateAdminForm(HttpServletRequest request) throws Exception {
		return adminFacade.create();
	}

	@ModelAttribute(AdminWebConstants.ModelAttribute.SEARCH)
	public AdminSearch populateAdminSearch(HttpServletRequest request) {
		return new AdminSearch();
	}

	@GetMapping(value = AdminWebConstants.Path.ADMIN)
	public String list(@ModelAttribute(AdminWebConstants.ModelAttribute.SEARCH) AdminSearch adminSearch,
			@ModelAttribute(AdminWebConstants.ModelAttribute.UPDATE) Admin adminUpdate, Model model,
			@RequestParam Map<String, Object> requestParams) throws Exception {

		model.addAttribute(ConsoleWebConstants.PAGINATION, getPagination(adminSearch, model, requestParams));

		if (adminUpdate.getUuid() != null) {
			Admin existingAdmin = adminFacade.findOneDtoByUuid(adminUpdate.getUuid());

			if (existingAdmin != null) {
				
				List<Object[]> revisions = adminFacade.findHistoryByUuid(adminUpdate.getUuid(), null, null);
				model.addAttribute(ConsoleWebConstants.Model.REVISIONS, revisions);
				
				model.addAttribute(AdminWebConstants.ModelAttribute.UPDATE, existingAdmin);
			} else {
				adminUpdate.setUuid(null);
				addErrorMessage(model, ConsoleWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_ADMIN_LIST;
	}

	@PostMapping(value = AdminWebConstants.Path.ADMIN, params = "create")
	public RedirectView create(@ModelAttribute(AdminWebConstants.ModelAttribute.SEARCH) AdminSearch adminSearch,
			@ModelAttribute(AdminWebConstants.ModelAttribute.CREATE) Admin adminCreate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (adminCreate.getUuid() != null) {
			redirectAttributes.addFlashAttribute(ConsoleWebConstants.Model.ERROR,
					"Create new record doesn't need UUID.");
		} else {
			try {
				adminCreate = adminFacade.createDto(adminCreate);

				addSuccessMessage(redirectAttributes, ConsoleWebConstants.Locale.SAVE_SUCCESS);
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

	@PostMapping(value = AdminWebConstants.Path.ADMIN, params = "update")
	public RedirectView update(@ModelAttribute(AdminWebConstants.ModelAttribute.SEARCH) AdminSearch adminSearch,
			@ModelAttribute(AdminWebConstants.ModelAttribute.UPDATE) Admin adminUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (adminUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(ConsoleWebConstants.Model.ERROR,
					"Update record needed existing UUID.");
		} else {
			try {
				adminUpdate = adminFacade.saveDto(adminUpdate);

				addSuccessMessage(redirectAttributes, ConsoleWebConstants.Locale.SAVE_SUCCESS);
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

	@PostMapping(value = AdminWebConstants.Path.ADMIN, params = "delete")
	public RedirectView delete(@ModelAttribute(AdminWebConstants.ModelAttribute.SEARCH) AdminSearch adminSearch,
			@ModelAttribute(AdminWebConstants.ModelAttribute.UPDATE) Admin adminUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		try {
			adminFacade.delete(adminUpdate.getUuid());

			addSuccessMessage(redirectAttributes, ConsoleWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(Admin.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addFlashAttribute(AdminWebConstants.ModelAttribute.UPDATE, adminUpdate);
		}

		setPaginationRedirectAttributes(redirectAttributes, requestParams, adminSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_ADMIN);
		return redirectView;

	}
}
