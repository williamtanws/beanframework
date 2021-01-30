package com.beanframework.backoffice.api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.LanguageWebConstants;
import com.beanframework.backoffice.LanguageWebConstants.LanguagePreAuthorizeEnum;
import com.beanframework.backoffice.data.LanguageDataTableResponseData;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.DataTableResponse;
import com.beanframework.common.data.HistoryDataTableResponseData;
import com.beanframework.core.api.AbstractResource;
import com.beanframework.core.data.LanguageDto;
import com.beanframework.core.facade.LanguageFacade;
import com.beanframework.internationalization.domain.Language;

@RestController
public class LanguageResource extends AbstractResource {

	@Autowired
	private LanguageFacade languageFacade;

	@PreAuthorize(LanguagePreAuthorizeEnum.HAS_READ)
	@RequestMapping(LanguageWebConstants.Path.Api.CHECKID)
	public boolean checkId(Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		String id = requestParams.get(BackofficeWebConstants.Param.ID).toString();

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Language.ID, id);

		LanguageDto data = languageFacade.findOneProperties(properties);

		String uuidStr = (String) requestParams.get(BackofficeWebConstants.Param.UUID);
		if (StringUtils.isNotBlank(uuidStr)) {
			UUID uuid = UUID.fromString(uuidStr);
			if (data != null && data.getUuid().equals(uuid)) {
				return true;
			}
		}

		return data != null ? false : true;
	}

	@PreAuthorize(LanguagePreAuthorizeEnum.HAS_READ)
	@RequestMapping(value = LanguageWebConstants.Path.Api.LIST, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<LanguageDataTableResponseData> page(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.prepareDataTableRequest(request);

		Page<LanguageDto> pagination = languageFacade.findPage(dataTableRequest);

		DataTableResponse<LanguageDataTableResponseData> dataTableResponse = new DataTableResponse<LanguageDataTableResponseData>();
		dataTableResponse.setDraw(dataTableRequest.getDraw());
		dataTableResponse.setRecordsTotal(languageFacade.count());
		dataTableResponse.setRecordsFiltered((int) pagination.getTotalElements());

		for (LanguageDto dto : pagination.getContent()) {

			LanguageDataTableResponseData data = new LanguageDataTableResponseData();
			data.setUuid(dto.getUuid().toString());
			data.setId(StringUtils.stripToEmpty(dto.getId()));
			data.setName(StringUtils.stripToEmpty(dto.getName()));
			data.setActive(dto.getActive());
			data.setSort(dto.getSort());
			dataTableResponse.getData().add(data);
		}
		return dataTableResponse;
	}

	@PreAuthorize(LanguagePreAuthorizeEnum.HAS_READ)
	@RequestMapping(value = LanguageWebConstants.Path.Api.HISTORY, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<HistoryDataTableResponseData> history(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.prepareDataTableRequest(request);
		dataTableRequest.setUniqueId((String) request.getParameter("uuid"));

		return historyDataTableResponse(dataTableRequest, languageFacade.findHistory(dataTableRequest), languageFacade.countHistory(dataTableRequest));
	}
}