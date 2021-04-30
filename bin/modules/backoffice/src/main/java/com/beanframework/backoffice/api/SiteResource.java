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
import com.beanframework.backoffice.SiteWebConstants;
import com.beanframework.backoffice.SiteWebConstants.SitePreAuthorizeEnum;
import com.beanframework.backoffice.api.data.SiteDataTableResponseData;
import com.beanframework.cms.domain.Site;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.DataTableResponse;
import com.beanframework.common.data.HistoryDataTableResponseData;
import com.beanframework.core.api.AbstractResource;
import com.beanframework.core.data.DataTableResponseData;
import com.beanframework.core.data.SiteDto;
import com.beanframework.core.facade.SiteFacade;

@RestController
public class SiteResource extends AbstractResource {

	@Autowired
	private SiteFacade siteFacade;

	@PreAuthorize(SitePreAuthorizeEnum.HAS_READ)
	@RequestMapping(SiteWebConstants.Path.Api.SITE_CHECKID)
	public boolean checkId(Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		String id = requestParams.get(BackofficeWebConstants.Param.ID).toString();

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Site.ID, id);

		SiteDto data = siteFacade.findOneProperties(properties);

		String uuidStr = (String) requestParams.get(BackofficeWebConstants.Param.UUID);
		if (StringUtils.isNotBlank(uuidStr)) {
			UUID uuid = UUID.fromString(uuidStr);
			if (data != null && data.getUuid().equals(uuid)) {
				return true;
			}
		}

		return data != null ? false : true;
	}

	@PreAuthorize(SitePreAuthorizeEnum.HAS_READ)
	@RequestMapping(value = SiteWebConstants.Path.Api.SITE, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<DataTableResponseData> page(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.prepareDataTableRequest(request);

		Page<SiteDto> pagination = siteFacade.findPage(dataTableRequest);

		DataTableResponse<DataTableResponseData> dataTableResponse = new DataTableResponse<DataTableResponseData>();
		dataTableResponse.setDraw(dataTableRequest.getDraw());
		dataTableResponse.setRecordsTotal(siteFacade.count());
		dataTableResponse.setRecordsFiltered((int) pagination.getTotalElements());

		for (SiteDto dto : pagination.getContent()) {

			SiteDataTableResponseData data = new SiteDataTableResponseData();
			data.setUuid(dto.getUuid().toString());
			data.setId(StringUtils.stripToEmpty(dto.getId()));
			data.setName(StringUtils.stripToEmpty(dto.getName()));
			data.setUrl(StringUtils.stripToEmpty(dto.getUrl()));
			dataTableResponse.getData().add(data);
		}
		return dataTableResponse;
	}

	@PreAuthorize(SitePreAuthorizeEnum.HAS_READ)
	@RequestMapping(value = SiteWebConstants.Path.Api.SITE_HISTORY, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<HistoryDataTableResponseData> history(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.prepareDataTableRequest(request);
		dataTableRequest.setUniqueId((String) request.getParameter("uuid"));

		return historyDataTableResponse(dataTableRequest, siteFacade.findHistory(dataTableRequest), siteFacade.countHistory(dataTableRequest));
	}
}