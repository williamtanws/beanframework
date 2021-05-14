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
import com.beanframework.backoffice.VendorWebConstants;
import com.beanframework.backoffice.VendorWebConstants.VendorPreAuthorizeEnum;
import com.beanframework.common.data.GenericDto;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.controller.AbstractController;
import com.beanframework.core.data.VendorDto;
import com.beanframework.core.facade.VendorFacade;


@Controller
public class VendorController extends AbstractController {

  @Autowired
  private VendorFacade vendorFacade;

  @Value(VendorWebConstants.Path.VENDOR)
  private String PATH_VENDOR;

  @Value(VendorWebConstants.Path.VENDOR_FORM)
  private String PATH_VENDOR_FORM;

  @Value(VendorWebConstants.View.VENDOR)
  private String VIEW_VENDOR;

  @Value(VendorWebConstants.View.VENDOR_FORM)
  private String VIEW_VENDOR_FORM;

  @PreAuthorize(VendorPreAuthorizeEnum.HAS_READ)
  @GetMapping(value = VendorWebConstants.Path.VENDOR)
  public String page(
      @Valid @ModelAttribute(VendorWebConstants.ModelAttribute.VENDOR_DTO) VendorDto vendorDto,
      Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
    return VIEW_VENDOR;
  }

  @PreAuthorize(VendorPreAuthorizeEnum.HAS_READ)
  @GetMapping(value = VendorWebConstants.Path.VENDOR_FORM)
  public String form(
      @Valid @ModelAttribute(VendorWebConstants.ModelAttribute.VENDOR_DTO) VendorDto vendorDto,
      Model model) throws Exception {

    if (vendorDto.getUuid() != null) {
      vendorDto = vendorFacade.findOneByUuid(vendorDto.getUuid());
    } else {
      vendorDto = vendorFacade.createDto();
    }
    model.addAttribute(VendorWebConstants.ModelAttribute.VENDOR_DTO, vendorDto);

    return VIEW_VENDOR_FORM;
  }

  @PreAuthorize(VendorPreAuthorizeEnum.HAS_CREATE)
  @PostMapping(value = VendorWebConstants.Path.VENDOR_FORM, params = "create")
  public RedirectView create(
      @Valid @ModelAttribute(VendorWebConstants.ModelAttribute.VENDOR_DTO) VendorDto vendorDto,
      Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
      RedirectAttributes redirectAttributes) {

    if (vendorDto.getUuid() != null) {
      redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
          "Create new record doesn't required UUID.");
    } else {
      try {
        vendorDto = vendorFacade.create(vendorDto);

        addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
      } catch (BusinessException e) {
        addErrorMessage(VendorDto.class, e.getMessage(), bindingResult, redirectAttributes);
      }
    }

    redirectAttributes.addAttribute(GenericDto.UUID, vendorDto.getUuid());

    RedirectView redirectView = new RedirectView();
    redirectView.setContextRelative(true);
    redirectView.setUrl(PATH_VENDOR_FORM);
    return redirectView;
  }

  @PreAuthorize(VendorPreAuthorizeEnum.HAS_UPDATE)
  @PostMapping(value = VendorWebConstants.Path.VENDOR_FORM, params = "update")
  public RedirectView update(
      @Valid @ModelAttribute(VendorWebConstants.ModelAttribute.VENDOR_DTO) VendorDto vendorDto,
      Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
      RedirectAttributes redirectAttributes) {

    if (vendorDto.getUuid() == null) {
      redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
          "Update record required existing UUID.");
    } else {
      try {
        vendorDto = vendorFacade.update(vendorDto);

        addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
      } catch (BusinessException e) {
        addErrorMessage(VendorDto.class, e.getMessage(), bindingResult, redirectAttributes);
      }
    }

    redirectAttributes.addAttribute(GenericDto.UUID, vendorDto.getUuid());

    RedirectView redirectView = new RedirectView();
    redirectView.setContextRelative(true);
    redirectView.setUrl(PATH_VENDOR_FORM);
    return redirectView;
  }

  @PreAuthorize(VendorPreAuthorizeEnum.HAS_DELETE)
  @PostMapping(value = VendorWebConstants.Path.VENDOR_FORM, params = "delete")
  public RedirectView delete(
      @Valid @ModelAttribute(VendorWebConstants.ModelAttribute.VENDOR_DTO) VendorDto dto,
      Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
      RedirectAttributes redirectAttributes) {

    if (dto.getUuid() == null) {
      redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
          "Delete record required existing UUID.");
    } else {
      try {
        vendorFacade.delete(dto.getUuid());

        addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
      } catch (BusinessException e) {
        redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
        redirectAttributes.addAttribute(GenericDto.UUID, dto.getUuid());
      }
    }

    RedirectView redirectView = new RedirectView();
    redirectView.setContextRelative(true);
    redirectView.setUrl(PATH_VENDOR_FORM);
    return redirectView;

  }
}
