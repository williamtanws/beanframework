package com.beanframework.core.facade;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;

@Component
public class ProcessDefinitionFacadeImpl implements ProcessDefinitionFacade {

  @Autowired
  private RepositoryService repositoryService;

  @Override
  public ProcessDefinition findOneById(String id) throws BusinessException {
    ProcessDefinition deployment =
        repositoryService.createProcessDefinitionQuery().deploymentId(id).singleResult();
    if (deployment == null) {
      throw new BusinessException("Could not find a process definition with id '" + id + "'.");
    }
    return deployment;
  }

  @Override
  public Page<ProcessDefinition> findPage(DataTableRequest dataTableRequest) throws Exception {
    List<ProcessDefinition> page = null;

    if (dataTableRequest.isGlobalSearch() && StringUtils.isNotEmpty(dataTableRequest.getSearch())) {
      page = repositoryService.createProcessDefinitionQuery()
          .processDefinitionNameLike(dataTableRequest.getSearch()).orderByProcessDefinitionVersion()
          .desc().listPage((int) dataTableRequest.getPageable().getOffset(),
              dataTableRequest.getPageable().getPageSize());
    } else {
      page = repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionVersion()
          .desc().listPage((int) dataTableRequest.getPageable().getOffset(),
              dataTableRequest.getPageable().getPageSize());
    }
    return new PageImpl<ProcessDefinition>(page,
        PageRequest.of(dataTableRequest.getPageable().getPageNumber(),
            dataTableRequest.getPageable().getPageSize(), dataTableRequest.getPageable().getSort()),
        page.size());
  }

  @Override
  public int count() throws Exception {
    return (int) repositoryService.createProcessDefinitionQuery().count();
  }
}
