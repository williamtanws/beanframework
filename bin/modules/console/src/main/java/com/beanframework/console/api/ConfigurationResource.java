package com.beanframework.console.api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.DataTableResponse;
import com.beanframework.common.data.HistoryDataTableResponseData;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.console.ConfigurationWebConstants;
import com.beanframework.console.ConsoleWebConstants;
import com.beanframework.console.data.ConfigurationDataTableResponseData;
import com.beanframework.core.api.AbstractResource;
import com.beanframework.core.data.ConfigurationDto;
import com.beanframework.core.data.DataTableResponseData;
import com.beanframework.core.facade.ConfigurationFacade;

@RestController
public class ConfigurationResource extends AbstractResource {

	@Autowired
	private ConfigurationFacade configurationFacade;

	@GetMapping(value = ConfigurationWebConstants.Path.Api.CONFIGURATION_CHECKID)
	public boolean checkIdExists(Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		String id = requestParams.get(ConsoleWebConstants.Param.ID).toString();

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Configuration.ID, id);

		ConfigurationDto configuration = configurationFacade.findOneProperties(properties);

		String uuidStr = (String) requestParams.get(ConsoleWebConstants.Param.UUID);
		if (StringUtils.isNotBlank(uuidStr)) {
			UUID uuid = UUID.fromString(uuidStr);
			if (configuration != null && configuration.getUuid().equals(uuid)) {
				return true;
			}
		}

		return configuration != null ? false : true;
	}

	@RequestMapping(value = ConfigurationWebConstants.Path.Api.CONFIGURATION, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<DataTableResponseData> page(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.prepareDataTableRequest(request);

		Page<ConfigurationDto> pagination = configurationFacade.findPage(dataTableRequest);

		DataTableResponse<DataTableResponseData> dataTableResponse = new DataTableResponse<DataTableResponseData>();
		dataTableResponse.setDraw(dataTableRequest.getDraw());
		dataTableResponse.setRecordsTotal(configurationFacade.count());
		dataTableResponse.setRecordsFiltered((int) pagination.getTotalElements());

		for (ConfigurationDto dto : pagination.getContent()) {

			ConfigurationDataTableResponseData data = new ConfigurationDataTableResponseData();
			data.setUuid(dto.getUuid().toString());
			data.setId(StringUtils.stripToEmpty(dto.getId()));
			data.setValue(StringUtils.stripToEmpty(dto.getValue()));
			dataTableResponse.getData().add(data);
		}
		return dataTableResponse;
	}

	@RequestMapping(value = ConfigurationWebConstants.Path.Api.CONFIGURATION_HISTORY, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<HistoryDataTableResponseData> history(HttpServletRequest request, @RequestParam Map<String, Object> requestParams) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.prepareDataTableRequest(request);
		dataTableRequest.setUniqueId(requestParams.get(ConsoleWebConstants.Param.UUID).toString());

		return historyDataTableResponse(dataTableRequest, configurationFacade.findHistory(dataTableRequest), configurationFacade.countHistory(dataTableRequest));
	}
}