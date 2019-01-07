package com.beanframework.backoffice.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import com.beanframework.backoffice.CronjobWebConstants;
import com.beanframework.backoffice.data.CronjobSearch;
import com.beanframework.backoffice.data.CronjobSpecification;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.utils.ParamUtils;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.domain.CronjobData;
import com.beanframework.cronjob.domain.CronjobEnum;
import com.beanframework.cronjob.service.CronjobFacade;
import com.beanframework.cronjob.service.CronjobManagerService;

@Controller
public class CronjobController extends AbstractController {

	@Autowired
	private CronjobFacade cronjobFacade;

	@Autowired
	private CronjobManagerService cronjobManagerService;

	@Value(CronjobWebConstants.Path.CRONJOB)
	private String PATH_CRONJOB;

	@Value(CronjobWebConstants.View.LIST)
	private String VIEW_CRONJOB_LIST;

	@Value(CronjobWebConstants.LIST_SIZE)
	private int MODULE_CRONJOB_LIST_SIZE;

	private Page<Cronjob> getPagination(CronjobSearch cronjobSearch, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		int page = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_CRONJOB_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.PROPERTIES));
		String[] properties = StringUtils.isBlank(propertiesStr) ? null : propertiesStr.split(BackofficeWebConstants.Pagination.PROPERTIES_SPLIT);

		String directionStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.DIRECTION));
		Direction direction = StringUtils.isBlank(directionStr) ? Direction.ASC : Direction.fromString(directionStr);

		if (properties == null) {
			properties = new String[1];
			properties[0] = Cronjob.CREATED_DATE;
			direction = Sort.Direction.DESC;
		}

		Page<Cronjob> pagination = cronjobFacade.findPage(CronjobSpecification.findByCriteria(cronjobSearch), PageRequest.of(page <= 0 ? 0 : page - 1, size <= 0 ? 1 : size, direction, properties));

		model.addAttribute(BackofficeWebConstants.Pagination.PROPERTIES, propertiesStr);
		model.addAttribute(BackofficeWebConstants.Pagination.DIRECTION, directionStr);

		return pagination;
	}

	private RedirectAttributes setPaginationRedirectAttributes(RedirectAttributes redirectAttributes, @RequestParam Map<String, Object> requestParams, CronjobSearch cronjobSearch) {

		cronjobSearch.setSearchAll((String) requestParams.get("cronjobSearch.searchAll"));
		cronjobSearch.setId((String) requestParams.get("cronjobSearch.id"));
		cronjobSearch.setJobName((String) requestParams.get("cronjobSearch.jobName"));
		String startup = (String) requestParams.get("cronjobSearch.startup");
		if (StringUtils.isNotBlank(startup)) {
			if (startup.equals("1")) {
				cronjobSearch.setStartup(Boolean.TRUE);
			}
			if (startup.equals("0")) {
				cronjobSearch.setStartup(Boolean.FALSE);
			}
		}
		String status = (String) requestParams.get("cronjobSearch.status");
		if (StringUtils.isNotBlank(status)) {
			cronjobSearch.setStatus(CronjobEnum.Status.valueOf(status));
		}
		String result = (String) requestParams.get("cronjobSearch.result");
		if (StringUtils.isNotBlank(result)) {
			cronjobSearch.setResult(CronjobEnum.Result.valueOf(result));
		}

		int page = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_CRONJOB_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.PROPERTIES));
		String directionStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.DIRECTION));

		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.PAGE, page);
		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.SIZE, size);
		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.PROPERTIES, propertiesStr);
		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.DIRECTION, directionStr);
		redirectAttributes.addAttribute("searchAll", cronjobSearch.getSearchAll());
		redirectAttributes.addAttribute("id", cronjobSearch.getId());
		redirectAttributes.addAttribute("jobName", cronjobSearch.getJobName());
		redirectAttributes.addAttribute("startup", cronjobSearch.getStartup());
		redirectAttributes.addAttribute("status", cronjobSearch.getStatus());
		redirectAttributes.addAttribute("result", cronjobSearch.getResult());

		return redirectAttributes;
	}

	@ModelAttribute(CronjobWebConstants.ModelAttribute.CREATE)
	public Cronjob populateCronjobCreate(HttpServletRequest request) throws Exception {
		return cronjobFacade.create();
	}

	@ModelAttribute(CronjobWebConstants.ModelAttribute.UPDATE)
	public Cronjob populateCronjobForm(HttpServletRequest request) throws Exception {
		return cronjobFacade.create();
	}

	@ModelAttribute(CronjobWebConstants.ModelAttribute.SEARCH)
	public CronjobSearch populateCronjobSearch(HttpServletRequest request) {
		return new CronjobSearch();
	}

	@GetMapping(value = CronjobWebConstants.Path.CRONJOB)
	public String list(@ModelAttribute(CronjobWebConstants.ModelAttribute.SEARCH) CronjobSearch cronjobSearch, @ModelAttribute(CronjobWebConstants.ModelAttribute.UPDATE) Cronjob cronjobUpdate,
			Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		model.addAttribute(BackofficeWebConstants.PAGINATION, getPagination(cronjobSearch, model, requestParams));

		if (cronjobUpdate.getUuid() != null) {

			Cronjob existingCronjob = cronjobFacade.findOneDtoByUuid(cronjobUpdate.getUuid());

			if (existingCronjob != null) {
				
				List<Object[]> revisions = cronjobFacade.findHistoryByUuid(cronjobUpdate.getUuid(), null, null);
				model.addAttribute(BackofficeWebConstants.Model.REVISIONS, revisions);
				
				model.addAttribute(CronjobWebConstants.ModelAttribute.UPDATE, existingCronjob);
			} else {
				cronjobUpdate.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_CRONJOB_LIST;
	}

	@PostMapping(value = CronjobWebConstants.Path.CRONJOB, params = "create")
	public RedirectView create(@ModelAttribute(CronjobWebConstants.ModelAttribute.SEARCH) CronjobSearch cronjobSearch, @ModelAttribute(CronjobWebConstants.ModelAttribute.CREATE) Cronjob cronjobCreate,
			Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (cronjobCreate.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {
			try {
				cronjobCreate = cronjobFacade.createDto(cronjobCreate);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(Cronjob.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(Cronjob.UUID, cronjobCreate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, cronjobSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CRONJOB);
		return redirectView;
	}

	@PostMapping(value = CronjobWebConstants.Path.CRONJOB, params = "update")
	public RedirectView update(@ModelAttribute(CronjobWebConstants.ModelAttribute.SEARCH) CronjobSearch cronjobSearch, @ModelAttribute(CronjobWebConstants.ModelAttribute.UPDATE) Cronjob cronjobUpdate,
			Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) throws Exception {

		if (cronjobUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			try {
				cronjobUpdate = cronjobFacade.updateDto(cronjobUpdate);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(Cronjob.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(Cronjob.UUID, cronjobUpdate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, cronjobSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CRONJOB);
		return redirectView;
	}

	@PostMapping(value = CronjobWebConstants.Path.CRONJOB, params = "delete")
	public RedirectView delete(@ModelAttribute(CronjobWebConstants.ModelAttribute.SEARCH) CronjobSearch cronjobSearch, @ModelAttribute(CronjobWebConstants.ModelAttribute.UPDATE) Cronjob cronjobUpdate,
			Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		try {
			cronjobManagerService.deleteJobByUuid(cronjobUpdate.getUuid());
			cronjobFacade.delete(cronjobUpdate.getUuid());
			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(Cronjob.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addFlashAttribute(CronjobWebConstants.ModelAttribute.UPDATE, cronjobUpdate);
		}

		setPaginationRedirectAttributes(redirectAttributes, requestParams, cronjobSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CRONJOB);
		return redirectView;

	}

	@PostMapping(value = CronjobWebConstants.Path.CRONJOB, params = "trigger")
	public RedirectView trigger(@ModelAttribute(CronjobWebConstants.ModelAttribute.SEARCH) CronjobSearch cronjobSearch,
			@ModelAttribute(CronjobWebConstants.ModelAttribute.UPDATE) Cronjob cronjobUpdate, Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) throws ParseException {

		if (cronjobUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			String triggerStartDate = (String) requestParams.get("jobTriggerStartDate");
			if (StringUtils.isNotBlank(triggerStartDate)) {
				Date date = new SimpleDateFormat("MM/dd/yyyy h:mm a").parse(triggerStartDate);
				cronjobUpdate.setTriggerStartDate(date);
			}

			try {
				cronjobFacade.trigger(cronjobUpdate);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(Cronjob.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(Cronjob.UUID, cronjobUpdate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, cronjobSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CRONJOB);
		return redirectView;
	}

	@PostMapping(value = CronjobWebConstants.Path.CRONJOB, params = "createjobdata")
	public RedirectView createjobdata(@ModelAttribute(CronjobWebConstants.ModelAttribute.SEARCH) CronjobSearch cronjobSearch,
			@ModelAttribute(CronjobWebConstants.ModelAttribute.UPDATE) Cronjob cronjobUpdate, Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) throws Exception {

		if (cronjobUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			String cronjobDataName = (String) requestParams.get("cronjobDataName");
			String cronjobDataValue = (String) requestParams.get("cronjobDataValue");

			CronjobData cronjobData = new CronjobData();
			cronjobData.setName(cronjobDataName);
			cronjobData.setValue(cronjobDataValue);

			try {
				cronjobFacade.updateDtoCronjobData(cronjobUpdate.getUuid(), cronjobData);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(Cronjob.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(Cronjob.UUID, cronjobUpdate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, cronjobSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CRONJOB);
		return redirectView;
	}

	@PostMapping(value = CronjobWebConstants.Path.CRONJOB, params = "deletejobdata")
	public RedirectView deletejobdata(@ModelAttribute(CronjobWebConstants.ModelAttribute.SEARCH) CronjobSearch cronjobSearch,
			@ModelAttribute(CronjobWebConstants.ModelAttribute.CREATE) Cronjob cronjobUpdate, Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) throws Exception {

		if (cronjobUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			UUID jobDataUuidToDelete = UUID.fromString((String) requestParams.get("jobDataUuid"));

			try {
				cronjobFacade.removeDtoCronjobData(cronjobUpdate.getUuid(), jobDataUuidToDelete);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(Cronjob.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(Cronjob.UUID, cronjobUpdate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, cronjobSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CRONJOB);
		return redirectView;
	}
}
