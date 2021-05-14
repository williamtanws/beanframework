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
import com.beanframework.backoffice.UserPermissionWebConstants;
import com.beanframework.backoffice.UserPermissionWebConstants.UserPermissionPreAuthorizeEnum;
import com.beanframework.common.data.GenericDto;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.controller.AbstractController;
import com.beanframework.core.data.UserPermissionDto;
import com.beanframework.core.facade.UserPermissionFacade;


@Controller
public class UserPermissionController extends AbstractController {

  @Autowired
  private UserPermissionFacade dynamicFieldFacade;

  @Value(UserPermissionWebConstants.Path.PERMISSION)
  private String PATH_PERMISSION;

  @Value(UserPermissionWebConstants.Path.PERMISSION_FORM)
  private String PATH_PERMISSION_FORM;

  @Value(UserPermissionWebConstants.View.PERMISSION)
  private String VIEW_PERMISSION;

  @Value(UserPermissionWebConstants.View.PERMISSION_FORM)
  private String VIEW_PERMISSION_FORM;

  @PreAuthorize(UserPermissionPreAuthorizeEnum.HAS_READ)
  @GetMapping(value = UserPermissionWebConstants.Path.PERMISSION)
  public String page(
      @Valid @ModelAttribute(UserPermissionWebConstants.ModelAttribute.PERMISSION_DTO) UserPermissionDto dynamicFieldDto,
      Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
    return VIEW_PERMISSION;
  }

  @PreAuthorize(UserPermissionPreAuthorizeEnum.HAS_READ)
  @GetMapping(value = UserPermissionWebConstants.Path.PERMISSION_FORM)
  public String form(
      @Valid @ModelAttribute(UserPermissionWebConstants.ModelAttribute.PERMISSION_DTO) UserPermissionDto dynamicFieldDto,
      Model model) throws Exception {

    if (dynamicFieldDto.getUuid() != null) {
      dynamicFieldDto = dynamicFieldFacade.findOneByUuid(dynamicFieldDto.getUuid());
    } else {
      dynamicFieldDto = dynamicFieldFacade.createDto();
    }
    model.addAttribute(UserPermissionWebConstants.ModelAttribute.PERMISSION_DTO, dynamicFieldDto);

    return VIEW_PERMISSION_FORM;
  }

  @PreAuthorize(UserPermissionPreAuthorizeEnum.HAS_CREATE)
  @PostMapping(value = UserPermissionWebConstants.Path.PERMISSION_FORM, params = "create")
  public RedirectView create(
      @Valid @ModelAttribute(UserPermissionWebConstants.ModelAttribute.PERMISSION_DTO) UserPermissionDto dynamicFieldDto,
      Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
      RedirectAttributes redirectAttributes) {

    if (dynamicFieldDto.getUuid() != null) {
      redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
          "Create new record doesn't required UUID.");
    } else {
      try {
        dynamicFieldDto = dynamicFieldFacade.create(dynamicFieldDto);

        addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
      } catch (BusinessException e) {
        addErrorMessage(UserPermissionDto.class, e.getMessage(), bindingResult, redirectAttributes);
      }
    }

    redirectAttributes.addAttribute(GenericDto.UUID, dynamicFieldDto.getUuid());

    RedirectView redirectView = new RedirectView();
    redirectView.setContextRelative(true);
    redirectView.setUrl(PATH_PERMISSION_FORM);
    return redirectView;
  }

  @PreAuthorize(UserPermissionPreAuthorizeEnum.HAS_UPDATE)
  @PostMapping(value = UserPermissionWebConstants.Path.PERMISSION_FORM, params = "update")
  public RedirectView update(
      @Valid @ModelAttribute(UserPermissionWebConstants.ModelAttribute.PERMISSION_DTO) UserPermissionDto dynamicFieldDto,
      Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
      RedirectAttributes redirectAttributes) {

    if (dynamicFieldDto.getUuid() == null) {
      redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
          "Update record required existing UUID.");
    } else {
      try {
        dynamicFieldDto = dynamicFieldFacade.update(dynamicFieldDto);

        addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
      } catch (BusinessException e) {
        addErrorMessage(UserPermissionDto.class, e.getMessage(), bindingResult, redirectAttributes);
      }
    }

    redirectAttributes.addAttribute(GenericDto.UUID, dynamicFieldDto.getUuid());

    RedirectView redirectView = new RedirectView();
    redirectView.setContextRelative(true);
    redirectView.setUrl(PATH_PERMISSION_FORM);
    return redirectView;
  }

  @PreAuthorize(UserPermissionPreAuthorizeEnum.HAS_DELETE)
  @PostMapping(value = UserPermissionWebConstants.Path.PERMISSION_FORM, params = "delete")
  public RedirectView delete(
      @Valid @ModelAttribute(UserPermissionWebConstants.ModelAttribute.PERMISSION_DTO) UserPermissionDto dto,
      Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
      RedirectAttributes redirectAttributes) {

    if (dto.getUuid() == null) {
      redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
          "Delete record required existing UUID.");
    } else {
      try {
        dynamicFieldFacade.delete(dto.getUuid());

        addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
      } catch (BusinessException e) {
        redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
        redirectAttributes.addAttribute(GenericDto.UUID, dto.getUuid());
      }
    }

    RedirectView redirectView = new RedirectView();
    redirectView.setContextRelative(true);
    redirectView.setUrl(PATH_PERMISSION_FORM);
    return redirectView;

  }
}
