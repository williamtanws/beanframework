package com.beanframework.backoffice.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.beanframework.backoffice.AuditorWebConstants;
import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.data.AuditorDto;
import com.beanframework.backoffice.facade.AuditorFacade;
import com.beanframework.common.controller.AbstractController;

@Controller
public class AuditorController extends AbstractController {

	@Autowired
	private AuditorFacade auditorFacade;

	@Value(AuditorWebConstants.Path.LANGUAGE)
	private String PATH_LANGUAGE;

	@Value(AuditorWebConstants.View.LIST)
	private String VIEW_LANGUAGE_LIST;

	@ModelAttribute(AuditorWebConstants.ModelAttribute.UPDATE)
	public AuditorDto populateAuditorForm(HttpServletRequest request) throws Exception {
		return new AuditorDto();
	}

	@GetMapping(value = AuditorWebConstants.Path.LANGUAGE)
	public String list(@ModelAttribute(AuditorWebConstants.ModelAttribute.UPDATE) AuditorDto updateDto, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		if (updateDto.getUuid() != null) {

			AuditorDto existDto = auditorFacade.findOneByUuid(updateDto.getUuid());

			if (existDto != null) {
				model.addAttribute(AuditorWebConstants.ModelAttribute.UPDATE, existDto);
			} else {
				updateDto.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_LANGUAGE_LIST;
	}
}
