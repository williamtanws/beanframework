package com.beanframework.backoffice.web;

import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import com.beanframework.backoffice.ProcessDefinitionWebConstants;
import com.beanframework.backoffice.ProcessDefinitionWebConstants.ProcessDefinitionPreAuthorizeEnum;
import com.beanframework.core.config.dto.ProcessDefinitionDto;
import com.beanframework.core.controller.AbstractController;
import com.beanframework.core.facade.ProcessDefinitionFacade;

@Controller
public class ProcessDefinitionController extends AbstractController {

  @Autowired
  private ProcessDefinitionFacade processdefinitionFacade;

  @Value(ProcessDefinitionWebConstants.View.PROCESSDEFINITION)
  private String VIEW_PROCESSDEFINITION;

  @Value(ProcessDefinitionWebConstants.View.PROCESSDEFINITION_FORM)
  private String VIEW_PROCESSDEFINITION_FORM;

  @PreAuthorize(ProcessDefinitionPreAuthorizeEnum.HAS_READ)
  @GetMapping(value = ProcessDefinitionWebConstants.Path.PROCESSDEFINITION)
  public String page(
      @Valid @ModelAttribute(ProcessDefinitionWebConstants.ModelAttribute.PROCESSDEFINITION) ProcessDefinitionDto processdefinition,
      Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
    return VIEW_PROCESSDEFINITION;
  }

  @PreAuthorize(ProcessDefinitionPreAuthorizeEnum.HAS_READ)
  @GetMapping(value = ProcessDefinitionWebConstants.Path.PROCESSDEFINITION_FORM)
  public String form(
      @Valid @ModelAttribute(ProcessDefinitionWebConstants.ModelAttribute.PROCESSDEFINITION) ProcessDefinitionDto processdefinition,
      Model model) throws Exception {

    processdefinition = processdefinitionFacade.findOneById(processdefinition.getId());
    model.addAttribute(ProcessDefinitionWebConstants.ModelAttribute.PROCESSDEFINITION,
        processdefinition);

    return VIEW_PROCESSDEFINITION_FORM;
  }
}
