package com.beanframework.backoffice.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.beanframework.backoffice.AuditorWebConstants;
import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.data.AuditorDto;
import com.beanframework.backoffice.data.AuditorSearch;
import com.beanframework.backoffice.facade.AuditorFacade;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.utils.ParamUtils;

@Controller
public class AuditorController extends AbstractController {

	@Autowired
	private AuditorFacade auditorFacade;

	@Value(AuditorWebConstants.Path.LANGUAGE)
	private String PATH_LANGUAGE;

	@Value(AuditorWebConstants.View.LIST)
	private String VIEW_LANGUAGE_LIST;

	@Value(AuditorWebConstants.LIST_SIZE)
	private int MODULE_LANGUAGE_LIST_SIZE;

	private Page<AuditorDto> getPagination(AuditorSearch auditorSearch, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		int page = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_LANGUAGE_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.PROPERTIES));
		String[] properties = StringUtils.isBlank(propertiesStr) ? null : propertiesStr.split(BackofficeWebConstants.Pagination.PROPERTIES_SPLIT);

		String directionStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.DIRECTION));
		Direction direction = StringUtils.isBlank(directionStr) ? Direction.ASC : Direction.fromString(directionStr);

		if (properties == null) {
			properties = new String[1];
			properties[0] = AuditorDto.CREATED_DATE;
			direction = Sort.Direction.DESC;
		}

		Page<AuditorDto> pagination = auditorFacade.findPage(auditorSearch, PageRequest.of(page <= 0 ? 0 : page - 1, size <= 0 ? 1 : size, direction, properties));

		model.addAttribute(BackofficeWebConstants.Pagination.PROPERTIES, propertiesStr);
		model.addAttribute(BackofficeWebConstants.Pagination.DIRECTION, directionStr);

		return pagination;
	}

	@ModelAttribute(AuditorWebConstants.ModelAttribute.UPDATE)
	public AuditorDto populateAuditorForm(HttpServletRequest request) throws Exception {
		return new AuditorDto();
	}

	@ModelAttribute(AuditorWebConstants.ModelAttribute.SEARCH)
	public AuditorSearch populateAuditorSearch(HttpServletRequest request) {
		return new AuditorSearch();
	}

	@GetMapping(value = AuditorWebConstants.Path.LANGUAGE)
	public String list(@ModelAttribute(AuditorWebConstants.ModelAttribute.SEARCH) AuditorSearch auditorSearch, @ModelAttribute(AuditorWebConstants.ModelAttribute.UPDATE) AuditorDto auditorUpdate,
			Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		model.addAttribute(BackofficeWebConstants.PAGINATION, getPagination(auditorSearch, model, requestParams));

		if (auditorUpdate.getUuid() != null) {

			AuditorDto existingAuditor = auditorFacade.findOneByUuid(auditorUpdate.getUuid());

			List<Object[]> revisions = auditorFacade.findHistoryByUuid(auditorUpdate.getUuid(), null, null);
			model.addAttribute(BackofficeWebConstants.Model.REVISIONS, revisions);

			if (existingAuditor != null) {
				model.addAttribute(AuditorWebConstants.ModelAttribute.UPDATE, existingAuditor);
			} else {
				auditorUpdate.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_LANGUAGE_LIST;
	}
}
