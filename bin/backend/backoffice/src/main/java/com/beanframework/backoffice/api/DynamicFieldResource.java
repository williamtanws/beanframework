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
import com.beanframework.backoffice.DynamicFieldWebConstants;
import com.beanframework.backoffice.data.DynamicFieldDataTableResponseData;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.DataTableResponse;
import com.beanframework.common.data.HistoryDataTableResponseData;
import com.beanframework.core.api.AbstractResource;
import com.beanframework.core.data.DynamicFieldDto;
import com.beanframework.core.facade.DynamicFieldFacade;
import com.beanframework.core.facade.DynamicFieldFacade.DynamicFieldPreAuthorizeEnum;
import com.beanframework.dynamicfield.domain.DynamicField;

@RestController
public class DynamicFieldResource extends AbstractResource {
	@Autowired
	private DynamicFieldFacade dynamicFieldFacade;

	@PreAuthorize(DynamicFieldPreAuthorizeEnum.HAS_READ)
	@RequestMapping(DynamicFieldWebConstants.Path.Api.CHECKID)
	public boolean checkId(Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		String id = requestParams.get(BackofficeWebConstants.Param.ID).toString();

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(DynamicField.ID, id);

		DynamicFieldDto data = dynamicFieldFacade.findOneProperties(properties);

		String uuidStr = (String) requestParams.get(BackofficeWebConstants.Param.UUID);
		if (StringUtils.isNotBlank(uuidStr)) {
			UUID uuid = UUID.fromString(uuidStr);
			if (data != null && data.getUuid().equals(uuid)) {
				return true;
			}
		}

		return data != null ? false : true;
	}

	@PreAuthorize(DynamicFieldPreAuthorizeEnum.HAS_READ)
	@RequestMapping(value = DynamicFieldWebConstants.Path.Api.PAGE, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<DynamicFieldDataTableResponseData> page(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.getSkipColumnIndexes().add(2);
		dataTableRequest.prepareDataTableRequest(request);

		Page<DynamicFieldDto> pagination = dynamicFieldFacade.findPage(dataTableRequest);

		DataTableResponse<DynamicFieldDataTableResponseData> dataTableResponse = new DataTableResponse<DynamicFieldDataTableResponseData>();
		dataTableResponse.setDraw(dataTableRequest.getDraw());
		dataTableResponse.setRecordsTotal(dynamicFieldFacade.count());
		dataTableResponse.setRecordsFiltered((int) pagination.getTotalElements());

		for (DynamicFieldDto dto : pagination.getContent()) {

			DynamicFieldDataTableResponseData data = new DynamicFieldDataTableResponseData();
			data.setUuid(dto.getUuid().toString());
			data.setId(StringUtils.stripToEmpty(dto.getId()));
			data.setName(StringUtils.stripToEmpty(dto.getName()));
			data.setType(dto.getType().toString());
			dataTableResponse.getData().add(data);
		}
		return dataTableResponse;
	}

	@PreAuthorize(DynamicFieldPreAuthorizeEnum.HAS_READ)
	@RequestMapping(value = DynamicFieldWebConstants.Path.Api.HISTORY, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<HistoryDataTableResponseData> history(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.prepareDataTableRequest(request);
		dataTableRequest.setUniqueId((String) request.getParameter("uuid"));

		return historyDataTableResponse(dataTableRequest, dynamicFieldFacade.findHistory(dataTableRequest), dynamicFieldFacade.countHistory(dataTableRequest), "module.dynamicfield");
	}
}