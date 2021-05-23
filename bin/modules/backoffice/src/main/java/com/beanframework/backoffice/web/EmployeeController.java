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
import com.beanframework.backoffice.EmployeeWebConstants;
import com.beanframework.backoffice.EmployeeWebConstants.EmployeePreAuthorizeEnum;
import com.beanframework.common.data.GenericDto;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.controller.AbstractController;
import com.beanframework.core.data.EmployeeDto;
import com.beanframework.core.facade.EmployeeFacade;


@Controller
public class EmployeeController extends AbstractController {

  @Autowired
  private EmployeeFacade employeeFacade;

  @Value(EmployeeWebConstants.Path.EMPLOYEE)
  private String PATH_EMPLOYEE;

  @Value(EmployeeWebConstants.Path.EMPLOYEE_FORM)
  private String PATH_EMPLOYEE_FORM;

  @Value(EmployeeWebConstants.View.EMPLOYEE)
  private String VIEW_EMPLOYEE;

  @Value(EmployeeWebConstants.View.EMPLOYEE_FORM)
  private String VIEW_EMPLOYEE_FORM;

  @PreAuthorize(EmployeePreAuthorizeEnum.HAS_READ)
  @GetMapping(value = EmployeeWebConstants.Path.EMPLOYEE)
  public String page(
      @Valid @ModelAttribute(EmployeeWebConstants.ModelAttribute.EMPLOYEE_DTO) EmployeeDto employeeDto,
      Model model, @RequestParam Map<String, Object> requestParams) {
    return VIEW_EMPLOYEE;
  }

  @PreAuthorize(EmployeePreAuthorizeEnum.HAS_READ)
  @GetMapping(value = EmployeeWebConstants.Path.EMPLOYEE_FORM)
  public String form(
      @Valid @ModelAttribute(EmployeeWebConstants.ModelAttribute.EMPLOYEE_DTO) EmployeeDto employeeDto,
      Model model) throws BusinessException {

    if (employeeDto.getUuid() != null) {
      employeeDto = employeeFacade.findOneByUuid(employeeDto.getUuid());
    } else {
      employeeDto = employeeFacade.createDto();
    }
    model.addAttribute(EmployeeWebConstants.ModelAttribute.EMPLOYEE_DTO, employeeDto);

    return VIEW_EMPLOYEE_FORM;
  }

  @PreAuthorize(EmployeePreAuthorizeEnum.HAS_CREATE)
  @PostMapping(value = EmployeeWebConstants.Path.EMPLOYEE_FORM, params = "create")
  public RedirectView create(
      @Valid @ModelAttribute(EmployeeWebConstants.ModelAttribute.EMPLOYEE_DTO) EmployeeDto employeeDto,
      Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
      RedirectAttributes redirectAttributes) {

    if (employeeDto.getUuid() != null) {
      redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
          "Create new record doesn't required UUID.");
    } else {
      try {
        employeeDto = employeeFacade.save(employeeDto);

        addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
      } catch (BusinessException e) {
        addErrorMessage(EmployeeDto.class, e.getMessage(), bindingResult, redirectAttributes);
      }
    }

    redirectAttributes.addAttribute(GenericDto.UUID, employeeDto.getUuid());

    RedirectView redirectView = new RedirectView();
    redirectView.setContextRelative(true);
    redirectView.setUrl(PATH_EMPLOYEE_FORM);
    return redirectView;
  }

  @PreAuthorize(EmployeePreAuthorizeEnum.HAS_UPDATE)
  @PostMapping(value = EmployeeWebConstants.Path.EMPLOYEE_FORM, params = "update")
  public RedirectView update(
      @Valid @ModelAttribute(EmployeeWebConstants.ModelAttribute.EMPLOYEE_DTO) EmployeeDto employeeDto,
      Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
      RedirectAttributes redirectAttributes) {

    if (employeeDto.getUuid() == null) {
      redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
          "Update record required existing UUID.");
    } else {
      try {
        employeeDto = employeeFacade.save(employeeDto);

        addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
      } catch (BusinessException e) {
        addErrorMessage(EmployeeDto.class, e.getMessage(), bindingResult, redirectAttributes);
      }
    }

    redirectAttributes.addAttribute(GenericDto.UUID, employeeDto.getUuid());

    RedirectView redirectView = new RedirectView();
    redirectView.setContextRelative(true);
    redirectView.setUrl(PATH_EMPLOYEE_FORM);
    return redirectView;
  }

  @PreAuthorize(EmployeePreAuthorizeEnum.HAS_DELETE)
  @PostMapping(value = EmployeeWebConstants.Path.EMPLOYEE_FORM, params = "delete")
  public RedirectView delete(
      @Valid @ModelAttribute(EmployeeWebConstants.ModelAttribute.EMPLOYEE_DTO) EmployeeDto dto,
      Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
      RedirectAttributes redirectAttributes) {

    if (dto.getUuid() == null) {
      redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
          "Delete record required existing UUID.");
    } else {
      try {
        employeeFacade.delete(dto.getUuid());

        addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
      } catch (BusinessException e) {
        redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
        redirectAttributes.addAttribute(GenericDto.UUID, dto.getUuid());
      }
    }

    RedirectView redirectView = new RedirectView();
    redirectView.setContextRelative(true);
    redirectView.setUrl(PATH_EMPLOYEE_FORM);
    return redirectView;

  }
}
