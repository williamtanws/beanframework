package com.beanframework.core.facade;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.config.dto.DeploymentDto;
import com.beanframework.core.converter.dto.DeploymentDtoConverter;

@Component
public class DeploymentFacadeImpl implements DeploymentFacade {

  @Autowired
  private RepositoryService repositoryService;

  @Autowired
  private DeploymentDtoConverter dtoConverter;

  @Override
  public DeploymentDto findOneById(String id) throws BusinessException {
    Deployment deployment =
        repositoryService.createDeploymentQuery().deploymentId(id).singleResult();
    if (deployment == null) {
      throw new BusinessException("Could not find a deployment with id '" + id + "'.");
    }
    return dtoConverter.convert(deployment);
  }

  @Override
  public Page<DeploymentDto> findPage(DataTableRequest dataTableRequest) throws Exception {
    List<Deployment> page = null;

    if (dataTableRequest.isGlobalSearch() && StringUtils.isNotEmpty(dataTableRequest.getSearch())) {
      page = repositoryService.createDeploymentQuery()
          .deploymentNameLike(dataTableRequest.getSearch()).orderByDeploymentTime().desc()
          .listPage((int) dataTableRequest.getPageable().getOffset(),
              dataTableRequest.getPageable().getPageSize());
    } else {
      page = repositoryService.createDeploymentQuery().orderByDeploymentTime().desc().listPage(
          (int) dataTableRequest.getPageable().getOffset(),
          dataTableRequest.getPageable().getPageSize());
    }

    List<DeploymentDto> dtoObjects = new ArrayList<DeploymentDto>();
    for (Deployment model : page) {
      dtoObjects.add(dtoConverter.convert(model));
    }

    return new PageImpl<DeploymentDto>(dtoObjects,
        PageRequest.of(dataTableRequest.getPageable().getPageNumber(),
            dataTableRequest.getPageable().getPageSize(), dataTableRequest.getPageable().getSort()),
        page.size());
  }

  @Override
  public int count() throws Exception {
    return (int) repositoryService.createDeploymentQuery().count();
  }
}
