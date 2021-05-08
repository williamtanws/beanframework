package com.beanframework.trainingbackoffice.api;

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
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.DataTableResponse;
import com.beanframework.common.data.HistoryDataTableResponseData;
import com.beanframework.core.api.AbstractResource;
import com.beanframework.training.domain.Training;
import com.beanframework.trainingbackoffice.TrainingWebConstants;
import com.beanframework.trainingbackoffice.TrainingWebConstants.TrainingPreAuthorizeEnum;
import com.beanframework.trainingbackoffice.api.data.TrainingDataTableResponseData;
import com.beanframework.trainingcore.data.TrainingDto;
import com.beanframework.trainingcore.facade.TrainingFacade;

@RestController
public class TrainingResource extends AbstractResource {

	@Autowired
	private TrainingFacade trainingFacade;

	@PreAuthorize(TrainingPreAuthorizeEnum.HAS_READ)
	@RequestMapping(TrainingWebConstants.Path.Api.TRAINING_CHECKID)
	public boolean checkId(Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		String id = requestParams.get(BackofficeWebConstants.Param.ID).toString();

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Training.ID, id);

		TrainingDto data = trainingFacade.findOneProperties(properties);

		String uuidStr = (String) requestParams.get(BackofficeWebConstants.Param.UUID);
		if (StringUtils.isNotBlank(uuidStr)) {
			UUID uuid = UUID.fromString(uuidStr);
			if (data != null && data.getUuid().equals(uuid)) {
				return true;
			}
		}

		return data != null ? false : true;
	}

	@PreAuthorize(TrainingPreAuthorizeEnum.HAS_READ)
	@RequestMapping(value = TrainingWebConstants.Path.Api.TRAINING, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<TrainingDataTableResponseData> page(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.prepareDataTableRequest(request);

		Page<TrainingDto> pagination = trainingFacade.findPage(dataTableRequest);

		DataTableResponse<TrainingDataTableResponseData> dataTableResponse = new DataTableResponse<TrainingDataTableResponseData>();
		dataTableResponse.setDraw(dataTableRequest.getDraw());
		dataTableResponse.setRecordsTotal(trainingFacade.count());
		dataTableResponse.setRecordsFiltered((int) pagination.getTotalElements());

		for (TrainingDto dto : pagination.getContent()) {

			TrainingDataTableResponseData data = new TrainingDataTableResponseData();
			data.setUuid(dto.getUuid().toString());
			data.setId(StringUtils.stripToEmpty(dto.getId()));
			data.setName(StringUtils.stripToEmpty(dto.getName()));
			dataTableResponse.getData().add(data);
		}
		return dataTableResponse;
	}

	@PreAuthorize(TrainingPreAuthorizeEnum.HAS_READ)
	@RequestMapping(value = TrainingWebConstants.Path.Api.TRAINING_HISTORY, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<HistoryDataTableResponseData> history(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.prepareDataTableRequest(request);
		dataTableRequest.setUniqueId((String) request.getParameter("uuid"));

		return historyDataTableResponse(dataTableRequest, trainingFacade.findHistory(dataTableRequest), trainingFacade.countHistory(dataTableRequest));
	}
}