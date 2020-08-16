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
import com.beanframework.backoffice.CurrencyWebConstants;
import com.beanframework.backoffice.data.CurrencyDataTableResponseData;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.DataTableResponse;
import com.beanframework.common.data.HistoryDataTableResponseData;
import com.beanframework.core.api.AbstractResource;
import com.beanframework.core.data.CurrencyDto;
import com.beanframework.core.facade.CurrencyFacade;
import com.beanframework.core.facade.CurrencyFacade.CurrencyPreAuthorizeEnum;
import com.beanframework.internationalization.domain.Currency;

@RestController
public class CurrencyResource extends AbstractResource {

	@Autowired
	private CurrencyFacade currencyFacade;

	@PreAuthorize(CurrencyPreAuthorizeEnum.HAS_READ)
	@RequestMapping(CurrencyWebConstants.Path.Api.CHECKID)
	public boolean checkId(Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		String id = requestParams.get(BackofficeWebConstants.Param.ID).toString();

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Currency.ID, id);

		CurrencyDto data = currencyFacade.findOneProperties(properties);

		String uuidStr = (String) requestParams.get(BackofficeWebConstants.Param.UUID);
		if (StringUtils.isNotBlank(uuidStr)) {
			UUID uuid = UUID.fromString(uuidStr);
			if (data != null && data.getUuid().equals(uuid)) {
				return true;
			}
		}

		return data != null ? false : true;
	}

	@PreAuthorize(CurrencyPreAuthorizeEnum.HAS_READ)
	@RequestMapping(value = CurrencyWebConstants.Path.Api.PAGE, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<CurrencyDataTableResponseData> page(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.prepareDataTableRequest(request);

		Page<CurrencyDto> pagination = currencyFacade.findPage(dataTableRequest);

		DataTableResponse<CurrencyDataTableResponseData> dataTableResponse = new DataTableResponse<CurrencyDataTableResponseData>();
		dataTableResponse.setDraw(dataTableRequest.getDraw());
		dataTableResponse.setRecordsTotal(currencyFacade.count());
		dataTableResponse.setRecordsFiltered((int) pagination.getTotalElements());

		for (CurrencyDto dto : pagination.getContent()) {

			CurrencyDataTableResponseData data = new CurrencyDataTableResponseData();
			data.setUuid(dto.getUuid().toString());
			data.setId(StringUtils.stripToEmpty(dto.getId()));
			data.setName(StringUtils.stripToEmpty(dto.getName()));
			dataTableResponse.getData().add(data);
		}
		return dataTableResponse;
	}

	@PreAuthorize(CurrencyPreAuthorizeEnum.HAS_READ)
	@RequestMapping(value = CurrencyWebConstants.Path.Api.HISTORY, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<HistoryDataTableResponseData> history(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.prepareDataTableRequest(request);
		dataTableRequest.setUniqueId((String) request.getParameter("uuid"));

		return historyDataTableResponse(dataTableRequest, currencyFacade.findHistory(dataTableRequest), currencyFacade.countHistory(dataTableRequest), "module.currency");
	}
}