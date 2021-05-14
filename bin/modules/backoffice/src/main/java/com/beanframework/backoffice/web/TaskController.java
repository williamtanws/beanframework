package com.beanframework.backoffice.web;

import java.util.Map;
import javax.validation.Valid;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import com.beanframework.backoffice.TaskWebConstants;
import com.beanframework.backoffice.TaskWebConstants.TaskPreAuthorizeEnum;
import com.beanframework.core.controller.AbstractController;
import com.beanframework.core.facade.TaskFacade;

@Controller
public class TaskController extends AbstractController {

  @Autowired
  private TaskFacade taskFacade;

  @Value(TaskWebConstants.View.TASK)
  private String VIEW_TASK;

  @Value(TaskWebConstants.View.TASK_FORM)
  private String VIEW_TASK_FORM;

  @PreAuthorize(TaskPreAuthorizeEnum.HAS_READ)
  @GetMapping(value = TaskWebConstants.Path.TASK)
  public String page(@Valid @ModelAttribute(TaskWebConstants.ModelAttribute.TASK) Task task,
      Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
    return VIEW_TASK;
  }

  @PreAuthorize(TaskPreAuthorizeEnum.HAS_READ)
  @GetMapping(value = TaskWebConstants.Path.TASK_FORM)
  public String form(@Valid @ModelAttribute(TaskWebConstants.ModelAttribute.TASK) Task task,
      Model model) throws Exception {

    task = taskFacade.findOneById(task.getId());
    model.addAttribute(TaskWebConstants.ModelAttribute.TASK, task);

    return VIEW_TASK_FORM;
  }
}
