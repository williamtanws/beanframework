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
import com.beanframework.backoffice.DynamicFieldTemplateWebConstants;
import com.beanframework.backoffice.DynamicFieldTemplateWebConstants.DynamicFieldTemplatePreAuthorizeEnum;
import com.beanframework.backoffice.api.data.DynamicFieldDataTableResponseData;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.DataTableResponse;
import com.beanframework.common.data.HistoryDataTableResponseData;
import com.beanframework.core.api.AbstractResource;
import com.beanframework.core.data.DataTableResponseData;
import com.beanframework.core.data.DynamicFieldTemplateDto;
import com.beanframework.core.facade.DynamicFieldTemplateFacade;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;

@RestController
public class DynamicFieldTemplateResource extends AbstractResource {
	@Autowired
	private DynamicFieldTemplateFacade dynamicFieldTemplateFacade;

	@PreAuthorize(DynamicFieldTemplatePreAuthorizeEnum.HAS_READ)
	@RequestMapping(DynamicFieldTemplateWebConstants.Path.Api.DYNAMICFIELDTEMPLATE_CHECKID)
	public boolean checkId(Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		String id = requestParams.get(BackofficeWebConstants.Param.ID).toString();

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(DynamicFieldTemplate.ID, id);

		DynamicFieldTemplateDto data = dynamicFieldTemplateFacade.findOneProperties(properties);

		String uuidStr = (String) requestParams.get(BackofficeWebConstants.Param.UUID);
		if (StringUtils.isNotBlank(uuidStr)) {
			UUID uuid = UUID.fromString(uuidStr);
			if (data != null && data.getUuid().equals(uuid)) {
				return true;
			}
		}

		return data != null ? false : true;
	}

	@PreAuthorize(DynamicFieldTemplatePreAuthorizeEnum.HAS_READ)
	@RequestMapping(value = DynamicFieldTemplateWebConstants.Path.Api.DYNAMICFIELDTEMPLATE, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<DataTableResponseData> page(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.prepareDataTableRequest(request);

		Page<DynamicFieldTemplateDto> pagination = dynamicFieldTemplateFacade.findPage(dataTableRequest);

		DataTableResponse<DataTableResponseData> dataTableResponse = new DataTableResponse<DataTableResponseData>();
		dataTableResponse.setDraw(dataTableRequest.getDraw());
		dataTableResponse.setRecordsTotal(dynamicFieldTemplateFacade.count());
		dataTableResponse.setRecordsFiltered((int) pagination.getTotalElements());

		for (DynamicFieldTemplateDto dto : pagination.getContent()) {

			DynamicFieldDataTableResponseData data = new DynamicFieldDataTableResponseData();
			data.setUuid(dto.getUuid().toString());
			data.setId(StringUtils.stripToEmpty(dto.getId()));
			data.setName(StringUtils.stripToEmpty(dto.getName()));
			dataTableResponse.getData().add(data);
		}
		return dataTableResponse;
	}

	@PreAuthorize(DynamicFieldTemplatePreAuthorizeEnum.HAS_READ)
	@RequestMapping(value = DynamicFieldTemplateWebConstants.Path.Api.DYNAMICFIELDTEMPLATE_HISTORY, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<HistoryDataTableResponseData> history(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.prepareDataTableRequest(request);
		dataTableRequest.setUniqueId((String) request.getParameter("uuid"));

		return historyDataTableResponse(dataTableRequest, dynamicFieldTemplateFacade.findHistory(dataTableRequest), dynamicFieldTemplateFacade.countHistory(dataTableRequest));
	}
}