package com.beanframework.backoffice.web;

import java.util.List;
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
import com.beanframework.backoffice.UserGroupWebConstants;
import com.beanframework.backoffice.UserGroupWebConstants.UserGroupPreAuthorizeEnum;
import com.beanframework.common.data.GenericDto;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.controller.AbstractController;
import com.beanframework.core.data.UserGroupDto;
import com.beanframework.core.data.UserPermissionDto;
import com.beanframework.core.data.UserRightDto;
import com.beanframework.core.facade.UserGroupFacade;
import com.beanframework.core.facade.UserPermissionFacade;
import com.beanframework.core.facade.UserRightFacade;


@Controller
public class UserGroupController extends AbstractController {

  @Autowired
  private UserGroupFacade usergroupFacade;

  @Autowired
  private UserRightFacade userRightFacade;

  @Autowired
  private UserPermissionFacade userPermissionFacade;

  @Value(UserGroupWebConstants.Path.USERGROUP)
  private String PATH_USERGROUP;

  @Value(UserGroupWebConstants.Path.USERGROUP_FORM)
  private String PATH_USERGROUP_FORM;

  @Value(UserGroupWebConstants.View.USERGROUP)
  private String VIEW_USERGROUP;

  @Value(UserGroupWebConstants.View.USERGROUP_FORM)
  private String VIEW_USERGROUP_FORM;

  @PreAuthorize(UserGroupPreAuthorizeEnum.HAS_READ)
  @GetMapping(value = UserGroupWebConstants.Path.USERGROUP)
  public String page(
      @Valid @ModelAttribute(UserGroupWebConstants.ModelAttribute.USERGROUP_DTO) UserGroupDto usergroupDto,
      Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
    return VIEW_USERGROUP;
  }

  @PreAuthorize(UserGroupPreAuthorizeEnum.HAS_READ)
  @GetMapping(value = UserGroupWebConstants.Path.USERGROUP_FORM)
  public String form(
      @Valid @ModelAttribute(UserGroupWebConstants.ModelAttribute.USERGROUP_DTO) UserGroupDto usergroupDto,
      Model model) throws Exception {

    // User Authority
    List<UserRightDto> userRights = userRightFacade.findAllDtoUserRights();
    model.addAttribute("userRights", userRights);

    List<UserPermissionDto> userPermissions = userPermissionFacade.findAllDtoUserPermissions();
    model.addAttribute("userPermissions", userPermissions);

    if (usergroupDto.getUuid() != null) {
      usergroupDto = usergroupFacade.findOneByUuid(usergroupDto.getUuid());
    } else {
      usergroupDto = usergroupFacade.createDto();
    }
    model.addAttribute(UserGroupWebConstants.ModelAttribute.USERGROUP_DTO, usergroupDto);

    return VIEW_USERGROUP_FORM;
  }

  @PreAuthorize(UserGroupPreAuthorizeEnum.HAS_CREATE)
  @PostMapping(value = UserGroupWebConstants.Path.USERGROUP_FORM, params = "create")
  public RedirectView create(
      @Valid @ModelAttribute(UserGroupWebConstants.ModelAttribute.USERGROUP_DTO) UserGroupDto usergroupDto,
      Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
      RedirectAttributes redirectAttributes) {

    if (usergroupDto.getUuid() != null) {
      redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
          "Create new record doesn't required UUID.");
    } else {
      try {
        usergroupDto = usergroupFacade.create(usergroupDto);

        addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
      } catch (BusinessException e) {
        addErrorMessage(UserGroupDto.class, e.getMessage(), bindingResult, redirectAttributes);
      }
    }

    redirectAttributes.addAttribute(GenericDto.UUID, usergroupDto.getUuid());

    RedirectView redirectView = new RedirectView();
    redirectView.setContextRelative(true);
    redirectView.setUrl(PATH_USERGROUP_FORM);
    return redirectView;
  }

  @PreAuthorize(UserGroupPreAuthorizeEnum.HAS_UPDATE)
  @PostMapping(value = UserGroupWebConstants.Path.USERGROUP_FORM, params = "update")
  public RedirectView update(
      @Valid @ModelAttribute(UserGroupWebConstants.ModelAttribute.USERGROUP_DTO) UserGroupDto usergroupDto,
      Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
      RedirectAttributes redirectAttributes) {

    if (usergroupDto.getUuid() == null) {
      redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
          "Update record required existing UUID.");
    } else {
      try {
        usergroupDto = usergroupFacade.update(usergroupDto);

        addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
      } catch (BusinessException e) {
        addErrorMessage(UserGroupDto.class, e.getMessage(), bindingResult, redirectAttributes);
      }
    }

    redirectAttributes.addAttribute(GenericDto.UUID, usergroupDto.getUuid());

    RedirectView redirectView = new RedirectView();
    redirectView.setContextRelative(true);
    redirectView.setUrl(PATH_USERGROUP_FORM);
    return redirectView;
  }

  @PreAuthorize(UserGroupPreAuthorizeEnum.HAS_DELETE)
  @PostMapping(value = UserGroupWebConstants.Path.USERGROUP_FORM, params = "delete")
  public RedirectView delete(
      @Valid @ModelAttribute(UserGroupWebConstants.ModelAttribute.USERGROUP_DTO) UserGroupDto dto,
      Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
      RedirectAttributes redirectAttributes) {

    if (dto.getUuid() == null) {
      redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
          "Delete record required existing UUID.");
    } else {
      try {
        usergroupFacade.delete(dto.getUuid());

        addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
      } catch (BusinessException e) {
        redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
        redirectAttributes.addAttribute(GenericDto.UUID, dto.getUuid());
      }
    }

    RedirectView redirectView = new RedirectView();
    redirectView.setContextRelative(true);
    redirectView.setUrl(PATH_USERGROUP_FORM);
    return redirectView;

  }
}
