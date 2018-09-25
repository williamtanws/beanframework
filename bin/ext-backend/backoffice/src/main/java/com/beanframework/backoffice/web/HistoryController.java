package com.beanframework.backoffice.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.beanframework.backoffice.WebBackofficeConstants;
import com.beanframework.backoffice.WebHistoryConstants;
import com.beanframework.backoffice.domain.HistorySearch;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.utils.ParamUtils;
import com.beanframework.history.domain.History;
import com.beanframework.history.service.HistoryFacade;

@Controller
public class HistoryController {

	@Autowired
	private HistoryFacade historyFacade;

	@Autowired
	private LocaleMessageService localeMessageService;

	@Value(WebHistoryConstants.Path.HISTORY)
	private String PATH_HISTORY;

	@Value(WebHistoryConstants.View.LIST)
	private String VIEW_HISTORY_LIST;

	@Value(WebHistoryConstants.LIST_SIZE)
	private int MODULE_HISTORY_LIST_SIZE;

	private Page<History> getPagination(Model model, @RequestParam Map<String, Object> requestParams) {
		int page = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_HISTORY_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.PROPERTIES));
		String[] properties = StringUtils.isEmpty(propertiesStr) ? null
				: propertiesStr.split(WebBackofficeConstants.Pagination.PROPERTIES_SPLIT);

		String directionStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.DIRECTION));
		Direction direction = StringUtils.isEmpty(directionStr) ? Direction.ASC : Direction.fromString(directionStr);

		HistorySearch historySearch = (HistorySearch) model.asMap().get(WebHistoryConstants.ModelAttribute.SEARCH);

		History history = new History();
		history.setId(historySearch.getIdSearch());

		if (properties == null) {
			properties = new String[1];
			properties[0] = History.CREATED_BY;
			direction = Sort.Direction.DESC;
		}

		Page<History> pagination = historyFacade.page(history, page, size, direction, properties);

		model.addAttribute(WebBackofficeConstants.Pagination.PROPERTIES, propertiesStr);
		model.addAttribute(WebBackofficeConstants.Pagination.DIRECTION, directionStr);

		return pagination;
	}

	@ModelAttribute(WebHistoryConstants.ModelAttribute.UPDATE)
	public History populateHistoryForm(HttpServletRequest request) {
		return historyFacade.create();
	}

	@ModelAttribute(WebHistoryConstants.ModelAttribute.SEARCH)
	public HistorySearch populateHistorySearch(HttpServletRequest request) {
		return new HistorySearch();
	}

	@PreAuthorize(WebHistoryConstants.PreAuthorize.READ)
	@GetMapping(value = WebHistoryConstants.Path.HISTORY)
	public String list(@ModelAttribute(WebHistoryConstants.ModelAttribute.SEARCH) HistorySearch historySearch,
			@ModelAttribute(WebHistoryConstants.ModelAttribute.UPDATE) History historyUpdate, Model model,
			@RequestParam Map<String, Object> requestParams) {

		model.addAttribute(WebBackofficeConstants.PAGINATION, getPagination(model, requestParams));

		if (historyUpdate.getUuid() != null) {
			History existingHistory = historyFacade.findByUuid(historyUpdate.getUuid());
			if (existingHistory != null) {
				model.addAttribute(WebHistoryConstants.ModelAttribute.UPDATE, existingHistory);
			} else {
				historyUpdate.setUuid(null);
				model.addAttribute(WebBackofficeConstants.Model.ERROR,
						localeMessageService.getMessage(WebBackofficeConstants.Locale.RECORD_UUID_NOT_FOUND));
			}
		}

		return VIEW_HISTORY_LIST;
	}
}
