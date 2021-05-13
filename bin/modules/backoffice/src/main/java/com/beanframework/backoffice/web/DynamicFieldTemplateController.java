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
import com.beanframework.backoffice.DynamicFieldTemplateWebConstants;
import com.beanframework.backoffice.DynamicFieldTemplateWebConstants.DynamicFieldTemplatePreAuthorizeEnum;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.controller.AbstractController;
import com.beanframework.core.data.DynamicFieldTemplateDto;
import com.beanframework.core.facade.DynamicFieldTemplateFacade;


@Controller
public class DynamicFieldTemplateController extends AbstractController {

  @Autowired
  private DynamicFieldTemplateFacade dynamicFieldTemplateFacade;

  @Value(DynamicFieldTemplateWebConstants.Path.DYNAMICFIELDTEMPLATE)
  private String PATH_DYNAMICFIELDTEMPLATE;

  @Value(DynamicFieldTemplateWebConstants.Path.DYNAMICFIELDTEMPLATE_FORM)
  private String PATH_DYNAMICFIELDTEMPLATE_FORM;

  @Value(DynamicFieldTemplateWebConstants.View.DYNAMICFIELDTEMPLATE)
  private String VIEW_DYNAMICFIELDTEMPLATE;

  @Value(DynamicFieldTemplateWebConstants.View.DYNAMICFIELDTEMPLATE_FORM)
  private String VIEW_DYNAMICFIELDTEMPLATE_FORM;

  @PreAuthorize(DynamicFieldTemplatePreAuthorizeEnum.HAS_READ)
  @GetMapping(value = DynamicFieldTemplateWebConstants.Path.DYNAMICFIELDTEMPLATE)
  public String page(
      @Valid @ModelAttribute(DynamicFieldTemplateWebConstants.ModelAttribute.DYNAMICFIELDTEMPLATE_DTO) DynamicFieldTemplateDto dynamicFieldTemplateDto,
      Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
    return VIEW_DYNAMICFIELDTEMPLATE;
  }

  @PreAuthorize(DynamicFieldTemplatePreAuthorizeEnum.HAS_READ)
  @GetMapping(value = DynamicFieldTemplateWebConstants.Path.DYNAMICFIELDTEMPLATE_FORM)
  public String form(
      @Valid @ModelAttribute(DynamicFieldTemplateWebConstants.ModelAttribute.DYNAMICFIELDTEMPLATE_DTO) DynamicFieldTemplateDto dynamicFieldTemplateDto,
      Model model) throws Exception {

    if (dynamicFieldTemplateDto.getUuid() != null) {
      dynamicFieldTemplateDto =
          dynamicFieldTemplateFacade.findOneByUuid(dynamicFieldTemplateDto.getUuid());
    } else {
      dynamicFieldTemplateDto = dynamicFieldTemplateFacade.createDto();
    }
    model.addAttribute(DynamicFieldTemplateWebConstants.ModelAttribute.DYNAMICFIELDTEMPLATE_DTO,
        dynamicFieldTemplateDto);

    return VIEW_DYNAMICFIELDTEMPLATE_FORM;
  }

  @PreAuthorize(DynamicFieldTemplatePreAuthorizeEnum.HAS_CREATE)
  @PostMapping(value = DynamicFieldTemplateWebConstants.Path.DYNAMICFIELDTEMPLATE_FORM,
      params = "create")
  public RedirectView create(
      @Valid @ModelAttribute(DynamicFieldTemplateWebConstants.ModelAttribute.DYNAMICFIELDTEMPLATE_DTO) DynamicFieldTemplateDto dynamicFieldTemplateDto,
      Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
      RedirectAttributes redirectAttributes) {

    if (dynamicFieldTemplateDto.getUuid() != null) {
      redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
          "Create new record doesn't required UUID.");
    } else {
      try {
        dynamicFieldTemplateDto = dynamicFieldTemplateFacade.create(dynamicFieldTemplateDto);

        addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
      } catch (BusinessException e) {
        addErrorMessage(DynamicFieldTemplateDto.class, e.getMessage(), bindingResult,
            redirectAttributes);
      }
    }

    redirectAttributes.addAttribute(DynamicFieldTemplateDto.UUID,
        dynamicFieldTemplateDto.getUuid());

    RedirectView redirectView = new RedirectView();
    redirectView.setContextRelative(true);
    redirectView.setUrl(PATH_DYNAMICFIELDTEMPLATE_FORM);
    return redirectView;
  }

  @PreAuthorize(DynamicFieldTemplatePreAuthorizeEnum.HAS_UPDATE)
  @PostMapping(value = DynamicFieldTemplateWebConstants.Path.DYNAMICFIELDTEMPLATE_FORM,
      params = "update")
  public RedirectView update(
      @Valid @ModelAttribute(DynamicFieldTemplateWebConstants.ModelAttribute.DYNAMICFIELDTEMPLATE_DTO) DynamicFieldTemplateDto dynamicFieldTemplateDto,
      Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
      RedirectAttributes redirectAttributes) {

    if (dynamicFieldTemplateDto.getUuid() == null) {
      redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
          "Update record required existing UUID.");
    } else {
      try {
        dynamicFieldTemplateDto = dynamicFieldTemplateFacade.update(dynamicFieldTemplateDto);

        addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
      } catch (BusinessException e) {
        addErrorMessage(DynamicFieldTemplateDto.class, e.getMessage(), bindingResult,
            redirectAttributes);
      }
    }

    redirectAttributes.addAttribute(DynamicFieldTemplateDto.UUID,
        dynamicFieldTemplateDto.getUuid());

    RedirectView redirectView = new RedirectView();
    redirectView.setContextRelative(true);
    redirectView.setUrl(PATH_DYNAMICFIELDTEMPLATE_FORM);
    return redirectView;
  }

  @PreAuthorize(DynamicFieldTemplatePreAuthorizeEnum.HAS_DELETE)
  @PostMapping(value = DynamicFieldTemplateWebConstants.Path.DYNAMICFIELDTEMPLATE_FORM,
      params = "delete")
  public RedirectView delete(
      @Valid @ModelAttribute(DynamicFieldTemplateWebConstants.ModelAttribute.DYNAMICFIELDTEMPLATE_DTO) DynamicFieldTemplateDto dynamicFieldTemplateDto,
      Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
      RedirectAttributes redirectAttributes) {

    if (dynamicFieldTemplateDto.getUuid() == null) {
      redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
          "Delete record required existing UUID.");
    } else {
      try {
        dynamicFieldTemplateFacade.delete(dynamicFieldTemplateDto.getUuid());

        addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
      } catch (BusinessException e) {
        redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
        redirectAttributes.addAttribute(DynamicFieldTemplateDto.UUID,
            dynamicFieldTemplateDto.getUuid());
      }
    }

    RedirectView redirectView = new RedirectView();
    redirectView.setContextRelative(true);
    redirectView.setUrl(PATH_DYNAMICFIELDTEMPLATE_FORM);
    return redirectView;

  }
}
