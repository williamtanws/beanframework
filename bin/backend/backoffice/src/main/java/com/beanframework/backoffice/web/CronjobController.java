package com.beanframework.backoffice.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import com.beanframework.backoffice.WebCronjobConstants;
import com.beanframework.backoffice.domain.CronjobSearch;
import com.beanframework.common.controller.AbstractCommonController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.common.utils.ParamUtils;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.domain.CronjobData;
import com.beanframework.cronjob.domain.CronjobSpecification;
import com.beanframework.cronjob.service.CronjobFacade;
import com.beanframework.cronjob.service.CronjobManagerService;

@Controller
public class CronjobController extends AbstractCommonController {

	@Autowired
	private ModelService modelService;

	@Autowired
	private CronjobFacade cronjobFacade;

	@Autowired
	private CronjobManagerService cronjobManagerService;

	@Value(WebCronjobConstants.Path.CRONJOB)
	private String PATH_CRONJOB;

	@Value(WebCronjobConstants.View.LIST)
	private String VIEW_CRONJOB_LIST;

	@Value(WebCronjobConstants.LIST_SIZE)
	private int MODULE_CRONJOB_LIST_SIZE;

	private Page<Cronjob> getPagination(Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		int page = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_CRONJOB_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.PROPERTIES));
		String[] properties = StringUtils.isEmpty(propertiesStr) ? null
				: propertiesStr.split(WebBackofficeConstants.Pagination.PROPERTIES_SPLIT);

		String directionStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.DIRECTION));
		Direction direction = StringUtils.isEmpty(directionStr) ? Direction.ASC : Direction.fromString(directionStr);

		CronjobSearch cronjobSearch = (CronjobSearch) model.asMap().get(WebCronjobConstants.ModelAttribute.SEARCH);

		Cronjob cronjob = new Cronjob();
		cronjob.setId(cronjobSearch.getIdSearch());

		if (properties == null) {
			properties = new String[1];
			properties[0] = Cronjob.CREATED_DATE;
			direction = Sort.Direction.DESC;
		}

		Page<Cronjob> pagination = modelService.findPage(CronjobSpecification.findByCriteria(cronjob),
				PageRequest.of(page <= 0 ? 0 : page - 1, size <= 0 ? 1 : size, direction, properties), Cronjob.class);

		model.addAttribute(WebBackofficeConstants.Pagination.PROPERTIES, propertiesStr);
		model.addAttribute(WebBackofficeConstants.Pagination.DIRECTION, directionStr);

		return pagination;
	}

	private RedirectAttributes setPaginationRedirectAttributes(RedirectAttributes redirectAttributes,
			@RequestParam Map<String, Object> requestParams, CronjobSearch cronjobSearch) {
		int page = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_CRONJOB_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.PROPERTIES));
		String directionStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.DIRECTION));

		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.PAGE, page);
		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.SIZE, size);
		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.PROPERTIES, propertiesStr);
		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.DIRECTION, directionStr);
		redirectAttributes.addAttribute(CronjobSearch.ID_SEARCH, cronjobSearch.getIdSearch());
		redirectAttributes.addFlashAttribute(WebCronjobConstants.ModelAttribute.SEARCH, cronjobSearch);

		return redirectAttributes;
	}

	@ModelAttribute(WebCronjobConstants.ModelAttribute.CREATE)
	public Cronjob populateCronjobCreate(HttpServletRequest request) throws Exception {
		return modelService.create(Cronjob.class);
	}

	@ModelAttribute(WebCronjobConstants.ModelAttribute.UPDATE)
	public Cronjob populateCronjobForm(HttpServletRequest request) throws Exception {
		return modelService.create(Cronjob.class);
	}

	@ModelAttribute(WebCronjobConstants.ModelAttribute.SEARCH)
	public CronjobSearch populateCronjobSearch(HttpServletRequest request) {
		return new CronjobSearch();
	}

	@PreAuthorize(WebCronjobConstants.PreAuthorize.READ)
	@GetMapping(value = WebCronjobConstants.Path.CRONJOB)
	public String list(@ModelAttribute(WebCronjobConstants.ModelAttribute.SEARCH) CronjobSearch cronjobSearch,
			@ModelAttribute(WebCronjobConstants.ModelAttribute.UPDATE) Cronjob cronjobUpdate, Model model,
			@RequestParam Map<String, Object> requestParams) throws Exception {

		model.addAttribute(WebBackofficeConstants.PAGINATION, getPagination(model, requestParams));

		if (cronjobUpdate.getUuid() != null) {

			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Cronjob.UUID, cronjobUpdate.getUuid());

			Cronjob existingCronjob = modelService.findOneDtoByProperties(properties, Cronjob.class);

			if (existingCronjob != null) {
				model.addAttribute(WebCronjobConstants.ModelAttribute.UPDATE, existingCronjob);
			} else {
				cronjobUpdate.setUuid(null);
				addErrorMessage(model, WebBackofficeConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_CRONJOB_LIST;
	}

	@PreAuthorize(WebCronjobConstants.PreAuthorize.CREATE)
	@PostMapping(value = WebCronjobConstants.Path.CRONJOB, params = "create")
	public RedirectView create(@ModelAttribute(WebCronjobConstants.ModelAttribute.SEARCH) CronjobSearch cronjobSearch,
			@ModelAttribute(WebCronjobConstants.ModelAttribute.CREATE) Cronjob cronjobCreate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (cronjobCreate.getUuid() != null) {
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR,
					"Create new record doesn't need UUID.");
		} else {
			try {
				modelService.saveDto(cronjobCreate, Cronjob.class);

				addSuccessMessage(redirectAttributes, WebBackofficeConstants.Locale.SAVE_SUCCESS);
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

	@PreAuthorize(WebCronjobConstants.PreAuthorize.UPDATE)
	@PostMapping(value = WebCronjobConstants.Path.CRONJOB, params = "update")
	public RedirectView update(@ModelAttribute(WebCronjobConstants.ModelAttribute.SEARCH) CronjobSearch cronjobSearch,
			@ModelAttribute(WebCronjobConstants.ModelAttribute.UPDATE) Cronjob cronjobUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) throws Exception {

		if (cronjobUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR,
					"Update record needed existing UUID.");
		} else {

			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Cronjob.UUID, cronjobUpdate.getUuid());

			Cronjob cronjob = modelService.findOneDtoByProperties(properties, Cronjob.class);

			cronjobUpdate.setCronjobDatas(cronjob.getCronjobDatas());

			try {
				modelService.saveDto(cronjobUpdate, Cronjob.class);

				addSuccessMessage(redirectAttributes, WebBackofficeConstants.Locale.SAVE_SUCCESS);
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

	@PreAuthorize(WebCronjobConstants.PreAuthorize.UPDATE)
	@PostMapping(value = WebCronjobConstants.Path.CRONJOB, params = "trigger")
	public RedirectView trigger(@ModelAttribute(WebCronjobConstants.ModelAttribute.SEARCH) CronjobSearch cronjobSearch,
			@ModelAttribute(WebCronjobConstants.ModelAttribute.UPDATE) Cronjob cronjobUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) throws ParseException {

		if (cronjobUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR,
					"Update record needed existing UUID.");
		} else {

			String triggerStartDate = (String) requestParams.get("jobTriggerStartDate");
			if (StringUtils.isNotEmpty(triggerStartDate)) {
				Date date = new SimpleDateFormat("MM/dd/yyyy h:mm a").parse(triggerStartDate);
				cronjobUpdate.setTriggerStartDate(date);
			}

			try {
				cronjobFacade.trigger(cronjobUpdate);

				addSuccessMessage(redirectAttributes, WebBackofficeConstants.Locale.SAVE_SUCCESS);
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

	@PreAuthorize(WebCronjobConstants.PreAuthorize.UPDATE)
	@PostMapping(value = WebCronjobConstants.Path.CRONJOB, params = "createjobdata")
	public RedirectView createjobdata(
			@ModelAttribute(WebCronjobConstants.ModelAttribute.SEARCH) CronjobSearch cronjobSearch,
			@ModelAttribute(WebCronjobConstants.ModelAttribute.UPDATE) Cronjob cronjobUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) throws Exception {

		if (cronjobUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR,
					"Update record needed existing UUID.");
		} else {

			String cronjobDataName = (String) requestParams.get("cronjobDataName");
			String cronjobDataValue = (String) requestParams.get("cronjobDataValue");

			CronjobData cronjobData = new CronjobData();
			cronjobData.setName(cronjobDataName);
			cronjobData.setValue(cronjobDataValue);

			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Cronjob.UUID, cronjobUpdate.getUuid());

			cronjobUpdate = modelService.findOneDtoByProperties(properties, Cronjob.class);

			cronjobUpdate.getCronjobDatas().add(cronjobData);

			try {
				modelService.saveDto(cronjobUpdate, Cronjob.class);

				addSuccessMessage(redirectAttributes, WebBackofficeConstants.Locale.SAVE_SUCCESS);
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

	@PreAuthorize(WebCronjobConstants.PreAuthorize.UPDATE)
	@PostMapping(value = WebCronjobConstants.Path.CRONJOB, params = "deletejobdata")
	public RedirectView deletejobdata(
			@ModelAttribute(WebCronjobConstants.ModelAttribute.SEARCH) CronjobSearch cronjobSearch,
			@ModelAttribute(WebCronjobConstants.ModelAttribute.CREATE) Cronjob cronjobUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) throws Exception {

		if (cronjobUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR,
					"Update record needed existing UUID.");
		} else {

			UUID jobDataUuidToDelete = UUID.fromString((String) requestParams.get("jobDataUuid"));

			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Cronjob.UUID, cronjobUpdate.getUuid());

			cronjobUpdate = modelService.findOneDtoByProperties(properties, Cronjob.class);

			for (int i = 0; i < cronjobUpdate.getCronjobDatas().size(); i++) {
				if (cronjobUpdate.getCronjobDatas().get(i).getUuid().equals(jobDataUuidToDelete)) {
					cronjobUpdate.getCronjobDatas().remove(i);
					break;
				}
			}

			try {
				modelService.saveDto(cronjobUpdate, Cronjob.class);

				addSuccessMessage(redirectAttributes, WebBackofficeConstants.Locale.SAVE_SUCCESS);
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

	@PreAuthorize(WebCronjobConstants.PreAuthorize.DELETE)
	@PostMapping(value = WebCronjobConstants.Path.CRONJOB, params = "delete")
	public RedirectView delete(@ModelAttribute(WebCronjobConstants.ModelAttribute.SEARCH) CronjobSearch cronjobSearch,
			@ModelAttribute(WebCronjobConstants.ModelAttribute.UPDATE) Cronjob cronjobUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		try {
			cronjobManagerService.deleteJobByUuid(cronjobUpdate.getUuid());

			addSuccessMessage(redirectAttributes, WebBackofficeConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(Cronjob.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addFlashAttribute(WebCronjobConstants.ModelAttribute.UPDATE, cronjobUpdate);
		}

		setPaginationRedirectAttributes(redirectAttributes, requestParams, cronjobSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CRONJOB);
		return redirectView;

	}
}
