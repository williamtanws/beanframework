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
import com.beanframework.backoffice.RegionWebConstants;
import com.beanframework.backoffice.RegionWebConstants.RegionPreAuthorizeEnum;
import com.beanframework.backoffice.data.RegionDataTableResponseData;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.DataTableResponse;
import com.beanframework.common.data.HistoryDataTableResponseData;
import com.beanframework.core.api.AbstractResource;
import com.beanframework.core.data.RegionDto;
import com.beanframework.core.facade.RegionFacade;
import com.beanframework.internationalization.domain.Region;

@RestController
public class RegionResource extends AbstractResource {

	@Autowired
	private RegionFacade regionFacade;

	@PreAuthorize(RegionPreAuthorizeEnum.HAS_READ)
	@RequestMapping(RegionWebConstants.Path.Api.CHECKID)
	public boolean checkId(Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		String id = requestParams.get(BackofficeWebConstants.Param.ID).toString();

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Region.ID, id);

		RegionDto data = regionFacade.findOneProperties(properties);

		String uuidStr = (String) requestParams.get(BackofficeWebConstants.Param.UUID);
		if (StringUtils.isNotBlank(uuidStr)) {
			UUID uuid = UUID.fromString(uuidStr);
			if (data != null && data.getUuid().equals(uuid)) {
				return true;
			}
		}

		return data != null ? false : true;
	}

	@PreAuthorize(RegionPreAuthorizeEnum.HAS_READ)
	@RequestMapping(value = RegionWebConstants.Path.Api.PAGE, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<RegionDataTableResponseData> page(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.prepareDataTableRequest(request);

		Page<RegionDto> pagination = regionFacade.findPage(dataTableRequest);

		DataTableResponse<RegionDataTableResponseData> dataTableResponse = new DataTableResponse<RegionDataTableResponseData>();
		dataTableResponse.setDraw(dataTableRequest.getDraw());
		dataTableResponse.setRecordsTotal(regionFacade.count());
		dataTableResponse.setRecordsFiltered((int) pagination.getTotalElements());

		for (RegionDto dto : pagination.getContent()) {

			RegionDataTableResponseData data = new RegionDataTableResponseData();
			data.setUuid(dto.getUuid().toString());
			data.setId(StringUtils.stripToEmpty(dto.getId()));
			data.setName(StringUtils.stripToEmpty(dto.getName()));
			data.setActive(dto.getActive());
			dataTableResponse.getData().add(data);
		}
		return dataTableResponse;
	}

	@PreAuthorize(RegionPreAuthorizeEnum.HAS_READ)
	@RequestMapping(value = RegionWebConstants.Path.Api.HISTORY, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<HistoryDataTableResponseData> history(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.prepareDataTableRequest(request);
		dataTableRequest.setUniqueId((String) request.getParameter("uuid"));

		return historyDataTableResponse(dataTableRequest, regionFacade.findHistory(dataTableRequest), regionFacade.countHistory(dataTableRequest));
	}
}