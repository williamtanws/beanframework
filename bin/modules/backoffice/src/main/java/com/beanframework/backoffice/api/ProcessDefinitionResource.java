package com.beanframework.backoffice.api;

import javax.servlet.http.HttpServletRequest;

import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.beanframework.backoffice.ProcessDefinitionWebConstants;
import com.beanframework.backoffice.ProcessDefinitionWebConstants.ProcessDefinitionPreAuthorizeEnum;
import com.beanframework.backoffice.data.ProcessDefinitionDataTableResponseData;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.DataTableResponse;
import com.beanframework.core.api.AbstractResource;
import com.beanframework.core.data.DataTableResponseData;
import com.beanframework.core.facade.ProcessDefinitionFacade;

@RestController
public class ProcessDefinitionResource extends AbstractResource {
	
	@Autowired
	private ProcessDefinitionFacade deploymentFacade;

	@PreAuthorize(ProcessDefinitionPreAuthorizeEnum.HAS_READ)
	@RequestMapping(value = ProcessDefinitionWebConstants.Path.Api.PROCESSDEFINITION, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<DataTableResponseData> page(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.prepareDataTableRequest(request);
		
		Page<ProcessDefinition> pagination = deploymentFacade.findPage(dataTableRequest);

		DataTableResponse<DataTableResponseData> dataTableResponse = new DataTableResponse<DataTableResponseData>();
		dataTableResponse.setDraw(dataTableRequest.getDraw());
		dataTableResponse.setRecordsTotal(deploymentFacade.count());
		dataTableResponse.setRecordsFiltered((int) pagination.getTotalElements());

		for (ProcessDefinition dto : pagination.getContent()) {
			ProcessDefinitionDataTableResponseData data = new ProcessDefinitionDataTableResponseData();
			data.setId(dto.getId());
			data.setName(dto.getName());
			data.setDeploymentId(dto.getDeploymentId());
			dataTableResponse.getData().add(data);
		}
		return dataTableResponse;
	}
}