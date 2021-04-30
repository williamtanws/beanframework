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

import com.beanframework.backoffice.AddressWebConstants;
import com.beanframework.backoffice.AddressWebConstants.AddressPreAuthorizeEnum;
import com.beanframework.backoffice.api.data.AddressDataTableResponseData;
import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.DataTableResponse;
import com.beanframework.common.data.HistoryDataTableResponseData;
import com.beanframework.core.api.AbstractResource;
import com.beanframework.core.data.AddressDto;
import com.beanframework.core.facade.AddressFacade;
import com.beanframework.user.domain.Address;

@RestController
public class AddressResource extends AbstractResource {

	@Autowired
	private AddressFacade addressFacade;

	@PreAuthorize(AddressPreAuthorizeEnum.HAS_READ)
	@RequestMapping(AddressWebConstants.Path.Api.ADDRESS_CHECKID)
	public boolean checkId(Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		String id = requestParams.get(BackofficeWebConstants.Param.ID).toString();

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Address.ID, id);

		AddressDto data = addressFacade.findOneProperties(properties);

		String uuidStr = (String) requestParams.get(BackofficeWebConstants.Param.UUID);
		if (StringUtils.isNotBlank(uuidStr)) {
			UUID uuid = UUID.fromString(uuidStr);
			if (data != null && data.getUuid().equals(uuid)) {
				return true;
			}
		}

		return data != null ? false : true;
	}

	@PreAuthorize(AddressPreAuthorizeEnum.HAS_READ)
	@RequestMapping(value = AddressWebConstants.Path.Api.ADDRESS, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<AddressDataTableResponseData> page(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.prepareDataTableRequest(request);
		
		Page<AddressDto> pagination = addressFacade.findPage(dataTableRequest);

		DataTableResponse<AddressDataTableResponseData> dataTableResponse = new DataTableResponse<AddressDataTableResponseData>();
		dataTableResponse.setDraw(dataTableRequest.getDraw());
		dataTableResponse.setRecordsTotal(addressFacade.count());
		dataTableResponse.setRecordsFiltered((int) pagination.getTotalElements());

		for (AddressDto dto : pagination.getContent()) {

			AddressDataTableResponseData data = new AddressDataTableResponseData();
			data.setUuid(dto.getUuid().toString());
			data.setId(StringUtils.stripToEmpty(dto.getId()));
			data.setStreetName(StringUtils.stripToEmpty(dto.getStreetName()));
			dataTableResponse.getData().add(data);
		}
		return dataTableResponse;
	}

	@PreAuthorize(AddressPreAuthorizeEnum.HAS_READ)
	@RequestMapping(value = AddressWebConstants.Path.Api.ADDRESS_HISTORY, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<HistoryDataTableResponseData> history(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.prepareDataTableRequest(request);
		dataTableRequest.setUniqueId((String) request.getParameter("uuid"));

		return historyDataTableResponse(dataTableRequest, addressFacade.findHistory(dataTableRequest), addressFacade.countHistory(dataTableRequest));
	}
}