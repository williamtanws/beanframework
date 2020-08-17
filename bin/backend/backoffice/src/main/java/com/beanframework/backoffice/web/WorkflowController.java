package com.beanframework.backoffice.web;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.WorkflowWebConstants;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.WorkflowDto;
import com.beanframework.core.facade.WorkflowFacade;
import com.beanframework.core.facade.WorkflowFacade.WorkflowPreAuthorizeEnum;

@Controller
public class WorkflowController extends AbstractController {

	@Autowired
	private WorkflowFacade workflowFacade;

	@Value(WorkflowWebConstants.Path.WORKFLOW)
	private String PATH_WORKFLOW;

	@Value(WorkflowWebConstants.View.LIST)
	private String VIEW_WORKFLOW_LIST;

	@PreAuthorize(WorkflowPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = WorkflowWebConstants.Path.WORKFLOW)
	public String list(@Valid @ModelAttribute(WorkflowWebConstants.ModelAttribute.WORKFLOW_DTO) WorkflowDto workflowDto, Model model) throws Exception {
		model.addAttribute("create", false);

		if (workflowDto.getUuid() != null) {

			WorkflowDto existsDto = workflowFacade.findOneByUuid(workflowDto.getUuid());

			if (existsDto != null) {
				model.addAttribute(WorkflowWebConstants.ModelAttribute.WORKFLOW_DTO, existsDto);
			} else {
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_WORKFLOW_LIST;
	}

	@PreAuthorize(WorkflowPreAuthorizeEnum.HAS_CREATE)
	@GetMapping(value = WorkflowWebConstants.Path.WORKFLOW, params = "create")
	public String createView(@Valid @ModelAttribute(WorkflowWebConstants.ModelAttribute.WORKFLOW_DTO) WorkflowDto workflowDto, Model model) throws Exception {

		workflowDto = workflowFacade.createDto();
		model.addAttribute(WorkflowWebConstants.ModelAttribute.WORKFLOW_DTO, workflowDto);
		model.addAttribute("create", true);

		return VIEW_WORKFLOW_LIST;
	}

	@PreAuthorize(WorkflowPreAuthorizeEnum.HAS_CREATE)
	@PostMapping(value = WorkflowWebConstants.Path.WORKFLOW, params = "create")
	public RedirectView create(@Valid @ModelAttribute(WorkflowWebConstants.ModelAttribute.WORKFLOW_DTO) WorkflowDto workflowDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes)
			throws Exception {

		if (workflowDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
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
		redirectView.setUrl(PATH_WORKFLOW);
		return redirectView;
	}

	@PreAuthorize(WorkflowPreAuthorizeEnum.HAS_UPDATE)
	@PostMapping(value = WorkflowWebConstants.Path.WORKFLOW, params = "update")
	public RedirectView update(@Valid @ModelAttribute(WorkflowWebConstants.ModelAttribute.WORKFLOW_DTO) WorkflowDto workflowDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes)
			throws Exception {

		if (workflowDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
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
		redirectView.setUrl(PATH_WORKFLOW);
		return redirectView;
	}

	@PreAuthorize(WorkflowPreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = WorkflowWebConstants.Path.WORKFLOW, params = "delete")
	public RedirectView delete(@Valid @ModelAttribute(WorkflowWebConstants.ModelAttribute.WORKFLOW_DTO) WorkflowDto workflowDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

		try {
			workflowFacade.delete(workflowDto.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(WorkflowDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addAttribute(WorkflowDto.UUID, workflowDto.getUuid());
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_WORKFLOW);
		return redirectView;

	}
}
