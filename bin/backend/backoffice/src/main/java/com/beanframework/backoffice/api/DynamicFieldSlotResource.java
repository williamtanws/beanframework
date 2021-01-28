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
import com.beanframework.backoffice.DynamicFieldSlotWebConstants;
import com.beanframework.backoffice.DynamicFieldSlotWebConstants.DynamicFieldSlotPreAuthorizeEnum;
import com.beanframework.backoffice.data.DynamicFieldSlotDataTableResponseData;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.DataTableResponse;
import com.beanframework.common.data.HistoryDataTableResponseData;
import com.beanframework.core.api.AbstractResource;
import com.beanframework.core.data.DynamicFieldSlotDto;
import com.beanframework.core.facade.DynamicFieldSlotFacade;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;

@RestController
public class DynamicFieldSlotResource extends AbstractResource {
	@Autowired
	private DynamicFieldSlotFacade dynamicFieldSlotFacade;

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.HAS_READ)
	@RequestMapping(DynamicFieldSlotWebConstants.Path.Api.CHECKID)
	public boolean checkId(Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		String id = requestParams.get(BackofficeWebConstants.Param.ID).toString();

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(DynamicFieldSlot.ID, id);

		DynamicFieldSlotDto data = dynamicFieldSlotFacade.findOneProperties(properties);

		String uuidStr = (String) requestParams.get(BackofficeWebConstants.Param.UUID);
		if (StringUtils.isNotBlank(uuidStr)) {
			UUID uuid = UUID.fromString(uuidStr);
			if (data != null && data.getUuid().equals(uuid)) {
				return true;
			}
		}

		return data != null ? false : true;
	}

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.HAS_READ)
	@RequestMapping(value = DynamicFieldSlotWebConstants.Path.Api.PAGE, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<DynamicFieldSlotDataTableResponseData> page(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.prepareDataTableRequest(request);

		Page<DynamicFieldSlotDto> pagination = dynamicFieldSlotFacade.findPage(dataTableRequest);

		DataTableResponse<DynamicFieldSlotDataTableResponseData> dataTableResponse = new DataTableResponse<DynamicFieldSlotDataTableResponseData>();
		dataTableResponse.setDraw(dataTableRequest.getDraw());
		dataTableResponse.setRecordsTotal(dynamicFieldSlotFacade.count());
		dataTableResponse.setRecordsFiltered((int) pagination.getTotalElements());

		for (DynamicFieldSlotDto dto : pagination.getContent()) {

			DynamicFieldSlotDataTableResponseData data = new DynamicFieldSlotDataTableResponseData();
			data.setUuid(dto.getUuid().toString());
			data.setId(StringUtils.stripToEmpty(dto.getId()));
			data.setName(StringUtils.stripToEmpty(dto.getName()));
			data.setSort(dto.getSort());
			dataTableResponse.getData().add(data);
		}
		return dataTableResponse;
	}

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.HAS_READ)
	@RequestMapping(value = DynamicFieldSlotWebConstants.Path.Api.HISTORY, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<HistoryDataTableResponseData> history(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.prepareDataTableRequest(request);
		dataTableRequest.setUniqueId((String) request.getParameter("uuid"));

		return historyDataTableResponse(dataTableRequest, dynamicFieldSlotFacade.findHistory(dataTableRequest), dynamicFieldSlotFacade.countHistory(dataTableRequest));
	}
}