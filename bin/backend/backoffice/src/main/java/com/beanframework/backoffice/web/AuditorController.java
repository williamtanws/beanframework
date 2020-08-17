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

import com.beanframework.backoffice.AuditorWebConstants;
import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.data.AuditorDto;
import com.beanframework.core.facade.AuditorFacade;
import com.beanframework.core.facade.AuditorFacade.PreAuthorizeEnum;

@Controller
public class AuditorController extends AbstractController {

	@Autowired
	private AuditorFacade auditorFacade;

	@Value(AuditorWebConstants.Path.AUDITOR)
	private String PATH_LANGUAGE;

	@Value(AuditorWebConstants.View.LIST)
	private String VIEW_LANGUAGE_LIST;

	@PreAuthorize(PreAuthorizeEnum.HAS_READ)
	@GetMapping(value = AuditorWebConstants.Path.AUDITOR)
	public String list(@Valid @ModelAttribute(AuditorWebConstants.ModelAttribute.AUDITOR_DTO) AuditorDto auditorDto, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		model.addAttribute("create", false);

		if (auditorDto.getUuid() != null) {

			AuditorDto existDto = auditorFacade.findOneByUuid(auditorDto.getUuid());

			if (existDto != null) {
				model.addAttribute(AuditorWebConstants.ModelAttribute.AUDITOR_DTO, existDto);
			} else {
				auditorDto.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_LANGUAGE_LIST;
	}
}
