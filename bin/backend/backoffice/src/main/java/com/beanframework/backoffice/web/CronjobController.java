package com.beanframework.backoffice.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.CronjobDataDto;
import com.beanframework.core.data.CronjobDto;
import com.beanframework.core.data.CustomerDto;
import com.beanframework.core.facade.CronjobFacade;
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
	
	@GetMapping(value = CronjobWebConstants.Path.CRONJOB)
	public String list(@ModelAttribute(CronjobWebConstants.ModelAttribute.CRONJOB_DTO) CronjobDto cronjobDto, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		model.addAttribute("create", false);
		
		if (cronjobDto.getUuid() != null) {

			CronjobDto existingCronjob = cronjobFacade.findOneByUuid(cronjobDto.getUuid());

			if (existingCronjob != null) {

				model.addAttribute(CronjobWebConstants.ModelAttribute.CRONJOB_DTO, existingCronjob);
			} else {
				cronjobDto.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_CRONJOB_LIST;
	}
	
	@GetMapping(value = CronjobWebConstants.Path.CRONJOB, params = "create")
	public String createView(@ModelAttribute(CronjobWebConstants.ModelAttribute.CRONJOB_DTO) CronjobDto cronjobDto, Model model) throws Exception {
		
		cronjobDto = cronjobFacade.createDto();
		model.addAttribute(CronjobWebConstants.ModelAttribute.CRONJOB_DTO, cronjobDto);
		model.addAttribute("create", true);
		
		return VIEW_CRONJOB_LIST;
	}

	@PostMapping(value = CronjobWebConstants.Path.CRONJOB, params = "create")
	public RedirectView create(@ModelAttribute(CronjobWebConstants.ModelAttribute.CRONJOB_DTO) CronjobDto cronjobDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (cronjobDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {
			try {
				cronjobDto = cronjobFacade.create(cronjobDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(CronjobDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(CronjobDto.UUID, cronjobDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CRONJOB);
		return redirectView;
	}

	@PostMapping(value = CronjobWebConstants.Path.CRONJOB, params = "update")
	public RedirectView update(@ModelAttribute(CronjobWebConstants.ModelAttribute.CRONJOB_DTO) CronjobDto cronjobDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) throws Exception {

		if (cronjobDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			try {
				cronjobDto = cronjobFacade.update(cronjobDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(CronjobDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(CronjobDto.UUID, cronjobDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CRONJOB);
		return redirectView;
	}

	@PostMapping(value = CronjobWebConstants.Path.CRONJOB, params = "delete")
	public RedirectView delete(@ModelAttribute(CronjobWebConstants.ModelAttribute.CRONJOB_DTO) CronjobDto cronjobDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		try {
			cronjobManagerService.deleteJobByUuid(cronjobDto.getUuid());
			cronjobFacade.delete(cronjobDto.getUuid());
			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(CronjobDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addFlashAttribute(CronjobWebConstants.ModelAttribute.CRONJOB_DTO, cronjobDto);
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CRONJOB);
		return redirectView;

	}

	@PostMapping(value = CronjobWebConstants.Path.CRONJOB, params = "trigger")
	public RedirectView trigger(@ModelAttribute(CronjobWebConstants.ModelAttribute.CRONJOB_DTO) CronjobDto cronjobDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) throws ParseException {

		if (cronjobDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			String triggerStartDate = (String) requestParams.get("jobTriggerStartDate");
			if (StringUtils.isNotBlank(triggerStartDate)) {
				Date date = new SimpleDateFormat("MM/dd/yyyy h:mm a").parse(triggerStartDate);
				cronjobDto.setTriggerStartDate(date);
			}

			try {
				cronjobFacade.trigger(cronjobDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(CronjobDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(CronjobDto.UUID, cronjobDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CRONJOB);
		return redirectView;
	}

	@PostMapping(value = CronjobWebConstants.Path.CRONJOB, params = "createjobdata")
	public RedirectView createjobdata(@ModelAttribute(CronjobWebConstants.ModelAttribute.CRONJOB_DTO) CronjobDto cronjobDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) throws Exception {

		if (cronjobDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			String cronjobDataName = (String) requestParams.get("cronjobDataName");
			String cronjobDataValue = (String) requestParams.get("cronjobDataValue");

			CronjobDataDto cronjobData = new CronjobDataDto();
			cronjobData.setName(cronjobDataName);
			cronjobData.setValue(cronjobDataValue);

			try {
				cronjobFacade.updateCronjobData(cronjobDto.getUuid(), cronjobData);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(CronjobDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(CronjobDto.UUID, cronjobDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CRONJOB);
		return redirectView;
	}

	@PostMapping(value = CronjobWebConstants.Path.CRONJOB, params = "deletejobdata")
	public RedirectView deletejobdata(@ModelAttribute(CronjobWebConstants.ModelAttribute.CRONJOB_DTO) CustomerDto cronjobDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) throws Exception {

		if (cronjobDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			UUID jobDataUuidToDelete = UUID.fromString((String) requestParams.get("jobDataUuid"));

			try {
				cronjobFacade.removeCronjobData(cronjobDto.getUuid(), jobDataUuidToDelete);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(CronjobDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(CronjobDto.UUID, cronjobDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CRONJOB);
		return redirectView;
	}
}
