package com.beanframework.core.facade;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.config.dto.TaskDto;
import com.beanframework.core.converter.dto.TaskDtoConverter;

@Component
public class TaskFacadeImpl implements TaskFacade {

  @Autowired
  private TaskService taskService;

  @Autowired
  private TaskDtoConverter dtoConverter;

  @Override
  public TaskDto findOneById(String id) throws BusinessException {
    Task task = taskService.createTaskQuery().taskId(id).singleResult();

    if (task == null) {
      throw new BusinessException("Could not find a task with id '" + id + "'.");
    }
    return dtoConverter.convert(task);
  }

  @Override
  public Page<TaskDto> findPage(DataTableRequest dataTableRequest) {
    List<Task> page = null;

    if (dataTableRequest.isGlobalSearch() && StringUtils.isNotEmpty(dataTableRequest.getSearch())) {
      page = taskService.createTaskQuery().taskNameLike(dataTableRequest.getSearch())
          .orderByTaskCreateTime().desc().listPage((int) dataTableRequest.getPageable().getOffset(),
              dataTableRequest.getPageable().getPageSize());
    } else {
      page = taskService.createTaskQuery().orderByTaskCreateTime().desc().listPage(
          (int) dataTableRequest.getPageable().getOffset(),
          dataTableRequest.getPageable().getPageSize());
    }

    List<TaskDto> dtoObjects = new ArrayList<TaskDto>();
    for (Task model : page) {
      dtoObjects.add(dtoConverter.convert(model));
    }

    return new PageImpl<TaskDto>(dtoObjects,
        PageRequest.of(dataTableRequest.getPageable().getPageNumber(),
            dataTableRequest.getPageable().getPageSize(), dataTableRequest.getPageable().getSort()),
        page.size());
  }

  @Override
  public int count() {
    return (int) taskService.createTaskQuery().count();
  }
}
