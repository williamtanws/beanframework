package com.beanframework.backoffice.web;

import java.util.Map;

import org.flowable.task.api.Task;
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
import com.beanframework.backoffice.TaskWebConstants;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.facade.TaskFacade;
import com.beanframework.core.facade.TaskFacade.TaskPreAuthorizeEnum;

@Controller
public class TaskController extends AbstractController {

	@Autowired
	private TaskFacade taskFacade;

	@Value(TaskWebConstants.Path.TASK)
	private String PATH_TASK;

	@Value(TaskWebConstants.View.LIST)
	private String VIEW_TASK_LIST;

	@PreAuthorize(TaskPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = TaskWebConstants.Path.TASK)
	public String list(@ModelAttribute(TaskWebConstants.ModelAttribute.TASK_DTO) Task task, @RequestParam Map<String, Object> requestParams, Model model) throws Exception {
		model.addAttribute("create", false);

		if (requestParams.get("id") != null) {

			Task existsDto = taskFacade.findOneById((String) requestParams.get("id"));

			if (existsDto != null) {
				model.addAttribute(TaskWebConstants.ModelAttribute.TASK_DTO, existsDto);
			} else {
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_TASK_LIST;
	}

	@PreAuthorize(TaskPreAuthorizeEnum.HAS_UPDATE)
	@PostMapping(value = TaskWebConstants.Path.TASK, params = "update")
	public RedirectView update(@ModelAttribute(TaskWebConstants.ModelAttribute.TASK_DTO) Task task, @RequestParam Map<String, Object> requestParams, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes)
			throws Exception {

		if (task.getId() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing ID.");
		} else {

			try {
				String taskId = (String) requestParams.get("id");
				taskFacade.complete(taskId, null);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(Task.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute("id", task.getId());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_TASK);
		return redirectView;
	}
}
