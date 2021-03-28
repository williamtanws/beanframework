package com.beanframework.backoffice.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.WorkflowWebConstants;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.WorkflowDto;
import com.beanframework.core.facade.WorkflowFacade;

@PreAuthorize("isAuthenticated()")
@Controller
public class WorkflowController extends AbstractController {

	@Autowired
	private WorkflowFacade workflowFacade;
	
	@Value(WorkflowWebConstants.Path.WORKFLOW_PAGE)
	private String PATH_WORKFLOW_PAGE;
	
	@Value(WorkflowWebConstants.Path.WORKFLOW_FORM)
	private String PATH_WORKFLOW_FORM;

	@Value(WorkflowWebConstants.View.PAGE)
	private String VIEW_WORKFLOW_PAGE;

	@Value(WorkflowWebConstants.View.FORM)
	private String VIEW_WORKFLOW_FORM;

	@GetMapping(value = WorkflowWebConstants.Path.WORKFLOW_PAGE)
	public String list(@Valid @ModelAttribute(WorkflowWebConstants.ModelAttribute.WORKFLOW_DTO) WorkflowDto workflowDto, Model model, @RequestParam Map<String, Object> requestParams)
			throws Exception {
		return VIEW_WORKFLOW_PAGE;
	}

	@GetMapping(value = WorkflowWebConstants.Path.WORKFLOW_FORM)
	public String createView(@Valid @ModelAttribute(WorkflowWebConstants.ModelAttribute.WORKFLOW_DTO) WorkflowDto workflowDto, Model model) throws Exception {

		if (workflowDto.getUuid() != null) {
			workflowDto = workflowFacade.findOneByUuid(workflowDto.getUuid());
		} else {
			workflowDto = workflowFacade.createDto();
		}
		model.addAttribute(WorkflowWebConstants.ModelAttribute.WORKFLOW_DTO, workflowDto);

		return VIEW_WORKFLOW_FORM;
	}

	@PostMapping(value = WorkflowWebConstants.Path.WORKFLOW_FORM, params = "create")
	public RedirectView create(@Valid @ModelAttribute(WorkflowWebConstants.ModelAttribute.WORKFLOW_DTO) WorkflowDto workflowDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (workflowDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't required UUID.");
		} else {
			try {
				workflowDto = workflowFacade.create(workflowDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(WorkflowDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(WorkflowDto.UUID, workflowDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_WORKFLOW_FORM);
		return redirectView;
	}

	@PostMapping(value = WorkflowWebConstants.Path.WORKFLOW_FORM, params = "update")
	public RedirectView update(@Valid @ModelAttribute(WorkflowWebConstants.ModelAttribute.WORKFLOW_DTO) WorkflowDto workflowDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (workflowDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record required existing UUID.");
		} else {
			try {
				workflowDto = workflowFacade.update(workflowDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(WorkflowDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(WorkflowDto.UUID, workflowDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_WORKFLOW_FORM);
		return redirectView;
	}

	@PostMapping(value = WorkflowWebConstants.Path.WORKFLOW_FORM, params = "delete")
	public RedirectView delete(@Valid @ModelAttribute(WorkflowWebConstants.ModelAttribute.WORKFLOW_DTO) WorkflowDto workflowDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (workflowDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Delete record required existing UUID.");
		} else {
			try {
				workflowFacade.delete(workflowDto.getUuid());

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(WorkflowDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_WORKFLOW_FORM);
		return redirectView;

	}
}
