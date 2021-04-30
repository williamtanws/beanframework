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
import com.beanframework.backoffice.CompanyWebConstants;
import com.beanframework.backoffice.CompanyWebConstants.CompanyPreAuthorizeEnum;
import com.beanframework.backoffice.api.data.CompanyDataTableResponseData;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.DataTableResponse;
import com.beanframework.common.data.HistoryDataTableResponseData;
import com.beanframework.core.api.AbstractResource;
import com.beanframework.core.data.CompanyDto;
import com.beanframework.core.facade.CompanyFacade;
import com.beanframework.user.domain.Company;

@RestController
public class CompanyResource extends AbstractResource {

	@Autowired
	private CompanyFacade companyFacade;

	@PreAuthorize(CompanyPreAuthorizeEnum.HAS_READ)
	@RequestMapping(CompanyWebConstants.Path.Api.COMPANY_CHECKID)
	public boolean checkId(Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		String id = requestParams.get(BackofficeWebConstants.Param.ID).toString();

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Company.ID, id);

		CompanyDto data = companyFacade.findOneProperties(properties);

		String uuidStr = (String) requestParams.get(BackofficeWebConstants.Param.UUID);
		if (StringUtils.isNotBlank(uuidStr)) {
			UUID uuid = UUID.fromString(uuidStr);
			if (data != null && data.getUuid().equals(uuid)) {
				return true;
			}
		}

		return data != null ? false : true;
	}

	@PreAuthorize(CompanyPreAuthorizeEnum.HAS_READ)
	@RequestMapping(value = CompanyWebConstants.Path.Api.COMPANY, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<CompanyDataTableResponseData> page(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.prepareDataTableRequest(request);

		Page<CompanyDto> pagination = companyFacade.findPage(dataTableRequest);

		DataTableResponse<CompanyDataTableResponseData> dataTableResponse = new DataTableResponse<CompanyDataTableResponseData>();
		dataTableResponse.setDraw(dataTableRequest.getDraw());
		dataTableResponse.setRecordsTotal(companyFacade.count());
		dataTableResponse.setRecordsFiltered((int) pagination.getTotalElements());

		for (CompanyDto dto : pagination.getContent()) {

			CompanyDataTableResponseData data = new CompanyDataTableResponseData();
			data.setUuid(dto.getUuid().toString());
			data.setId(StringUtils.stripToEmpty(dto.getId()));
			data.setName(StringUtils.stripToEmpty(dto.getName()));
			dataTableResponse.getData().add(data);
		}
		return dataTableResponse;
	}

	@PreAuthorize(CompanyPreAuthorizeEnum.HAS_READ)
	@RequestMapping(value = CompanyWebConstants.Path.Api.COMPANY_HISTORY, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<HistoryDataTableResponseData> history(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.prepareDataTableRequest(request);
		dataTableRequest.setUniqueId((String) request.getParameter("uuid"));

		return historyDataTableResponse(dataTableRequest, companyFacade.findHistory(dataTableRequest), companyFacade.countHistory(dataTableRequest));
	}
}